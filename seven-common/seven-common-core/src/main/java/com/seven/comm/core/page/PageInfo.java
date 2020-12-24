package com.seven.comm.core.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author v_chendongdong
 * @date 2020/12/22
 */
@Data
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> rows;

    /**
     * 当前页
     */
    private int index;

    /**
     * 每页条数
     */
    private int size;

    public PageInfo(List<T> list, int total, int index) {
        this.rows = list;
        this.total = total;
        this.index = index;
    }

    public PageInfo(){

    }
}
