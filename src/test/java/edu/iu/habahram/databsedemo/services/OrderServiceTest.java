package edu.iu.habahram.databsedemo.services;

import edu.iu.habahram.databsedemo.model.Order;
import edu.iu.habahram.databsedemo.repository.OrderRepository;
import org.aspectj.lang.annotation.After;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTest {

    @Autowired
    OrderService orderService;


    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void setUp() {
        postgres.start();
    }

    @AfterAll
    static void tearDown() {
        postgres.stop();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void add() {
        Order order = new Order();
        order.setFlowerId(2);
        order.setRecipientName("Jane");
        order.setTotalCost(50.56f);
        order.setCustomerUserName("John");
        orderService.add(order);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAllByCustomer() {
        List<Order> orders = orderService.findAllByCustomer("John");
        Assert.assertEquals(orders.size(), 1);
        Assert.assertEquals("Jane", orders.get(0).getRecipientName());
        Assert.assertEquals(2, orders.get(0).getFlowerId().intValue());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void searchByFlowerId() {
        Order order = new Order();
        order.setFlowerId(2);
        List<Order> result = orderService.search(order);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(2, result.get(0).getFlowerId().intValue());
    }
}