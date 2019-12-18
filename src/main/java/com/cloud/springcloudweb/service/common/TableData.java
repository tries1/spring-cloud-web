package com.cloud.springcloudweb.service.common;

import java.util.List;

import lombok.Data;

@Data
public class TableData<T>{
    private List<T> data;
    private Long recordsTotal;
    private Long recordsFiltered;
    private String draw;
}
