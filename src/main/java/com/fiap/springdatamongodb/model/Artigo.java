package com.fiap.springdatamongodb.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
  @NotBlank(message = "O titulo do artigo não pode estar em branco")
  private String titulo;
  @NotNull(message = "A data do artigo não pode ser nula")
  private LocalDateTime data;
  // Estou informando pro MongoDB que esse campo é indexado
  // db.artigo.createIndex({texto: "text"}) Preciso tbm criar um index dentro meu banco
  @NotBlank(message = "O texto do artigo não pode estar em branco")
  @TextIndexed
  private String texto;
  private String url;
  @NotNull(message = "O status do artigo não pode ser nulo")
  private Integer status;
  @DBRef
  private Autor autor;
  // Essa anotacao garante que o documento vai estar dentro de uma versao , garantindo que estou controlando
  // a concorrencia
  @Version
  private Long version;

}
