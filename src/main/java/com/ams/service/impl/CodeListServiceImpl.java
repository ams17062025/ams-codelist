package com.ams.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.ams.beans.CodeListBean;
import com.ams.beans.CodeListRequest;
import com.ams.beans.CodeListResponse;
import com.ams.beans.Error;
import com.ams.dao.entity.CodeList;
import com.ams.dao.repo.CodeListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                e.printStackTrace();
            }

        } else {
            response.setStatus("FAILED");
            Error error = new Error("AMS-CL-1001", "Request not found");
            response.setError(error);
        }

        return response;
    }
}
