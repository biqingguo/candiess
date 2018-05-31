package com.itranswarp.crypto.candiess.api.query;

import java.util.List;

/**
 * jqgrid data
 *
 * @author heguojun
 */
public class JqgridView {

    /**
     * 总记录数
     */
    private Integer records;

    /**
     * 记录列表
     */
    private List<?> rows;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 总页数
     */
    private Integer total;

    public JqgridView(Integer records, List<?> rows, Integer page, Integer total) {
        super();
        this.records = records;
        this.rows = rows;
        this.page = page;
        this.total = total;
    }

    public JqgridView() {
    }

    public Integer getRecords() {
        return this.records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public List<?> getRows() {
        return this.rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
