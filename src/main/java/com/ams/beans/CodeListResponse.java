package com.ams.beans;

import lombok.Data;

import java.util.List;

@Data
public class CodeListResponse extends BaseResponse {

    private List<CodeListBean> codeListBeanList;

}
