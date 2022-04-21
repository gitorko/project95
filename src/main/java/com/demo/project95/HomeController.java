package com.demo.project95;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
public class HomeController {

    @Autowired
    CustomerRepository repository;

    @GetMapping("/customer")
    public Iterable<Customer> getAllCustomer() {
        return repository.findAll();
    }


    @GetMapping("/customer/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping("/customer")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @GetMapping("/time")
    public String getServerTime() {
        log.info("Getting server time!");
        String podName = System.getenv("HOSTNAME");
        return "Pod: " + podName + " : " + LocalDateTime.now();
    }
}
