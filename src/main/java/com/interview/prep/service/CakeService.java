package com.interview.prep.service;

import com.interview.prep.exception.CakeNotFoundException;
import com.interview.prep.model.Cake;

import java.util.List;

public interface CakeService {
    List<Cake> getAllCakes();
    Cake createCake(Cake cake);
    Cake getCakeById(Long id) throws CakeNotFoundException;
    Cake updateCake(Long id, Cake cake) throws CakeNotFoundException;
    void deleteCake(Long id);
}
