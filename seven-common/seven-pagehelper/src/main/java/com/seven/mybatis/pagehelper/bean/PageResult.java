package com.seven.mybatis.pagehelper.bean;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 14:26
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageNo = 1;
    private int size = 20;
    private long total;
    private long totalPage;
    private List<T> rows;

    private int offset = 0;
    private int limit = 20;

    private boolean count = true;

    public PageResult(List<T> list) {
        this.rows = list;
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNo = page.getPageNum();
            this.size = page.getPageSize();
            this.total = page.getTotal();
            this.totalPage = page.getPages();
            this.count = page.isCount();
        } else if (list instanceof Collection) {
            this.pageNo = 1;
            this.size = list.size();
            this.totalPage = this.size > 0 ? 1 : 0;
            this.size = list.size();
            this.count = false;
        }
    }

    public PageResult(int pageNo, int size, long total, List<T> rows) {
        this.pageNo = pageNo;
        this.size = size;
        this.total = total;
        this.setRows(rows);
        long totalPage = total % size == 0 ? total / size : total / size + 1;
        this.setTotalPage(totalPage);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        int pages = total / size;
        this.setTotalPage(total % size == 0 ? pages : pages + 1);
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = (this.pageNo - 1) * this.size;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public PageInfo<T> toPageInfo() {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setRows(this.getRows());
        pageInfo.setTotal((int) this.getTotal());
        pageInfo.setPageNo(this.getPageNo());
        pageInfo.setTotalPage((int) this.getTotalPage());
        return pageInfo;
    }


    public PageInfo toPageInfo(Class clz) {
        PageInfo pageInfo = new PageInfo<>();
        List<Object> nList = new ArrayList<>(this.getRows().size());
        List<T> oList = this.getRows();
        pageInfo.setTotal((int) this.getTotal());
        pageInfo.setPageNo(this.getPageNo());
        pageInfo.setTotalPage((int) this.getTotalPage());
        if (oList != null && oList.size() > 0) {
            oList.forEach((T item) -> {
                Object vo = null;
                try {
                    vo = clz.newInstance();
                    BeanUtils.copyProperties(item, vo);
                    nList.add(vo);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            });
            pageInfo.setRows(nList);
        } else {
            return pageInfo;
        }
        return pageInfo;
    }

    public PageInfo toPageInfo(PageResultCallback callback) {
        PageInfo pageInfo = new PageInfo<>();
        List<T> oList = this.getRows();
        pageInfo.setTotal((int) this.getTotal());
        pageInfo.setPageNo(this.getPageNo());
        pageInfo.setTotalPage((int) this.getTotalPage());
        if (oList != null && oList.size() > 0) {
            List nList=callback.callback(oList);
            pageInfo.setRows(nList);
        } else {
            return pageInfo;
        }
        return pageInfo;
    }
}
