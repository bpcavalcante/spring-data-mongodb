package com.fiap.springdatamongodb.service;

import com.fiap.springdatamongodb.model.Autor;

public interface AutorService {

  public Autor criar(Autor autor);

  public Autor obterAutorPorCodigo(String codigo);

}
