package com.interview.prep.controller;

import com.interview.prep.exception.CakeNotFoundException;
import com.interview.prep.model.Cake;
import com.interview.prep.service.CakeServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cake")
public class CakeController {

    private final CakeServiceImpl productService;

    public CakeController(CakeServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Cake> getAllProducts() {
        return productService.getAllCakes();
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Cake createProduct(@RequestBody Cake cake) {
        return productService.createCake(cake);
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Cake getProductById(@PathVariable Long id) throws CakeNotFoundException {
        return productService.getCakeById(id);
    }

    @PutMapping("/products/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Cake updateProduct(@PathVariable Long id, @RequestBody Cake cake) throws CakeNotFoundException {
        return productService.updateCake(id, cake);
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteCake(id);
    }

}

