package com.fiap.springdatamongodb.model;

import lombok.Data;

// Essa clase representa o resultado de uma agregação
@Data
public class ArtigoStatusCount {

  private Integer status;
  private Long quantidade;

}
