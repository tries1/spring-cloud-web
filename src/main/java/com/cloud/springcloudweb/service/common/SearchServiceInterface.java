package com.cloud.springcloudweb.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SearchServiceInterface<T> {
    Pageable updatePageable(int page, int size, Sort sort);
    TableData<T> getData(SearchData<T> searchData);
    Page<T> findListData(SearchData<T> searchData, Pageable pageable);
    List<Predicate> defaultPredicate(Root r, CriteriaQuery cq, CriteriaBuilder cb, Map<String, Object> map);
}
