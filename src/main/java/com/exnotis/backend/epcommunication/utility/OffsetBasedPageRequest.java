package com.exnotis.backend.epcommunication.utility;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest implements Pageable {

    private int limit;
    private int offset;
    private final Sort sort;

    public OffsetBasedPageRequest(int limit, int offset, Sort sort){

        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }

        if (limit > 20) {
            throw new IllegalArgumentException("Limit must not be great than 20!");
        }

        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }


    public OffsetBasedPageRequest(int offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }



    // Override methods   #############

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return null;
        //return new OffsetBasedPageRequest(getOffset() + getPageSize(), getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
