package com.ams.controller;

import com.ams.beans.CodeListRequest;
import com.ams.beans.CodeListResponse;
import com.ams.service.impl.CodeListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    private CodeListResponse update(@RequestBody CodeListRequest request) {
        return service.update(request);
    }

    @DeleteMapping("/delete/{recordId}")
    private CodeListResponse delete(@PathVariable Long recordId) {
        return service.delete(recordId);
    }

    @PostMapping("/code/add")
    private CodeListResponse addCode(@RequestBody CodeListRequest request) {
        return service.addCode(request);
    }

    @PostMapping("/code/remove")
    private CodeListResponse removeCode(@RequestBody CodeListRequest request) {
        return service.removeCode(request);
    }
}
