package com.example.spring_elastic_java_client.service;

import com.example.spring_elastic_java_client.domain.Candy;
import com.example.spring_elastic_java_client.repository.CandyRepo;
import com.example.spring_elastic_java_client.repository.CandyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandyService {
  @Autowired
  private CandyRepo candyRepo;

  public Iterable<Candy> getProducts() {
    return candyRepo.findAll();
  }

  public Candy insertProduct(Candy product) {
    return candyRepo.save(product);
  }

  public Candy updateProduct(Candy product, int id) {
    Candy product1  = candyRepo.findById(id).get();
    product1.setFirst_name(product.getFirst_name());
    return product1;
  }

  public void deleteProduct(int id ) {
    candyRepo.deleteById(id);
  }
}
