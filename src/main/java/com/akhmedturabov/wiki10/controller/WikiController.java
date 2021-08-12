package com.akhmedturabov.wiki10.controller;

import com.akhmedturabov.wiki10.client.*;
import com.akhmedturabov.wiki10.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pages")
public class WikiController {

    @Autowired
    private WikiClient wikiClient;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WikiResponse> getPages(@RequestParam(value = "lat") double lat,
                                                 @RequestParam(value = "lon") double lon) {
        try {
            return ResponseEntity.ok(wikiClient.getPages(lat, lon));
        } catch (Exception e) {
            return ResponseEntity.ok(WikiResponse.error(e.getMessage()));
        }
    }
}
