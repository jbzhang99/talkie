package com.meta.query;

import com.google.common.base.Splitter;
import com.meta.Converters;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class PredicateUtil {
    private final static Logger logger = LoggerFactory.getLogger(PredicateUtil.class);

    public static enum Operator {
        NOTEQ, EQ, LIKE, LLIKE, RLIKE, ISNULL, ISNOTNULL, ISEMPTY, ISNOTEMPTY, IN, NOTIN, GT, LT, GTE, LTE, BETWEEN, LIKEIN,
    }

    public static <Z, X> Predicate[] getPredicatesArray(String filters, CriteriaBuilder criteriaBuilder, From<Z, X> root) {
        return getPredicates(filters, criteriaBuilder, root).toArray(new Predicate[]{});
    }

    public static <Z, X> Predicate getOrPredicate(List<String> filters, CriteriaBuilder criteriaBuilder, From<Z, X> root) {
        Map<String, Join> stringJoinMap = new HashMap<>();
        List<String> orFilters = filters.stream().filter(StringUtils::isNoneBlank).collect(Collectors.toList());
        if (orFilters.size() == 0) {
            return null;
        } else if (orFilters.size() == 1) {
            return criteriaBuilder.and(filterToPredicates(orFilters.get(0), criteriaBuilder, root, stringJoinMap).<javax.persistence.criteria.Predicate>toArray(new Predicate[]{}));
        } else {
            Predicate[] ors = orFilters
                    .stream().map(str -> criteriaBuilder.and(filterToPredicates(str, criteriaBuilder, root, stringJoinMap).toArray(new Predicate[]{})))
                    .collect(Collectors.toList()).toArray(new Predicate[]{});
            return criteriaBuilder.or(ors);
        }
    }

    public static <Z, X> List<Predicate> getPredicates(String filters, CriteriaBuilder criteriaBuilder, From<Z, X> root) {
        if (StringUtils.isBlank(filters)) {
            return new ArrayList<>();
        }

        Map<String, Join> stringJoinMap = new HashMap<>();

        List<Predicate> predicates = filterToPredicates(filters, criteriaBuilder, root, stringJoinMap);

        return predicates;
    }

    private static <Z, X> List<Predicate> filterToPredicates(String filters, CriteriaBuilder cb, From<Z, X> root, Map<String, Join> joinCache) {
        List<Predicate> predicates = new ArrayList<>();
        List<String[]> keyVals = Splitter.on(";").omitEmptyStrings().trimResults().splitToList(filters)
                .stream().map(token -> StringUtils.split(token, "=", 2))
                .collect(Collectors.toList());
        logger.debug("filters=>{}", filters);
        keyVals.stream().forEach(keyVal -> {
            String key = keyVal[0];

            String[] names = StringUtils.split(key, "_", 2);
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }

            Path path = getPath(root, joinCache, names[1]);

            String val = "";
            if (keyVal.length > 1) {
                val = StringUtils.trimToEmpty(keyVal[1]);
            }
            Operator operator = Operator.valueOf(names[0]);
            List<String> vals = Arrays.stream(val.split(",")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
            Class pathJavaType = path.getJavaType();
            switch (operator) {
                case NOTEQ:
                    predicates.add(cb.notEqual(path, Converters.convert(val, pathJavaType)));
                    break;
                case EQ:
                    predicates.add(cb.equal(path, Converters.convert(val, pathJavaType)));
                    break;
                case LIKE:
                    predicates.add(cb.like(path, "%" + val + "%"));
                    break;
                case LLIKE:
                    predicates.add(cb.like(path, "%" + val));
                    break;
                case RLIKE:
                    predicates.add(cb.like(path, val + "%"));
                    break;
                case GT:
                    predicates.add(cb.greaterThan(path, (Comparable) Converters.convert(val, pathJavaType)));
                    break;
                case LT:
                    predicates.add(cb.lessThan(path, (Comparable) Converters.convert(val, pathJavaType)));
                    break;
                case GTE:
                    predicates.add(cb.greaterThanOrEqualTo(path, (Comparable) Converters.convert(val, pathJavaType)));
                    break;
                case LTE:
                    predicates.add(cb.lessThanOrEqualTo(path, (Comparable) Converters.convert(val, pathJavaType)));
                    break;
                case ISNULL:
                    predicates.add(cb.isNull(path));
                    break;
                case ISNOTNULL:
                    predicates.add(cb.isNotNull(path));
                    break;
                case ISEMPTY:
                    predicates.add(cb.isEmpty(path));
                    break;
                case ISNOTEMPTY:
                    predicates.add(cb.isNotEmpty(path));
                    break;
                case BETWEEN:
                    if (vals.isEmpty() || vals.size() != 2) {
                        throw new RuntimeException("BETWEEN Params is Empty.");
                    }
                    predicates.add(cb.between(path,
                            (Comparable) Converters.convert(vals.get(0), pathJavaType),
                            (Comparable) Converters.convert(vals.get(1), pathJavaType)));
                    break;
                case IN:
                    if (vals.isEmpty()) {
                        throw new RuntimeException("IN Params is Empty.");
                    }
                    predicates.add(path.in(vals.stream().map(s -> Converters.convert(s, pathJavaType)).collect(Collectors.toList())));
                    break;
                case NOTIN:
                    if (vals.isEmpty()) {
                        throw new RuntimeException("IN Params is Empty.");
                    }
                    predicates.add(path.in(vals.stream().map(s -> Converters.convert(s, pathJavaType)).collect(Collectors.toList())).not());
                    break;
                case LIKEIN:
                    if (vals.isEmpty()) {
                        throw new RuntimeException("LIKEIN Params is Empty.");
                    }
                    Predicate[] orPredicates = new Predicate[vals.size()];
                    for(int i=0;i<vals.size();i++) {
                        orPredicates[i] = cb.like(path, "%" + vals.get(i) + "%");
                    }
                    predicates.add(cb.or(orPredicates));
                    break;
            }
        });

        return predicates;
    }

    public static <Z, X> List<Predicate> getExtendPredicates(String filters, CriteriaBuilder criteriaBuilder, From<Z, X> root) {
        if (StringUtils.isBlank(filters)) {
            return new ArrayList<>();
        }
        System.err.println(filters);
        logger.debug("getExtendPredicates[filters]=>{}", filters);
        Map<String, Predicate> predicateMap = new HashMap<>();
        String[] filterArray = filters.split(";");
        Map<String, Join> stringJoinMap = new HashMap<>();
        for (int i = 0; i < filterArray.length; ++i) {
            String filter = filterArray[i];
            if (StringUtils.isBlank(filter))
                continue;

            String[] tokens;
            tokens = filter.split("&");

            String group = null;
            if (tokens.length >= 2) group = tokens[1];

            Predicate predicate = null;
            try {
                predicate = filterToPredicate(tokens[0], criteriaBuilder, root, stringJoinMap);
            } catch (ParseException e) {
                throw new RuntimeException("查询参数filters处理失败");
            }

            if (group != null) {
                if (predicateMap.get(group) == null)
                    predicateMap.put(group, predicate);
                else
                    predicateMap.put(group, criteriaBuilder.or(predicateMap.get(group), predicate));
            } else
                predicateMap.put(Integer.toString(i), predicate);
        }
        System.err.println(predicateMap.values());
        return new ArrayList<>(predicateMap.values());
    }

    private static <Z, X> Predicate filterToPredicate(String filters, CriteriaBuilder cb, From<Z, X> root, Map<String, Join> joinCache) throws ParseException {
        Predicate predicate = null;
        List<String[]> keyVals = Splitter.on(";").omitEmptyStrings().trimResults().splitToList(filters)
                .stream().map(token -> StringUtils.split(token, "=", 2))
                .collect(Collectors.toList());
        logger.debug("filters=>{}", filters);

        String[] keyAndValue = StringUtils.split(filters, "=", 2);

        String key = keyAndValue[0];

        String[] names = StringUtils.split(key, "_", 2);
        if (names.length != 2) {
            throw new IllegalArgumentException(key + " is not a valid search filter name");
        }

        Path path = getPath(root, joinCache, names[1]);

        String val = "";
        if (keyAndValue.length > 1) {
            val = StringUtils.trimToEmpty(keyAndValue[1]);
        }
        Operator operator = Operator.valueOf(names[0]);
        String[] vals = val.split(",");
        Class pathJavaType = path.getJavaType();

        switch (operator) {
            case NOTEQ:
                predicate = cb.notEqual(path, Converters.convert(val, pathJavaType));
                break;
            case EQ:
                predicate = cb.equal(path, Converters.convert(val, pathJavaType));
                break;
            case LIKE:
                predicate = cb.like(path, "%" + val + "%");
                break;
            case LLIKE:
                predicate = cb.like(path, "%" + val);
                break;
            case RLIKE:
                predicate = cb.like(path, val + "%");
                break;
            case GT:
                predicate = cb.greaterThan(path, (Comparable) Converters.convert(val, pathJavaType));
                break;
            case LT:
                predicate = cb.lessThan(path, (Comparable) Converters.convert(val, pathJavaType));
                break;
            case GTE:
                predicate = cb.greaterThanOrEqualTo(path, (Comparable) Converters.convert(val, pathJavaType));
                break;
            case LTE:
                predicate = cb.lessThanOrEqualTo(path, (Comparable) Converters.convert(val, pathJavaType));
                break;
            case ISNULL:
                predicate = cb.isNull(path);
                break;
            case ISNOTNULL:
                predicate = cb.isNotNull(path);
                break;
            case ISEMPTY:
                predicate = cb.isEmpty(path);
                break;
            case ISNOTEMPTY:
                predicate = cb.isNotEmpty(path);
                break;
            case BETWEEN:
                if (ArrayUtils.isEmpty(vals) || vals.length != 2) {
                    throw new RuntimeException("BETWEEN Params is Empty.");
                }
                predicate = cb.between(path,
                        (Comparable) Converters.convert(vals[0], pathJavaType),
                        (Comparable) Converters.convert(vals[1], pathJavaType));
                break;
            case IN:
                if (ArrayUtils.isEmpty(vals)) {
                    throw new RuntimeException("IN Params is Empty.");
                }
                predicate = path.in(Arrays.stream(vals).map(s -> Converters.convert(s, pathJavaType)).collect(Collectors.toList()));
                break;
            case NOTIN:
                if (ArrayUtils.isEmpty(vals)) {
                    throw new RuntimeException("IN Params is Empty.");
                }
                predicate = path.in(Arrays.stream(vals).map(s -> Converters.convert(s, pathJavaType)).collect(Collectors.toList())).not();
                break;
            case LIKEIN:
                if (ArrayUtils.isEmpty(vals)) {
                    throw new RuntimeException("LIKEIN Params is Empty.");
                }
                Predicate[] orPredicates = new Predicate[vals.length];
                for (int i = 0; i < vals.length; i++) {
                    orPredicates[i] = cb.like(path, "%" + vals[i] + "%");
                }
                predicate = cb.or(orPredicates);
                break;
        }
        return predicate;
    }

    private static <Z, X> Path getPath(From<Z, X> root, Map<String, Join> stringJoinMap, String fieldName) {
        Path path = null;
        List<String> fields = Splitter.on(".").omitEmptyStrings().trimResults().splitToList(fieldName);
        String firstFiled = fields.get(0);
        if (fields.size() > 1) {
            Join join = null;
            for (int i = 0; i < fields.size(); i++) {
                String field = fields.get(i);
                String[] joinField = StringUtils.split(field, "_", 2);
                if (i == fields.size() - 1) {
                    path = join.get(field);
                } else {
                    String joinFieldKey = StringUtils.join(fields.subList(0, i + 1), ".");
                    if (stringJoinMap.containsKey(joinFieldKey)) {
                        join = stringJoinMap.get(joinFieldKey);
                    } else {
                        if (i == 0) {
                            join = (joinField.length == 2 ? root.join(joinField[1], JoinType.valueOf(joinField[0])) : root.join(field));
                        } else {
                            join = (joinField.length == 2 ? join.join(joinField[1], JoinType.valueOf(joinField[0])) : join.join(field));
                        }
                        stringJoinMap.put(joinFieldKey, join);
                    }
                }
            }
        } else {
            path = root.get(firstFiled);
        }
        return path;
    }

    public static <Z, X> Predicate[] getPredicatesArray(Map<String, Object> params, CriteriaBuilder criteriaBuilder, From<Z, X> root) {
        return getPredicates(params, criteriaBuilder, root).toArray(new Predicate[]{});
    }

    public static <Z, X> List<Predicate> getPredicates(Map<String, Object> params, CriteriaBuilder cb, From<Z, X> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (MapUtils.isEmpty(params)) {
            return predicates;
        }
        Map<String, Join> stringJoinMap = new HashMap<>();
        params.forEach((key, val) -> {
            String[] names = StringUtils.split(key, "_", 2);
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }

            Path path = getPath(root, stringJoinMap, names[1]);

            Operator operator = Operator.valueOf(names[0]);
            switch (operator) {
                case NOTEQ:
                    predicates.add(cb.notEqual(path, val));
                    break;
                case EQ:
                    predicates.add(cb.equal(path, val));
                    break;
                case LIKE:
                    predicates.add(cb.like(path, "%" + val + "%"));
                    break;
                case LLIKE:
                    predicates.add(cb.like(path, "%" + val));
                    break;
                case RLIKE:
                    predicates.add(cb.like(path, val + "%"));
                    break;
                case GT:
                    predicates.add(cb.greaterThan(path, (Comparable) val));
                    break;
                case LT:
                    predicates.add(cb.lessThan(path, (Comparable) val));
                    break;
                case GTE:
                    predicates.add(cb.greaterThanOrEqualTo(path, (Comparable) val));
                    break;
                case LTE:
                    predicates.add(cb.lessThanOrEqualTo(path, (Comparable) val));
                    break;
                case ISNULL:
                    predicates.add(cb.isNull(path));
                    break;
                case ISNOTNULL:
                    predicates.add(cb.isNotNull(path));
                    break;
                case ISEMPTY:
                    predicates.add(cb.isEmpty(path));
                    break;
                case ISNOTEMPTY:
                    predicates.add(cb.isNotEmpty(path));
                    break;
                case BETWEEN:
                    Object[] values = (Object[]) val;
                    if (ArrayUtils.isEmpty(values) || values.length != 2) {
                        throw new RuntimeException("IN Params is Empty.");
                    }
                    predicates.add(cb.between(path,
                            (Comparable) values[0],
                            (Comparable) values[1]));
                    break;
                case IN:
                    Object[] vals = (Object[]) val;
                    if (ArrayUtils.isEmpty(vals)) {
                        throw new RuntimeException("IN Params is Empty.");
                    }
                    predicates.add(path.in(vals));
                    break;
                case LIKEIN:
                    Object[] orVals = (Object[]) val;
                    if (ArrayUtils.isEmpty(orVals)) {
                        throw new RuntimeException("LIKEIN Params is Empty.");
                    }
                    Predicate[] orPredicates = new Predicate[orVals.length];
                    for (int i = 0; i < orVals.length; i++) {
                        orPredicates[i] = cb.like(path, "%" + orVals[i] + "%");
                    }
                    predicates.add(cb.or(orPredicates));
                    break;
            }
        });

        return predicates;
    }
}
