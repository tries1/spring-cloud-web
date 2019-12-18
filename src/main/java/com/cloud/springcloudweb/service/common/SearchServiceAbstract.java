package com.cloud.springcloudweb.service.common;

import com.cloud.springcloudweb.repository.BaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
public abstract class SearchServiceAbstract<T> implements SearchServiceInterface<T> {

    BaseRepository<T, Long> baseRepository;

    @Override
    public Page<T> findListData(SearchData<T> searchData, Pageable pageable) {
        return baseRepository.findAll((r, cq, cb) -> {
            List<Predicate> predicates = Optional.ofNullable( searchData.getConditions() )
                .map(map -> defaultPredicate(r, cq, cb, map))
                .orElse(new ArrayList<>());

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

    /**
     * 화면에 전달될 데이터
     *
     * @param searchData
     * @return
     */
    @Override
    public TableData<T> getData(SearchData<T> searchData) {
        Pageable pageable = updatePageable(searchData.getStart(), searchData.getLength(), searchData.getSort());
        Page<T> data = findListData(searchData, pageable);

        TableData<T> tableData = new TableData<>();
        tableData.setData(data.getContent());
        tableData.setRecordsTotal(data.getTotalElements());
        tableData.setRecordsFiltered(data.getTotalElements());
        tableData.setDraw(searchData.getDraw());
        return tableData;
    }

    /**
     * @param page   : 페이지 번호
     * @param length : 한번에 보여줄 데이터수
     * @return
     */
    @Override
    public Pageable updatePageable(int page, int length, Sort sort) {
        if (length == 0) {
            length = 20;
        }
        if (page == 0) {
            page = 1;
        }

        page = page / length;//dataTable에서 page번호가 아닌 page 시작번호가 전달됨

        return (Objects.isNull(sort)) ? PageRequest.of(page, length) : PageRequest.of(page, length, sort);
    }

    /**
     * Criteria를 이용하여 동적쿼리를 사용할때 기본적인 조건을 정의
     *
     * @param r
     * @param cq
     * @param cb
     * @param map
     * @return
     */
    @Override
    public List<Predicate> defaultPredicate(Root r, CriteriaQuery cq, CriteriaBuilder cb, Map<String, Object> map) {
        List<Predicate> predicates = new ArrayList<>();
        map.forEach((k, v) -> {

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
            if ("startDate".equals(k)) {
                predicates.add(cb.greaterThanOrEqualTo(r.get("createdAt"), LocalDate.parse(map.get(k).toString(), df).atTime(00, 00, 00)));
            } else if ("endDate".equals(k)) {
                predicates.add(cb.lessThanOrEqualTo(r.get("createdAt"), LocalDate.parse(map.get(k).toString(), df).atTime(23, 59, 59)));
            } else {
                predicates.add(cb.equal(r.get(k), map.get(k)));
            }
        });

        return predicates;
    }
}
