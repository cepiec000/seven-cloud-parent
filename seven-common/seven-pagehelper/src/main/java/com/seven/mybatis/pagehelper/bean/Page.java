package com.seven.mybatis.pagehelper.bean;

import com.seven.mybatis.pagehelper.PageTools;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 14:23
 */
public class Page<E> extends ArrayList<E> implements Closeable {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private long startRow;
    private long endRow;
    private long total;
    private int pages;
    private boolean count;
    private String orderBy;

    public Page() {
        this.count = true;
    }

    public Page(int pageNum, int pageSize) {
        this(pageNum, pageSize, true);
    }

    public Page(int pageNum, int pageSize, boolean count) {
        super(0);
        this.count = true;
        if (pageNum == 1 && pageSize == 2147483647) {
            pageSize = 0;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.count = count;
        this.startRow = (pageNum - 1) * this.pageSize;
    }

    public Page(int[] rowBounds, boolean count) {
        super(0);
        this.count = true;
        if (rowBounds[0] == 0 && rowBounds[1] == 2147483647) {
            this.pageSize = 0;
        } else {
            this.pageSize = rowBounds[1];
            this.pageNum = rowBounds[1] != 0 ? (int) Math.ceil(((double) rowBounds[0] + (double) rowBounds[1]) / (double) rowBounds[1]) : 0;
        }

        this.startRow = (long) rowBounds[0];
        this.count = count;
        this.endRow = this.startRow + (long) rowBounds[1];
    }

    public List<E> getResult() {
        return this;
    }

    public int getPages() {
        return this.pages;
    }

    public Page<E> setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public long getEndRow() {
        return this.endRow;
    }

    public Page<E> setEndRow(long endRow) {
        this.endRow = endRow;
        return this;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public Page<E> setPageNum(int pageNum) {
        this.pageNum = pageNum <= 0 ? 1 : pageNum;
        return this;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public Page<E> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getStartRow() {
        return this.startRow;
    }

    public Page<E> setStartRow(long startRow) {
        this.startRow = startRow;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (total == -1L) {
            this.pages = 1;
        } else {
            if (this.pageSize > 0) {
                this.pages = (int) (total / (long) this.pageSize + (long) (total % (long) this.pageSize == 0L ? 0 : 1));
            } else {
                this.pages = 0;
            }

            if (this.pageNum > this.pages) {
                if (this.pages != 0) {
                    this.pageNum = this.pages;
                }

                this.calculateStartAndEndRow();
            }

        }
    }

    private void calculateStartAndEndRow() {
        this.startRow = this.pageNum > 0 ? (long) ((this.pageNum - 1) * this.pageSize) : 0L;
        this.endRow = this.startRow + (long) (this.pageSize * (this.pageNum > 0 ? 1 : 0));
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public <E> Page<E> setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return (Page<E>) this;
    }

    public boolean isCount() {
        return this.count;
    }

    public Page<E> setCount(boolean count) {
        this.count = count;
        return this;
    }

    public Page<E> pageNum(int pageNum) {
        this.pageNum = pageNum <= 0 ? 1 : pageNum;
        return this;
    }

    public Page<E> pageSize(int pageSize) {
        this.pageSize = pageSize;
        this.calculateStartAndEndRow();
        return this;
    }

    public Page<E> count(Boolean count) {
        this.count = count;
        return this;
    }

    @Override
    public void close() throws IOException {
        PageTools.clearPage();
    }
}
