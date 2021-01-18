package com.seven.mybatis.pagehelper.utils;

import com.seven.mybatis.pagehelper.PageException;
import com.seven.mybatis.pagehelper.bean.Page;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/28 11:30
 */
@Slf4j
public class SQLTools {
    /**
     * 获取count sql
     * @param sql
     * @param name
     * @return
     */
    public static String getSimpleCountSql(String sql, String name) {
        StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
        stringBuilder.append("select count(");
        stringBuilder.append(name);
        stringBuilder.append(") from ( \n");
        stringBuilder.append(sql);
        stringBuilder.append("\n ) tmp_count");
        return stringBuilder.toString();
    }

    public static String getMysqlPageSql(String sql, Page page) {
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
        sqlBuilder.append(sql);
        if (page.getStartRow() == 0L) {
            sqlBuilder.append("\n LIMIT ? ");
        } else {
            sqlBuilder.append("\n LIMIT ?, ? ");
        }

        return sqlBuilder.toString();
    }

    public static String converToOrderBySql(String sql, String orderBy) {
        Statement stmt = null;

        try {
            stmt = CCJSqlParserUtil.parse(sql);
            Select select = (Select) stmt;
            SelectBody selectBody = select.getSelectBody();
            List<OrderByElement> orderByElements = extraOrderBy(selectBody);
            String defaultOrderBy = PlainSelect.orderByToString(orderByElements);
            if (defaultOrderBy.indexOf(63) != -1) {
                throw new PageException("原SQL[" + sql + "]中的order by包含参数，因此不能使用OrderBy插件进行修改!");
            }

            sql = select.toString();
        } catch (Throwable var7) {
            log.warn("处理排序失败: " + var7 + "，降级为直接拼接 order by 参数");
        }

        return sql + " order by " + orderBy;
    }

    public static List<OrderByElement> extraOrderBy(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            List<OrderByElement> orderByElements = ((PlainSelect) selectBody).getOrderByElements();
            ((PlainSelect) selectBody).setOrderByElements((List) null);
            return orderByElements;
        } else {
            if (selectBody instanceof WithItem) {
                WithItem withItem = (WithItem) selectBody;
                if (withItem.getSelectBody() != null) {
                    return extraOrderBy(withItem.getSelectBody());
                }
            } else {
                SetOperationList operationList = (SetOperationList) selectBody;
                if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                    List<SelectBody> plainSelects = operationList.getSelects();
                    return extraOrderBy((SelectBody) plainSelects.get(plainSelects.size() - 1));
                }
            }

            return null;
        }
    }
}
