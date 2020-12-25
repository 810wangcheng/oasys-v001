package com.cy.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class MapToListUtils<T> {

    /**
     * 将map中的value转为list
     * @param data
     * @return
     */
    public List<Object> mapToList(T data){
        Map<Object,Object> map = (Map<Object, Object>) data;
        Collection<Object> collection = map.values();
        List<Object> list = new ArrayList<>(collection);
        return list;
    }
}
