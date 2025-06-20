package com.ams.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.ams.beans.*;
import com.ams.beans.Error;
import com.ams.dao.entity.CodeList;
import com.ams.dao.entity.CodeListCode;
import com.ams.dao.repo.CodeListRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CodeListServiceImpl implements CodeListService {

    @Autowired
    private CodeListRepo repo;

    @Override
    public List<CodeListBean> list() {
        List<CodeList> codeLists = repo.findAll();
        List<CodeListBean> list = new ArrayList<>();
        codeLists.forEach(code -> {
            CodeListBean bean = new CodeListBean();
            bean.setRecordId(code.getRecordId());
            bean.setName(code.getName());
            bean.setDescription(code.getDescription());
            list.add(bean);
        });
        return list;
    }

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

    @Override
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
