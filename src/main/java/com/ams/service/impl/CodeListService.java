package com.ams.service.impl;

import com.ams.beans.CodeListBean;
import com.ams.beans.CodeListRequest;
import com.ams.beans.CodeListResponse;

import java.util.List;

public interface CodeListService {
    List<CodeListBean> list();
    CodeListResponse add(CodeListRequest request);

    CodeListResponse update(CodeListRequest request);

    CodeListResponse delete(Long recordId);

    CodeListResponse addCode(CodeListRequest request);

    CodeListResponse removeCode(CodeListRequest request);
}
