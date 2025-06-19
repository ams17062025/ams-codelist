package com.ams.beans;

import lombok.Data;

import java.util.List;

@Data
public class BaseResponse {

    private String status;

    private Error error;



}
