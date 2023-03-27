package com.interview.prep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.prep.model.Cake;
import com.interview.prep.service.CakeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CakeController.class)
public class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CakeServiceImpl productService;

    @Test
    public void testGetAllProducts() throws Exception {
        Cake cake1 = new Cake(1L,"Product 1", 10.0);
        Cake cake2 = new Cake(2L,"Product 2", 20.0);
        List<Cake> cakes = Arrays.asList(cake1, cake2);
        when(productService.getAllCakes()).thenReturn(cakes);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(cakes)));
    }

    @Test
    public void testGetProductById() throws Exception {
        Long productId = 1L;
        Cake cake = new Cake(1L,"Product 1", 10.0);
        when(productService.getCakeById(productId)).thenReturn(cake);
        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(cake)));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Cake cake = new Cake(1L,"Product 1", 10.0);
        when(productService.createCake(cake)).thenReturn(cake);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cake)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        Cake cake = new Cake(1L,"Product 1", 10.0);
        Cake updatedCake = new Cake(2L,"Product 2", 20.0);
        when(productService.updateCake(productId, updatedCake)).thenReturn(updatedCake);
        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedCake)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;
        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isOk());
        verify(productService, Mockito.times(1)).deleteCake(productId);
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



