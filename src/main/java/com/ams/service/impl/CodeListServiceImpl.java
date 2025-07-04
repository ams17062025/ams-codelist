package com.ams.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.ams.aspect.LogExecutionTime;
import com.ams.pojo.beans.CodeListBean;
import com.ams.pojo.beans.CodeListCodeBean;
import com.ams.pojo.request.CodeListRequest;
import com.ams.pojo.response.CodeListResponse;
import com.ams.pojo.response.Error;
import com.ams.dao.entity.CodeList;
import com.ams.dao.entity.CodeListCode;
import com.ams.dao.repo.CodeListRepo;
import com.ams.pojo.utils.ResponseStatus;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CodeListServiceImpl implements CodeListService {

    Logger logger = LoggerFactory.getLogger(CodeListServiceImpl.class);

    @Autowired
    private CodeListRepo repo;

    @Override
    @LogExecutionTime
    public List<CodeListBean> list() {
        List<CodeList> codeLists = repo.findAll();
        List<CodeListBean> list = new ArrayList<>();
        codeLists.forEach(code -> {
            CodeListBean bean = CodeListBean.builder().build();
            bean.setRecordId(code.getRecordId());
            bean.setName(code.getName());
            bean.setDescription(code.getDescription());
            list.add(bean);
        });
        return list;
    }

    @Transactional
    public CodeListResponse add(CodeListRequest request) {
        CodeListResponse response = new CodeListResponse();
        if(Objects.nonNull(request.getCodeListBean())) {
            if(StringUtil.isNullOrEmpty(request.getCodeListBean().getName())) {
                response.setStatus("FAILED");
                Error error = new Error("AMS-CL-1002", "Code list name should not be empty.");
                response.setError(error);
                return response;
            }
            try {
                Optional<CodeList> existing = repo.findByCodeListName(request.getCodeListBean().getName().trim());
                if(existing.isPresent()) {
                    response.setStatus("FAILED");
                    Error error = new Error("AMS-CL-1004", "Codelist already exists.");
                    response.setError(error);
                    return response;
                }
                CodeList codeList = new CodeList();
                codeList.setName(request.getCodeListBean().getName());
                codeList.setDescription(request.getCodeListBean().getDescription());
                repo.save(codeList);
                response.setStatus("SUCCESS");
            } catch (Exception e) {
                response.setStatus("FAILED");
                Error error = new Error("AMS-CL-1000", "Error in save code list name");
                response.setError(error);
            }
        } else {
            response.setStatus("FAILED");
            Error error = new Error("AMS-CL-1001", "Request not found");
            response.setError(error);
        }
        return response;
    }

    @Override
    public CodeListResponse update(CodeListRequest request) {
        CodeListResponse response = new CodeListResponse();
        if(Objects.nonNull(request.getCodeListBean())) {
            if(Objects.isNull(request.getCodeListBean().getRecordId())) {
                response.setStatus("FAILED");
                Error error = new Error("AMS-CL-1003", "Code list id should not be null.");
                response.setError(error);
                return response;
            }
            try {
                Optional<CodeList> codeList = repo.findById(request.getCodeListBean().getRecordId());
                if(codeList.isPresent()) {
                    CodeList updateObject = codeList.get();
                    updateObject.setDescription(request.getCodeListBean().getDescription());
                    repo.save(updateObject);
                    response.setStatus("SUCCESS");
                } else {
                    response.setStatus("FAILED");
                    Error error = new Error("AMS-CL-1004", "Code list not found for update.");
                    response.setError(error);
                    return response;
                }
            } catch (Exception e) {
                response.setStatus("FAILED");
                Error error = new Error("AMS-CL-1000", "Error in save code list name");
                response.setError(error);
            }

        } else {
            response.setStatus("FAILED");
            Error error = new Error("AMS-CL-1001", "Request not found");
            response.setError(error);
        }
        return response;
    }

    public CodeListResponse delete(@NonNull Long recordId) {
        CodeListResponse response = new CodeListResponse();
        Optional<CodeList> codeList = repo.findById(recordId);
        if(codeList.isPresent()) {
            CodeList updateObject = codeList.get();
            repo.delete(updateObject);
            response.setStatus("SUCCESS");
        } else {
            response.setStatus("FAILED");
            Error error = new Error("AMS-CL-1004", "Code list not found for update.");
            response.setError(error);
            return response;
        }
        return response;
    }

    @Override
    @Transactional
    @LogExecutionTime
    public CodeListResponse find(Long recordId) {
        CodeListResponse response = new CodeListResponse();
        Optional<CodeList> codeListObj = repo.findById(recordId);
        if(codeListObj.isPresent()) {
            CodeList codeList = codeListObj.get();
            List<CodeListCodeBean> childs = new ArrayList<>();
            codeList.getCodeListCode().forEach(codeListCode -> {
                CodeListCodeBean codeListCodeBean = CodeListCodeBean.builder()
                        .recordId(codeListCode.getRecordId())
                        .code(codeListCode.getCode())
                        .codeValue(codeListCode.getCodeValue())
                        .codeDescription(codeListCode.getCodeDescription())
                        .build();
                childs.add(codeListCodeBean);
            });

            CodeListBean codeListBean = CodeListBean.builder()
                    .recordId(codeList.getRecordId())
                    .name(codeList.getName())
                    .description(codeList.getDescription())
                    .codeListCodeBeans(childs)
                    .build();
            response.setCodeListBean(codeListBean);
            response.setStatus("SUCCESS");
        } else {
            response.setStatus("FAILED");
            Error error = new Error("AMS-CL-1004", "Code list not found for update.");
            response.setError(error);
            return response;
        }
        return response;
    }

    @Override
    @LogExecutionTime
    public CodeListResponse findCodesByName(CodeListRequest request) {
        CodeListResponse response = new CodeListResponse();
        if(Objects.isNull(request) || Objects.isNull(request.getCodeListBean())) {
            logger.warn("Request should not be empty");
            response.setError(new Error("AMS-CL-1004", "Request should not be empty"));
        } else if(Objects.isNull(request.getCodeListBean().getName())) {
            logger.warn("Name should not be empty");
            response.setError(new Error("AMS-CL-1006", "Name should not be empty"));
        } else {
            Optional<CodeList> codeListObjs = repo.findByCodeListName(request.getCodeListBean().getName());
            if(codeListObjs.isPresent()) {
                CodeList codeList = codeListObjs.get();
                List<CodeListCodeBean> childs = new ArrayList<>();
                if(!CollectionUtils.isEmpty(codeList.getCodeListCode())) {
                    codeList.getCodeListCode().forEach(e -> {
                        CodeListCodeBean codeBean = CodeListCodeBean.builder()
                                .code(e.getCode())
                                .codeValue(e.getCodeValue())
                                .codeDescription(e.getCodeDescription())
                                .build();
                        childs.add(codeBean);
                    });
                }
                CodeListBean codeListBean = CodeListBean.builder()
                        .name(codeList.getName())
                        .description(codeList.getDescription())
                        .codeListCodeBeans(childs)
                        .build();
                response.setCodeListBean(codeListBean);
                response.setStatus(ResponseStatus.SUCCESS.toString());
            } else {
                logger.warn("Codelist not found for "+request.getCodeListBean().getName());
                response.setError(new Error("AMS-CL-1005", "Codelist not found"));
            }
        }
        return response;
    }

    @Override
    @LogExecutionTime
    @Transactional
    public CodeListResponse addCodeListCode(CodeListRequest request) {
        CodeListResponse response = new CodeListResponse();
        Optional<CodeList> codeListObj = repo.findById(request.getCodeListBean().getRecordId());
        if(codeListObj.isPresent()) {
            CodeList codeList = codeListObj.get();
            List<CodeListCode> existingChilds = codeList.getCodeListCode();
            List<String> existingCodes = existingChilds.stream().map(CodeListCode::getCode).toList();
            if(!CollectionUtils.isEmpty(request.getCodeListBean().getCodeListCodeBeans())) {
                request.getCodeListBean().getCodeListCodeBeans().forEach(e -> {
                    if(!existingCodes.contains(e.getCode().trim())) {
                        CodeListCode child = new CodeListCode();
                        child.setCode(e.getCode());
                        child.setCodeValue(e.getCodeValue());
                        child.setCodeDescription(e.getCodeDescription());
                        child.setCodeList(codeList);
                        codeList.getCodeListCode().add(child);
                    }
                });
                try {
                    repo.save(codeList);
                    response.setStatus(ResponseStatus.SUCCESS.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                logger.warn("Codelist not found for "+request.getCodeListBean().getRecordId());
                response.setError(new Error("AMS-CL-1005", "Codelist not found"));
            }
        }
        return response;
    }

    @Override
    @LogExecutionTime
    @Transactional
    public CodeListResponse deleteCodeListCode(CodeListRequest request) {
        CodeListResponse response = new CodeListResponse();
        Optional<CodeList> codeListObj = repo.findById(request.getCodeListBean().getRecordId());
        if(codeListObj.isPresent()) {
            CodeList codeList = codeListObj.get();
            List<String> deleteCode = new ArrayList<>();
            if(Objects.nonNull(request.getCodeListBean())
                    && !CollectionUtils.isEmpty(request.getCodeListBean().getCodeListCodeBeans())) {
                request.getCodeListBean().getCodeListCodeBeans().forEach(e -> {
                    deleteCode.add(e.getCode().trim());
                });
            }
            List<CodeListCode> deleteChilds = codeList.getCodeListCode()
                    .stream().filter(e-> deleteCode.contains(e.getCode())).toList();
            try {
                codeList.getCodeListCode().removeAll(deleteChilds);
                repo.save(codeList);
                response.setStatus(ResponseStatus.SUCCESS.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    //@Override
    public CodeListResponse addCode(CodeListRequest request) {
        CodeListResponse response = new CodeListResponse();
        Optional<CodeList> codeList = repo.findById(request.getCodeListBean().getRecordId());
        if(codeList.isPresent()) {
            CodeList updateObject = codeList.get();
            if(CollectionUtils.isEmpty(request.getCodeListBean().getCodeListCodeBeans())) {
                request.getCodeListBean().getCodeListCodeBeans().forEach(codeListCodeBean -> {
                    CodeListCode code = new CodeListCode();
                    code.setCode(codeListCodeBean.getCode());
                    code.setCodeValue(codeListCodeBean.getCodeValue());
                    code.setCodeDescription(codeListCodeBean.getCodeDescription());
                    code.setCodeList(updateObject);
                    updateObject.getCodeListCode().add(code);
                });
            }
            repo.save(updateObject);
            response.setStatus("SUCCESS");
        } else {
            response.setStatus("FAILED");
            Error error = new Error("AMS-CL-1004", "Code list not found for add code.");
            response.setError(error);
            return response;
        }
        return response;
    }

    //@Override
    public CodeListResponse removeCode(CodeListRequest request) {
        CodeListResponse response = new CodeListResponse();
        Optional<CodeList> codeList = repo.findById(request.getCodeListBean().getRecordId());
        if(codeList.isPresent()) {
            CodeList updateObject = codeList.get();
            if(CollectionUtils.isEmpty(request.getCodeListBean().getCodeListCodeBeans())) {
                List<Long> list = request.getCodeListBean().getCodeListCodeBeans().stream().map(CodeListCodeBean::getRecordId).toList();
                List<CodeListCode> removeList = new ArrayList<>();
                updateObject.getCodeListCode().forEach(codeListCode -> list.forEach(rec -> {
                    if(codeListCode.getRecordId().equals(rec)) {
                        removeList.add(codeListCode);
                    }
                }));
                updateObject.getCodeListCode().removeAll(removeList);
                repo.save(updateObject);
            }
            response.setStatus("SUCCESS");
        } else {
            response.setStatus("FAILED");
            Error error = new Error("AMS-CL-1004", "Code list not found for add code.");
            response.setError(error);
            return response;
        }
        return response;
    }
}
