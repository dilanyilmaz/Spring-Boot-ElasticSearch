package com.example.spring_elastic_java_client.controller;

import com.example.spring_elastic_java_client.domain.Candy;
import com.example.spring_elastic_java_client.repository.CandyRepository;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/candy")
public class CandyController {

  @Autowired
  private CandyRepository elasticSearchQuery;

  @PostMapping("/{candyId}")
  public ResponseEntity<Candy> getDocumentByID(@PathVariable String candyId) throws IOException {
    Candy candy = elasticSearchQuery.findDocById(candyId);
    log.info("Product Document has been successfully retrieved");
    return new ResponseEntity<>(candy, HttpStatus.OK);
  }
  @GetMapping("all")
  public ResponseEntity<List<Candy>> findAll() throws IOException{
    List<Candy> products = elasticSearchQuery.findAll();
    log.info("No of Product Document has been successfully retrieved: {}",products.size());
    return new ResponseEntity<>(products,HttpStatus.OK);
  }
}
