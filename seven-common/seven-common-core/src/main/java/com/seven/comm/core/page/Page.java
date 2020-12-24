package com.seven.comm.core.page;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> rows;
}
