package ru.netology.springbootdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    private static GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoadsDevApp() {
        ResponseEntity<String> responseFromDevApp = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);
        System.out.println(responseFromDevApp.getBody());
        Assertions.assertEquals("Current profile is dev", responseFromDevApp.getBody());

    }
    @Test
    void contextLoadsProdApp() {
        ResponseEntity<String> responseFromProdApp = restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(8081)+ "/profile", String.class);
        System.out.println(responseFromProdApp.getBody());
        Assertions.assertEquals("Current profile is production", responseFromProdApp.getBody());
    }
}
