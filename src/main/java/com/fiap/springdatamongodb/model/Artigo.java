package com.fiap.springdatamongodb.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Artigo {

  @Id
  private String codigo;
  private String titulo;
  private LocalDateTime data;
  // Estou informando pro MongoDB que esse campo é indexado
  // db.artigo.createIndex({texto: "text"}) Preciso tbm criar um index dentro meu banco
  @TextIndexed
  private String texto;
  private String url;
  private Integer status;
  @DBRef
  private Autor autor;
  // Essa anotacao garante que o documento vai estar dentro de uma versao , garantindo que estou controlando
  // a concorrencia
  @Version
  private Long version;

}
