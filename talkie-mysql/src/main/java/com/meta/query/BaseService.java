package com.meta.query;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T, ID extends Serializable> {

    T save(T entity);

    List<T> save(List<T> list);

    boolean exists(ID id);

    T findOne(ID id);

    T findOneByFilter(String filter);

    @Transactional(readOnly = true)
    T findOneByFilter(Map<String, Object> params);

    T findOne(String field, Object value);

    @Transactional(readOnly = true)
    List<T> findAll();

    @Transactional(readOnly = true)
    List<T> findAll(String sorts);

    @Transactional(readOnly = true)
    List<T> findAll(List<ID> ids);

    List<T> findList(String field, Object value);

    List<T> findList(String field, Object value, String sorts);

    List<T> findList(String filters, String sorts);

    @Transactional(readOnly = true)
    List<T> findList(Map<String, Object> params, String sorts);

    List<T> findList(String filters);

    @Transactional(readOnly = true)
    List<T> findList(Map<String, Object> params);

    List<T> findDistinctList(String field, Object value);

    List<T> findDistinctList(String field, Object value, String sorts);

    List<T> findDistinctList(String filters, String sorts);

    @Transactional(readOnly = true)
    List<T> findDistinctList(Map<String, Object> params, String sorts);

    List<T> findDistinctList(String filters);

    @Transactional(readOnly = true)
    List<T> findDistinctListOr(List<String> orFilters);

    @Transactional(readOnly = true)
    List<T> findDistinctList(Map<String, Object> params);

    long count();

    long count(String filters);

    @Transactional(readOnly = true)
    long count(Map<String, Object> params);

    void delete(ID id);

    void delete(T entity);

    void delete(Iterable<T> entitys);

    void delete(List<ID> ids);

    Page<T> search(String filters, Integer page, Integer size);


    @Transactional(readOnly = true)
    Page<T> searchOr(List<String> orFilters, Integer page, Integer size);

    @Transactional(readOnly = true)
    Page<T> search(Map<String, Object> params, Integer page, Integer size);

    Page<T> search(String filters, String sorts, Integer page, Integer size);

    @Transactional(readOnly = true)
    Page<T> searchOr(List<String> orFilters, String sorts, Integer page, Integer size);

    @Transactional(readOnly = true)
    Page<T> search(Map<String, Object> params, String sorts, Integer page, Integer size);

    Page<T> searchDistinct(String filters, Integer page, Integer size);

    @Transactional(readOnly = true)
    Page<T> searchDistinct(Map<String, Object> params, Integer page, Integer size);

    Page<T> searchDistinct(String filters, String sorts, Integer page, Integer size);

    @Transactional(readOnly = true)
    Page<T> searchDistinct(Map<String, Object> params, String sorts, Integer page, Integer size);

    @Transactional(readOnly = true)
    Page<T> searchDistinctOr(List<String> strings, String sorts, int page, int size);
}
