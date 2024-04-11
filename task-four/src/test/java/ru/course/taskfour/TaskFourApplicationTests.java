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
import ru.course.taskfour.repository.LoginEntityRepositoryable;
import ru.course.taskfour.repository.UserEntityRepositoryable;

import java.io.File;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskFourApplicationTests {
    @Autowired
    private FileContentRepositoryable f;
    @Autowired
    private UserEntityRepositoryable u;
    @Autowired
    private LoginEntityRepositoryable l;

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

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        f.deleteAll();
    }

    @Test
    void entityServiceTest() {

        String path = "src/test/resources/files";
        File file = new File(path);
        String folderPath = file.getAbsolutePath().replace("\\","//");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body("{\"folderName\":\"" + folderPath + "\"}")
                .post("/api/v1/save")
                .then()
                .statusCode(200);

        Assertions.assertEquals(4, f.count());
        Assertions.assertEquals(3, u.count()); //users
        Assertions.assertEquals(4, l.count()); //logins
    }
}