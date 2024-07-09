package com.example.spring_elastic_java_client.controller;

import com.example.spring_elastic_java_client.domain.Product;
import com.example.spring_elastic_java_client.repository.ProductRepository;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/products")
public class ProductController {

  @Autowired
  private ProductRepository elasticSearchQuery;

  @PostMapping
  public ResponseEntity<String> createOrUpdateDocument(@RequestBody Product product) throws IOException{
    String response = elasticSearchQuery.createOrUpdateDocument(product);
  return  new ResponseEntity<>(response, HttpStatus.OK);
  }
  @PostMapping("/bulk")
  public ResponseEntity<String> bulk(@RequestBody List<Product> products) throws IOException{
    String response = elasticSearchQuery.bulkSave(products);
    return new ResponseEntity<>(response,HttpStatus.OK);
  }
  @PostMapping("/{productId}")
  public ResponseEntity<Product> getDocumentByID(@PathVariable String productId) throws IOException{
    Product product = elasticSearchQuery.findDocById(productId);
    log.info("Product Document has been successfully retrieved");
    return new ResponseEntity<>(product,HttpStatus.OK);
  }
  @DeleteMapping("/{productId}")
  public ResponseEntity<String> deleteDocumentByID(@PathVariable String productId) throws IOException{
    String message = elasticSearchQuery.deleteDocById(productId);
    log.info("Product Document has been successfully deleted. Message: {}",message);
    return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
  }
  @GetMapping
  public ResponseEntity<List<Product>> findAll() throws IOException{
    List<Product> products = elasticSearchQuery.findAll();
    log.info("No of Product Document has been successfully retrieved: {}",products.size());
    return new ResponseEntity<>(products,HttpStatus.OK);
  }
}
