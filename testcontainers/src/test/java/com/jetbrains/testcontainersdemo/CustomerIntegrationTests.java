package com.jetbrains.testcontainersdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Testcontainers
public class CustomerIntegrationTests {

    @Autowired
    private CustomerDao customerDao;

    //    @Container
    private static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:latest").withReuse(true);

    //if you need to reuse the container add "testcontainers.reuse.enable=true" line in to the .testcontainer.properties file inside the home directory

//    private static GenericContainer genericContainer = new GenericContainer("myimage:tag");

    @BeforeAll
    public static void setup() {
        container.start();
    }

    @DynamicPropertySource
    public static void overideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    void test_select_count() {

//        container.withClasspathResourceMapping("application.propertiess", "/tmp/application.propperties", BindMode.READ_ONLY);

//        container.withFileSystemBind("/home/{path}", "/temp/...", BindMode.READ_ONLY);
//
//        Integer portInYourMachine = container.getMappedPort(3306);
//
//        container.execInContainer("ls")

        List<Customer> customers = customerDao.findAll();
        assertThat(customers).hasSize(2);
    }
}
