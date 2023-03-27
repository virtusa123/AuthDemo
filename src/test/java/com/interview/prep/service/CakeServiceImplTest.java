package com.interview.prep.service;

import com.interview.prep.exception.CakeAlreadyExistsException;
import com.interview.prep.exception.CakeNotFoundException;
import com.interview.prep.model.Cake;
import com.interview.prep.repository.CakeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CakeServiceImplTest {

    @Mock
    private CakeRepository cakeRepository;

    @InjectMocks
    private CakeServiceImpl productService;

    @Test
    public void testGetAllProducts() {
        List<Cake> cakes = Arrays.asList(
                new Cake(1L, "Product 1", 10.0),
                new Cake(2L, "Product 2", 20.0)
        );
        Mockito.when(cakeRepository.findAll()).thenReturn(cakes);

        List<Cake> result = productService.getAllCakes();

        Assert.assertEquals(2, result.size());
        Assert.assertEquals("Product 1", result.get(0).getName());
        Assert.assertEquals(10.0, result.get(0).getPrice(), 0.001);
        Assert.assertEquals("Product 2", result.get(1).getName());
        Assert.assertEquals(20.0, result.get(1).getPrice(), 0.001);
    }

    @Test
    public void testCreateProduct() {
        Cake cake = new Cake(1L, "Product 1", 10.0);
        Mockito.when(cakeRepository.save(Mockito.any(Cake.class))).thenReturn(cake);

        Cake result = productService.createCake(cake);

        Assert.assertEquals("Product 1", result.getName());
        Assert.assertEquals(10.0, result.getPrice(), 0.001);
    }

    @Test(expected = CakeAlreadyExistsException.class)
    public void testCreateProductAlreadyExists() {
        Cake cake = new Cake(1L, "Product 1", 10.0);
        Mockito.when(cakeRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(cake));

        productService.createCake(cake);
    }

    @Test
    public void testGetProductById() throws CakeNotFoundException {
        Cake cake = new Cake(1L, "Product 1", 10.0);
        Mockito.when(cakeRepository.findById(1L)).thenReturn(Optional.of(cake));

        Cake result = productService.getCakeById(1L);

        Assert.assertEquals("Product 1", result.getName());
        Assert.assertEquals(10.0, result.getPrice(), 0.001);
    }

    @Test(expected = CakeNotFoundException.class)
    public void testGetProductByIdNotFound() throws CakeNotFoundException {
        Mockito.when(cakeRepository.findById(1L)).thenReturn(Optional.empty());

        productService.getCakeById(1L);
    }

    @Test
    public void testUpdateProduct() throws CakeNotFoundException {
        Cake existingCake = new Cake(1L, "Product 1", 10.0);
        Mockito.when(cakeRepository.findById(1L)).thenReturn(Optional.of(existingCake));
        Mockito.when(cakeRepository.save(Mockito.any(Cake.class))).thenReturn(existingCake);

        Cake updatedCake = new Cake(1L, "Product 1 Updated", 20.0);
        Cake result = productService.updateCake(1L, updatedCake);

        Assert.assertEquals("Product 1 Updated", result.getName());
        Assert.assertEquals(20.0, result.getPrice(), 0.001);
    }

    @Test(expected = CakeNotFoundException.class)
    public void testUpdateProductNotFound() throws CakeNotFoundException {
        Cake updatedCake = new Cake(1L, "Product 1 Updated", 20.0);
        Mockito.when(cakeRepository.findById(1L)).thenReturn(Optional.empty());

        productService.updateCake(1L, updatedCake);
    }

    @Test
    public void testDeleteProduct() {
        productService.deleteCake(1L);
        Mockito.verify(cakeRepository, Mockito.times(1)).deleteById(1L);
    }
}
