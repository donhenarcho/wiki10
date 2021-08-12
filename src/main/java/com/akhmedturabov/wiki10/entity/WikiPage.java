package com.akhmedturabov.wiki10.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiPage {
    private int pageid;
    private String title;
    private String fullurl;

    public static WikiPage create(int pageid) {
        WikiPage wikiPage = new WikiPage();
        wikiPage.setPageid(pageid);
        return wikiPage;
    }
}
