package com.akhmedturabov.wiki10.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.util.*;

import com.akhmedturabov.wiki10.client.*;
import com.akhmedturabov.wiki10.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.mock.web.*;
import org.springframework.web.context.request.*;

@ExtendWith(MockitoExtension.class)
public class WikiControllerTest {

    @InjectMocks
    WikiController wikiController;

    @Mock
    WikiClient wikiClient;

    @Test
    void test() throws IOException, InterruptedException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<WikiPage> pages = new ArrayList<>();
        pages.add(WikiPage.create(100));
        when(wikiClient.getPages(1d, 1d)).thenReturn(WikiResponse.ok(pages));

        ResponseEntity<WikiResponse> responseEntity = wikiController.getPages(1d, 1d);

        assertThat(responseEntity.getBody().getPages().size()).isEqualTo(1);
        assertThat(responseEntity.getBody().getPages().get(0).getPageid()).isEqualTo(100);
    }
}
