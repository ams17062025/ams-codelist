package com.ams.controller;

import com.ams.beans.CodeListBean;
import com.ams.beans.CodeListRequest;
import com.ams.beans.CodeListResponse;
import com.ams.service.impl.CodeListService;
import com.ams.service.impl.CodeListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("CodeListController")
@RequestMapping("/codelist")
@CrossOrigin(origins = "http://localhost:3000")
public class CodeListController {

    @Autowired
    private CodeListServiceImpl service;

    @GetMapping("/list")
    public CodeListResponse list() {
        System.out.println("Calling the list API");
        CodeListResponse response = new CodeListResponse();
        response.setCodeListBeanList(service.list());
        response.setStatus("SUCCESS");
        return response;
    }

    @PostMapping("/add")
    private CodeListResponse add(@RequestBody CodeListRequest request) {
        return service.add(request);
    }


}
