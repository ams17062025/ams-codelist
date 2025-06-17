package com.ams.beans;

import lombok.Data;

import java.util.List;

@Data
public class CodeListBean {

    private Long recordId;

    private String name;

    private String description;

    private List<CodeListCodeBean> codeListCodeBeans;
}
