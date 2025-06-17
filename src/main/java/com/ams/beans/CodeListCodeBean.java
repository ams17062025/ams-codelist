package com.ams.beans;

import lombok.Data;

@Data
public class CodeListCodeBean {

    private Long recordId;

    private String code;

    private String codeValue;

    private String codeDescription;

    private CodeListBean codeListBean;
}
