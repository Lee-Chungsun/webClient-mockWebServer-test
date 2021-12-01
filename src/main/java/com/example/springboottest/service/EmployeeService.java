package com.example.springboottest.service;

import com.example.springboottest.model.Employee;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class EmployeeService {

    private final WebClient webClient;

    public EmployeeService(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Mono<Employee> getEmployeeById(Long employeeId) {
        return webClient.get()
                .uri("/employee/{id}", employeeId)
                .retrieve()
                .bodyToMono(Employee.class);
    }
}
