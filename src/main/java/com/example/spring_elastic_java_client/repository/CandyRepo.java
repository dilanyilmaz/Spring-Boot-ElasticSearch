package com.example.spring_elastic_java_client.repository;

import com.example.spring_elastic_java_client.domain.Candy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CandyRepo extends ElasticsearchRepository<Candy,Integer> {}

