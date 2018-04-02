package com.meta.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    private BaseRepository<T, ID> repository;
    @PersistenceContext
    protected EntityManager entityManager;

    public void setBaseDao(BaseRepository baseRepository) {
        this.repository = baseRepository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> save(List<T> list) {
       return repository.save(list);
   }

    @Override
    public boolean exists(ID id) {
        if (id != null)
            return repository.exists(id);
        else
            return false;
    }

    @Override
    @Transactional(readOnly = true)
    public T findOne(ID id) {
        if (id != null)
            return repository.findOne(id);
        else
            return null;
    }

    @Override
    @Transactional(readOnly = true)
    public T findOneByFilter(String filter) {
        return repository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            Predicate[] restrictions = getPredicates(filter, root, criteriaBuilder);
            return criteriaBuilder.and(restrictions);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public T findOneByFilter(Map<String, Object> params) {
        return repository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            Predicate[] restrictions = getPredicates(params, root, criteriaBuilder);
            return criteriaBuilder.and(restrictions);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public T findOne(String field, Object value) {
        return repository.findOne((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(String sorts) {
        return repository.findAll(SortUtil.parseSorts(sorts));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(List<ID> ids) {
        return repository.findAll(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findList(String field, Object value) {
        return repository.findAll((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value));
    }


    @Override
    @Transactional(readOnly = true)
    public List<T> findList(String field, Object value, String sorts) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value),
                SortUtil.parseSorts(sorts)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findList(String filters, String sorts) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder)),
                SortUtil.parseSorts(sorts));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findList(Map<String, Object> params, String sorts) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(params, root, criteriaBuilder)),
                SortUtil.parseSorts(sorts));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findList(String filters) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findList(Map<String, Object> params) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(params, root, criteriaBuilder)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findDistinctList(String field, Object value) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.equal(root.get(field), value);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findDistinctList(String field, Object value, String sorts) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.equal(root.get(field), value);
                },
                SortUtil.parseSorts(sorts)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findDistinctList(String filters, String sorts) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder));
                },
                SortUtil.parseSorts(sorts));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findDistinctList(Map<String, Object> params, String sorts) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(getPredicates(params, root, criteriaBuilder));
                },
                SortUtil.parseSorts(sorts));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findDistinctList(String filters) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findDistinctListOr(List<String> orFilters) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(PredicateUtil.getOrPredicate(orFilters, criteriaBuilder, root));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findDistinctList(Map<String, Object> params) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(getPredicates(params, root, criteriaBuilder));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long count(String filters) {
        return repository.count((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder)));
    }

    @Override
    @Transactional(readOnly = true)
    public long count(Map<String, Object> params) {
        return repository.count((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(params, root, criteriaBuilder)));
    }

    protected Predicate[] getPredicates(String filter, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return PredicateUtil.getPredicates(filter, criteriaBuilder, root).toArray(new Predicate[]{});
    }

    protected Predicate[] getPredicates(Map<String,Object> params, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return PredicateUtil.getPredicates(params, criteriaBuilder, root).toArray(new Predicate[]{});
    }

    @Override
    public void delete(ID id) {
        repository.delete(id);
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Iterable<T> entitys) {
        repository.delete(entitys);
    }

    @Override
    public void delete(List<ID> ids) {
        List<T> list = repository.findAll(ids);
        repository.delete(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> search(String filters, Integer page, Integer size) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder)),
                new PageRequest(page - 1, size)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> searchOr(List<String> orFilters, Integer page, Integer size) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> PredicateUtil.getOrPredicate(orFilters,criteriaBuilder,root),
                new PageRequest(page - 1, size)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> search(Map<String, Object> params, Integer page, Integer size) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(params, root, criteriaBuilder)),
                new PageRequest(page - 1, size)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> search(String filters, String sorts, Integer page, Integer size) {
        Sort sort = SortUtil.parseSorts(sorts);
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder)),
                new PageRequest(page - 1, size, sort)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> searchOr(List<String> orFilters, String sorts, Integer page, Integer size) {
        Sort sort = SortUtil.parseSorts(sorts);
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> PredicateUtil.getOrPredicate(orFilters, criteriaBuilder, root),
                new PageRequest(page - 1, size, sort)
        );
    }
    @Transactional(readOnly = true)
    public List<T> search(String filters) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> search(Map<String, Object> params, String sorts, Integer page, Integer size) {
        Sort sort = SortUtil.parseSorts(sorts);
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(getPredicates(params, root, criteriaBuilder)),
                new PageRequest(page - 1, size, sort)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> searchDistinct(String filters, Integer page, Integer size) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder));
                },
                new PageRequest(page - 1, size)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> searchDistinct(Map<String, Object> params, Integer page, Integer size) {
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(getPredicates(params, root, criteriaBuilder));
                },
                new PageRequest(page - 1, size)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> searchDistinct(String filters, String sorts, Integer page, Integer size) {
        Sort sort = SortUtil.parseSorts(sorts);
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(getPredicates(filters, root, criteriaBuilder));
                },
                new PageRequest(page - 1, size, sort)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> searchDistinct(Map<String, Object> params, String sorts, Integer page, Integer size) {
        Sort sort = SortUtil.parseSorts(sorts);
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(getPredicates(params, root, criteriaBuilder));
                },
                new PageRequest(page - 1, size, sort)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> searchDistinctOr(List<String> strings, String sorts, int page, int size) {
        Sort sort = SortUtil.parseSorts(sorts);
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return PredicateUtil.getOrPredicate(strings,criteriaBuilder,root);
                },
                new PageRequest(page - 1, size, sort)
        );
    }
    //查询  a=1 or a=2
    @Transactional(readOnly = true)
    public Page<T> searchExtendDistinct(String filters, String sorts, Integer page, Integer size) {
        Sort sort = SortUtil.parseSorts(sorts);
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(PredicateUtil.getExtendPredicates(filters, criteriaBuilder, root).toArray(new Predicate[]{}));
                },
                new PageRequest(page - 1, size, sort)
        );
    }

    //查询  a=1 or a=2 无分页
    @Transactional(readOnly = true)
    public List<T> searchExtendDistinct(String filters) {

        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(PredicateUtil.getExtendPredicates(filters, criteriaBuilder, root).toArray(new Predicate[]{}));
                }
        );
    }



    @Transactional(readOnly = true)
    public long countExtend(String filters) {
        return repository.count((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(PredicateUtil.getExtendPredicates(filters, criteriaBuilder, root).toArray(new Predicate[]{})));
    }

    @Transactional(readOnly = true)
    public Page<T> searchExtend(String filters, String sorts, Integer page, Integer size) {
        Sort sort = SortUtil.parseSorts(sorts);
        return repository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> {
                    criteriaQuery.distinct(true);
                    return criteriaBuilder.and(PredicateUtil.getExtendPredicates(filters, criteriaBuilder, root).toArray(new Predicate[]{}));
                },
                new PageRequest(page - 1, size, sort)
        );
    }

    public List<T> findByField(String field, Object value){

        return findByFields(
                new String[]{field},
                new Object[]{value}
        );
    }

    public List<T> findByFields(String[] fields, Object[] values){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        List<Predicate> ls = new ArrayList<>();
        for(int i=0; i< fields.length; i++){
            if(values[i].getClass().isArray())
                ls.add(criteriaBuilder.in(root.get(fields[i]).in((Object[])values[i])));
            else
                ls.add(criteriaBuilder.equal(root.get(fields[i]), values[i]));
        }
        query.where(ls.toArray(new Predicate[ls.size()]));
        return entityManager
                .createQuery(query)
                .getResultList() ;
    }

    public Class<T> getEntityClass() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] parameters = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class) parameters[0];
    }
}
