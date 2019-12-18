package com.cloud.springcloudweb.service.common;

import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SearchData<T> implements Serializable{

    //dataTable use
    private List<T> data;
    private Long recordsTotal;
    private Long recordsFiltered;
    private String draw;

    //page info
    private int start;
    private int length;
    private Sort sort;

    //search parameter
    private Map<String, Object> conditions;

    //csv
    String[] csvHeader;
}
