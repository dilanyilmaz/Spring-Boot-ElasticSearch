package com.example.spring_elastic_java_client.util;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.val;

import java.util.function.Supplier;

public class ElasticSearchUtil {

  public static Supplier<Query> supplier() {
    Supplier<Query> supplier = () -> Query.of(q -> q.matchAll(matchAllQuery()));
    return supplier;
  }

  public static MatchAllQuery matchAllQuery() {
    val matchAllQuery = new MatchAllQuery.Builder();
    return matchAllQuery.build();
  }

  public static Supplier<Query> supplierWithNameField(String fieldValue) {
    Supplier<Query> supplier = () -> Query.of(q -> q.match(matchQueryWithNameField(fieldValue)));
    return supplier;
  }

  public static MatchQuery matchQueryWithNameField(String fieldValue) {
    val matchQuery = new MatchQuery.Builder();
    return matchQuery.field("name").query(fieldValue).build();
  }

  /**
   * Fuzzy Search
   */
  public static Supplier<Query> createSupplier(String approximateProductName) {
    Supplier<Query> supplier = () -> Query.of(
        q -> q.fuzzy(createFuzzyQuery(approximateProductName)));
    return supplier;
  }

  public static FuzzyQuery createFuzzyQuery(String approximateProductName) {
    val fuzzyQuery = new FuzzyQuery.Builder();
    return fuzzyQuery.field("candy").value(approximateProductName).build();
  }
}
