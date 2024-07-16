package com.example.spring_elastic_java_client.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.spring_elastic_java_client.domain.Candy;
import com.example.spring_elastic_java_client.util.ElasticSearchUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CandyRepository {

  public static final String CANDY = "favorite_candy";
  @Autowired
  private ElasticsearchClient elasticsearchClient;
  private ElasticSearchUtil elasticSearchUtil;

  public Candy findDocById(String candyId) throws IOException {
    return elasticsearchClient.get(g -> g.index(CANDY).id(candyId), Candy.class).source();
  }

  public List<Candy> findAll() throws IOException {
    SearchRequest request = SearchRequest.of(s -> s.index(CANDY));
    SearchResponse<Candy> response = elasticsearchClient.search(request, Candy.class);

    List<Candy> candys = new ArrayList<>();
    response.hits().hits().stream().forEach(object -> {
      candys.add(object.source());
    });

    return candys;
  }

  public List<Candy> _findAll() throws IOException {
    SearchRequest searchRequest = SearchRequest.of(s -> s.index(CANDY));
    SearchResponse<Candy> searchx = elasticsearchClient.search(searchRequest, Candy.class);
    List<Candy> c = new ArrayList<>();
    searchx.hits().hits().stream().forEach(o -> {
      c.add(o.source());
    });
    return c;
  }

  public SearchResponse<Candy> fuzzySearch(String approximateProductName) throws IOException {
    //Supplier elastic sorgusu üretmek için kullanılır.
    Supplier<Query> supplier = elasticSearchUtil.createSupplier(approximateProductName);

    SearchResponse<Candy> response = elasticsearchClient.search(
        s -> s.index("favorite_candy").query(supplier.get()), Candy.class);

    return response;
  }

}
