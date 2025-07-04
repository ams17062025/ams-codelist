package com.ams.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="AMS_CODE_LIST_CODE")
public class CodeListCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordId")
    private Long recordId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "CODE_VALUE")
    private String codeValue;

    @Column(name = "CODE_DESC")
    private String codeDescription;

    @ManyToOne
    @JoinColumn(name = "CODE_ID")
    private CodeList codeList;
}
