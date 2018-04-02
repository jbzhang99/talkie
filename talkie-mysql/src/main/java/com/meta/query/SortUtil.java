package com.meta.query;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortUtil {
    public static Sort parseSorts(String sorter) {
        if (!StringUtils.isEmpty(sorter)) {
            String[] orderArray = sorter.split(",");

            List<Sort.Order> orderList = new ArrayList<Sort.Order>(orderArray.length);
            Arrays.stream(orderArray).forEach(
                    elem -> orderList.add(
                            elem.startsWith("+") ? new Sort.Order(Sort.Direction.ASC, elem.substring(1)) :
                                    new Sort.Order(Sort.Direction.DESC, elem.substring(1))));

            return new Sort(orderList);
        }
        return null;
    }
}