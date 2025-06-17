package com.ams.service.impl;

import com.ams.beans.CodeListBean;
import com.ams.dao.entity.CodeList;
import com.ams.dao.repo.CodeListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
