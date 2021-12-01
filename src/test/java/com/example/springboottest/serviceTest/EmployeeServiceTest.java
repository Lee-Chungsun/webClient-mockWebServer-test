package com.example.springboottest.serviceTest;

import com.example.springboottest.model.Employee;
import com.example.springboottest.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    private static MockWebServer mockBackEnd;
    private static ObjectMapper objectMapper;
    private static EmployeeService employeeService;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        objectMapper = new ObjectMapper();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        employeeService = new EmployeeService(baseUrl);
    }

    @Test
    void getEmployeeById() throws Exception {
        Employee mockEmployee = new Employee(100L, "Adam", "Sandler", 32L);

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockEmployee))
                .addHeader("Content-Type", "application/json"));

        Mono<Employee> employeeMono = employeeService.getEmployeeById(100L);

        StepVerifier.create(employeeMono)
                .expectNextMatches(employee -> employee.getAge().equals(32L))
                .verifyComplete();
    }
}
