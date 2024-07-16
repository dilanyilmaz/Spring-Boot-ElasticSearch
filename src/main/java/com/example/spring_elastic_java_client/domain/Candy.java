package com.example.spring_elastic_java_client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "favorite_candy")
public class Candy {

  @Id
  private Integer id;

  @Field(type = FieldType.Text, name = "first_name")
  private String first_name;

  @Field(type = FieldType.Text, name = "candy")
  private String candy;
}
