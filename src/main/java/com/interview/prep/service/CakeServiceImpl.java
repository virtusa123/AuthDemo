package com.interview.prep.service;

import com.interview.prep.exception.CakeAlreadyExistsException;
import com.interview.prep.exception.CakeNotFoundException;
import com.interview.prep.model.Cake;
import com.interview.prep.repository.CakeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CakeServiceImpl implements CakeService {

    private final CakeRepository cakeRepository;

    public CakeServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    public List<Cake> getAllCakes() {
        return cakeRepository.findAll();
    }

    public Cake createCake(Cake cake) {
        cakeRepository.findByName(cake.getName())
                .ifPresent(p -> {
                    try {
                        throw new CakeAlreadyExistsException("Cake already exists");
                    } catch (CakeAlreadyExistsException e) {
                        throw new RuntimeException(e);
                    }
                });
        return cakeRepository.save(cake);
    }

    public Cake getCakeById(Long id) throws CakeNotFoundException {
        return cakeRepository.findById(id)
                .orElseThrow(() -> new CakeNotFoundException("Cake not found"));
    }

    public Cake updateCake(Long id, Cake cake) throws CakeNotFoundException {
        return cakeRepository.findById(id)
                .map(p -> {
                    p.setName(cake.getName());
                    p.setPrice(cake.getPrice());
                    return cakeRepository.save(p);
                })
                .orElseThrow(() -> new CakeNotFoundException("Cake not found"));
    }

    public void deleteCake(Long id) {
        cakeRepository.deleteById(id);
    }

}

