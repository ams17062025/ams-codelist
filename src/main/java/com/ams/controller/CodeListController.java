package com.ams.controller;

import com.ams.beans.CodeListBean;
import com.ams.service.impl.CodeListService;
import com.ams.service.impl.CodeListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("CodeListController")
@RequestMapping("/codelist")
public class CodeListController {

    @Autowired
    private CodeListServiceImpl service;

    @GetMapping("/list")
    public List<CodeListBean> list() {
        return service.list();
    }
}
