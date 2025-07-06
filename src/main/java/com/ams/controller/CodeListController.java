package com.ams.controller;

import com.ams.pojo.request.CodeListRequest;
import com.ams.pojo.response.CodeListResponse;
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

    @GetMapping("/code/find/{codeListId}")
    private CodeListResponse addCode(@PathVariable Long codeListId) {
        return service.find(codeListId);
    }

    @PostMapping("/codes")
    private CodeListResponse findCodes(@RequestBody CodeListRequest request) {
        return service.findCodesByName(request);
    }

    @PostMapping("/code/add")
    private CodeListResponse addCodeListCode(@RequestBody CodeListRequest request) {
        return service.addCodeListCode(request);
    }

    @DeleteMapping("/code/delete")
    private CodeListResponse deleteCodeListCode(@RequestBody CodeListRequest request) {
        return service.deleteCodeListCode(request);
    }
}
