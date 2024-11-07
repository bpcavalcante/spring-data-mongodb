package com.fiap.springdatamongodb.service;

import com.fiap.springdatamongodb.model.Artigo;
import java.time.LocalDateTime;
import java.util.List;

public interface ArtigoService {

  public List<Artigo> obterTodos();

  public Artigo obterArtigo(String codigo);

  public Artigo criar(Artigo artigo);

  public List<Artigo> findByDataGreaterThan(LocalDateTime data);

}
