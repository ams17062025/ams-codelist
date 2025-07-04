package com.ams.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "AMS_CODE_LIST")
@Data
@NamedQuery(name="CodeList.findByCodeListName",
        query = "select e from CodeList e where e.name = :name")
public class CodeList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordId")
    private Long recordId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "codeList", cascade = CascadeType.ALL)
    private List<CodeListCode> codeListCode;

}
