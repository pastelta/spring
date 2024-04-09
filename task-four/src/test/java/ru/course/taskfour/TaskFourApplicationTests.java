package ru.course.taskfour;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.course.taskfour.repository.FileContentRepositoryable;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskFourApplicationTests {
    @Test
    void contextLoads() {
    }

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private FileContentRepositoryable f;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        f.deleteAll();
    }

    @Test
    void entityServiceTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body("{\"folderName\":\"C://Users//Татьяна//SpringDemo//task-four//src//main//resources//files\"}")
                .post("/api/v1/save")
                .then()
                .statusCode(200);

        Assertions.assertEquals(4, f.count());
    }

}
