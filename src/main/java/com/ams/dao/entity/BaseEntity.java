package com.ams.dao.entity;


import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;

}
