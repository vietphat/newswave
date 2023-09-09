package com.vietphat.newswave.dto;

import java.util.Date;
import java.util.List;

public abstract class BaseDTO<T> {

    protected Long id;

    protected String createdBy;

    protected Date createdDate;

    protected String modifiedBy;

    protected Date modifiedDate;

    protected Integer currentPage = 1;

    protected Integer size = 5;

    protected int totalPages;

    protected Long totalItems;

    protected Integer visiblePageItems = 5;

    protected List<T> listResult;

    protected String search;

    public BaseDTO() {
    }

    public BaseDTO(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getVisiblePageItems() {
        return visiblePageItems;
    }

    public void setVisiblePageItems(Integer visiblePageItems) {
        this.visiblePageItems = visiblePageItems;
    }

    public List<T> getListResult() {
        return listResult;
    }

    public void setListResult(List<T> listResult) {
        this.listResult = listResult;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
