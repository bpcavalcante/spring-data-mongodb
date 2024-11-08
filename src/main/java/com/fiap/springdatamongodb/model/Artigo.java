package com.fiap.springdatamongodb.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Artigo {

  @Id
  private String codigo;
  private String titulo;
  private LocalDateTime data;
  private String texto;
  private String url;
  private Integer status;
  @DBRef
  private Autor autor;

}
