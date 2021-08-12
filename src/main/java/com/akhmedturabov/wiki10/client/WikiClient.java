package com.akhmedturabov.wiki10.client;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

import com.akhmedturabov.wiki10.entity.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import org.json.*;
import org.springframework.stereotype.*;

@Component
public class WikiClient {

    static final String ENDPOINT = "https://en.wikipedia.org/w/api.php";

    public WikiResponse getPages(double lat, double lon) throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("action", "query");
        parameters.put("prop", "info");
        parameters.put("inprop", "url");
        parameters.put("generator", "geosearch");
        parameters.put("ggsradius", "10000");
        parameters.put("ggslimit", "10");
        parameters.put("ggscoord", lat + "|" + lon);
        parameters.put("format", "json");

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ENDPOINT))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();

        return getWikiResponse(client.send(request, HttpResponse.BodyHandlers.ofString()));
    }

    private WikiResponse getWikiResponse(HttpResponse<?> response) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(response.body().toString());
        if (jsonObject.has("error")) {
            return WikiResponse.error(jsonObject.getJSONObject("error").getString("info"));
        } else {
            List<WikiPage> pages = new ArrayList<>();
            if (jsonObject.has("query")
                    && jsonObject.getJSONObject("query").has("pages")) {
                ObjectMapper objectMapper = new ObjectMapper();
                TypeReference<HashMap<String, WikiPage>> typeRef = new TypeReference<>() {};
                pages = objectMapper.readValue(jsonObject.getJSONObject("query").getJSONObject("pages").toString(), typeRef)
                        .values().stream().collect(Collectors.toList());
            }
            return WikiResponse.ok(pages);
        }
    }
}
