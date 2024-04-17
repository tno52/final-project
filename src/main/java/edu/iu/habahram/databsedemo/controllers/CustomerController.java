package edu.iu.habahram.databsedemo.controllers;

import edu.iu.habahram.databsedemo.model.Customer;
import org.springframework.web.bind.annotation.*;
import edu.iu.habahram.databsedemo.repository.CustomerRepository;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomerController {
    private CustomerRepository customerRepository;


    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public void add(@RequestBody Customer customer) {
        customerRepository.save(customer);
    }
}
