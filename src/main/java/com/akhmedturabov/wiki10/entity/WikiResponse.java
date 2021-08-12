package com.akhmedturabov.wiki10.entity;

import java.util.*;

import lombok.*;

@Data
public class WikiResponse {
    private int error;
    private String errorMessage;
    private List<WikiPage> pages;

    private WikiResponse(String errorMessage) {
        this.error = 1;
        this.errorMessage = errorMessage;
        pages = Collections.EMPTY_LIST;
    }

    private WikiResponse(List<WikiPage> pages) {
        this.pages = pages;
    }

    public static WikiResponse ok(List<WikiPage> pages) {
        return new WikiResponse(pages);
    }

    public static WikiResponse error(String errorMessage) {
        return new WikiResponse(errorMessage);
    }
}
