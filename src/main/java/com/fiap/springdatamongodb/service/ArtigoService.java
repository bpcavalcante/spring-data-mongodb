package com.fiap.springdatamongodb.service;

import com.fiap.springdatamongodb.model.Artigo;
import com.fiap.springdatamongodb.model.ArtigoStatusCount;
import com.fiap.springdatamongodb.model.Autor;
import com.fiap.springdatamongodb.model.AutorTotalArtigo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ArtigoService {

  public List<Artigo> obterTodos();

  public Artigo obterArtigo(String codigo);

/*
  public Artigo criar(Artigo artigo);
*/

//  public ResponseEntity<?> criar(Artigo artigo);

  public ResponseEntity<?> criarArtigoComAutor(Artigo artigo, Autor autor);

  public ResponseEntity<?> atualizarArtigo(String id, Artigo artigo);

  public List<Artigo> findByDataGreaterThan(LocalDateTime data);

  public List<Artigo> findByDataAndStatus(LocalDateTime data, Integer status);

  public void atualizar(Artigo updateArtigo);

  public void atualizarArtigo(String id, String novaUrl);

  public void deleteById(String id);

  public void deleteArtigoById(String id);

  public List<Artigo> findByStatusAndDataGreaterThan(Integer status, LocalDateTime data);

  public List<Artigo> obterArtigoPorDataHora(LocalDateTime de, LocalDateTime ate);

  public List<Artigo> encontrarArtigosComplexos(Integer status, LocalDateTime data, String titulo);

  Page<Artigo> listaArtigos(Pageable pageable);

  public List<Artigo> findByStatusOrderByTitleAsc(Integer status);

  public List<Artigo> obterArtigoPorStatusComOrdenacao(Integer status);

  public List<Artigo> findByTexto(String searchTerm);

  public List<ArtigoStatusCount> contarArtigosPorStatus();

  public List<AutorTotalArtigo> calcularTotalArtigosPorAutoNoPeriodo(LocalDate dataInicial, LocalDate dataFinal);

}
