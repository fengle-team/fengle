package com.yunqi.fengle.model.http;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class BmobListResponse<T> {
    private T results;

    private int code;

    private String error;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
