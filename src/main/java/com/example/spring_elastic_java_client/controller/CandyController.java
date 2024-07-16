package com.example.spring_elastic_java_client.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.spring_elastic_java_client.domain.Candy;
import com.example.spring_elastic_java_client.repository.CandyRepo;
import com.example.spring_elastic_java_client.repository.CandyRepository;
import com.example.spring_elastic_java_client.service.CandyService;
import com.example.spring_elastic_java_client.service.ElasticSearchService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/candy")
public class CandyController {

  @Autowired
  private CandyRepository elasticSearchQuery;
  @Autowired
  private CandyService candyService;
  @Autowired
  private ElasticSearchService elasticSearchService;

  @PostMapping("/{candyId}")
  public ResponseEntity<Candy> getDocumentByID(@PathVariable String candyId) throws IOException {
    Candy candy = elasticSearchQuery.findDocById(candyId);
    log.info("Product Document has been successfully retrieved");
    return new ResponseEntity<>(candy, HttpStatus.OK);
  }
  @GetMapping("all")
  public ResponseEntity<List<Candy>> _findAll() throws IOException{
    List<Candy> products = elasticSearchQuery.findAll();
    log.info("No of Product Document has been successfully retrieved: {}",products.size());
    return new ResponseEntity<>(products,HttpStatus.OK);
  }

  @GetMapping("/fuzzySearch/{approximateProductName}")
  public List<Candy> fuzzySearch(@PathVariable String approximateProductName) throws IOException {
    SearchResponse<Candy> searchResponse = elasticSearchQuery.fuzzySearch(approximateProductName);
    List<Hit<Candy>> hitList = searchResponse.hits().hits();

    List<Candy> productList = new ArrayList<>();
    for(Hit<Candy> hit : hitList){
      productList.add(hit.source());
    }

    return productList;
  }

  @GetMapping("/findAll")
  Iterable<Candy> findAll(){
    return candyService.getProducts();

  }

  @PostMapping("/insert")
  public Candy insertProduct(@RequestBody Candy product){
    return candyService.insertProduct(product);
  }

  @GetMapping("/matchAll")
  public String matchAll() throws IOException {
    SearchResponse<Map> searchResponse =  elasticSearchService.matchAllServices();
    System.out.println(searchResponse.hits().hits().toString());
    return searchResponse.hits().hits().toString();
  }
  //matchAllProducts video content
  @GetMapping("/matchAllProducts")
  public List<Candy> matchAllProducts() throws IOException {
    SearchResponse<Candy> searchResponse =  elasticSearchService.matchAllProductsServices();
    System.out.println(searchResponse.hits().hits().toString());

    List<Hit<Candy>> listOfHits= searchResponse.hits().hits();
    List<Candy> listOfProducts  = new ArrayList<>();
    for(Hit<Candy> hit : listOfHits){
      listOfProducts.add(hit.source());
    }
    return listOfProducts;
  }

  @GetMapping("/matchAllProducts/{fieldValue}")
  public List<Candy> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
    SearchResponse<Candy> searchResponse =  elasticSearchService.matchProductsWithName(fieldValue);
    System.out.println(searchResponse.hits().hits().toString());

    List<Hit<Candy>> listOfHits= searchResponse.hits().hits();
    List<Candy> listOfProducts  = new ArrayList<>();
    for(Hit<Candy> hit : listOfHits){
      listOfProducts.add(hit.source());
    }
    return listOfProducts;
  }
}
