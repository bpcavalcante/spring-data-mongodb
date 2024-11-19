package com.fiap.springdatamongodb.service;

import com.fiap.springdatamongodb.model.Artigo;
import java.time.LocalDateTime;
import java.util.List;

public interface ArtigoService {

  public List<Artigo> obterTodos();

  public Artigo obterArtigo(String codigo);

  public Artigo criar(Artigo artigo);

  public List<Artigo> findByDataGreaterThan(LocalDateTime data);

  public List<Artigo> findByDataAndStatus(LocalDateTime data, Integer status);

  public void atualizar(Artigo updateArtigo);

  public void atualizarArtigo(String id, String novaUrl);

  public void deleteById(String id);

  public void deleteArtigoById(String id);

  public List<Artigo> findByStatusAndDataGreaterThan(Integer status, LocalDateTime data);


}
