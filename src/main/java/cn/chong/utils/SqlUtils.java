package cn.chong.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 23:15
 * @description
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
