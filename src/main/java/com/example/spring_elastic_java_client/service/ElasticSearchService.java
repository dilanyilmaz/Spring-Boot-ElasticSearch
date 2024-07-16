package com.example.spring_elastic_java_client.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.spring_elastic_java_client.domain.Candy;
import com.example.spring_elastic_java_client.util.ElasticSearchUtil;
import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {
  @Autowired
  private ElasticsearchClient elasticsearchClient;

  public SearchResponse<Map> matchAllServices() throws IOException {
    Supplier<Query> supplier  = ElasticSearchUtil.supplier();
    SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class);
    System.out.println("elasticsearch query is "+supplier.get().toString());
    return searchResponse;
  }
  //matchAllProducts video content

  public SearchResponse<Candy> matchAllProductsServices() throws IOException {
    Supplier<Query> supplier  = ElasticSearchUtil.supplier();
    SearchResponse<Candy> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()),Candy.class);
    System.out.println("elasticsearch query is "+supplier.get().toString());
    return searchResponse;
  }

  //matchProductWithName

  public SearchResponse<Candy> matchProductsWithName(String fieldValue) throws IOException {
    Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
    SearchResponse<Candy> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()),Candy.class);
    System.out.println("elasticsearch query is "+supplier.get().toString());
    return searchResponse;
  }
}
