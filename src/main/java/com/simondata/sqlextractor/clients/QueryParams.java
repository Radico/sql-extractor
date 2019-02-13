package com.simondata.sqlextractor.clients;

public class QueryParams {

    private static final Integer DEFAULT_FETCH_SIZE = 10000;

    private Integer fetchSize;

    public QueryParams() {
        this.fetchSize = DEFAULT_FETCH_SIZE;
    }

    public QueryParams(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }

    public Integer getFetchSize() {
        if (fetchSize == null) {
            return DEFAULT_FETCH_SIZE;
        } else {
            return fetchSize;
        }
    }

    public static QueryParams getDefaultQueryParams() {
        return new QueryParams();
    }
}
