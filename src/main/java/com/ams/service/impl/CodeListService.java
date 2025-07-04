package com.ams.service.impl;

import com.ams.pojo.beans.CodeListBean;
import com.ams.pojo.request.CodeListRequest;
import com.ams.pojo.response.CodeListResponse;

import java.util.List;

public interface CodeListService {
    List<CodeListBean> list();
    CodeListResponse add(CodeListRequest request);

    CodeListResponse update(CodeListRequest request);

    CodeListResponse delete(Long recordId);

    CodeListResponse find(Long recordId);

    CodeListResponse findCodesByName(CodeListRequest request);

    CodeListResponse addCodeListCode(CodeListRequest request);

    CodeListResponse deleteCodeListCode(CodeListRequest request);
}
