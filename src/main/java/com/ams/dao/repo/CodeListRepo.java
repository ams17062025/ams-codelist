package com.ams.dao.repo;

import com.ams.dao.entity.CodeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CodeListRepo extends JpaRepository<CodeList, Long> {

}
