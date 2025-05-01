package com.jsamkt.learn;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

//@MicronautTest
//public class HelloControllerTest {
//
//    @Inject
//    @Client("/")
//    HttpClient client;
//
//    @Test
//    void testHelloEndpoint() {
//        HttpRequest<?> request = HttpRequest.GET("/hello").accept(MediaType.APPLICATION_JSON);
//        String response = client.toBlocking().retrieve(request);
//        assert response.equals("{\"message\": \"Hello World\"}");
//    }
//}
