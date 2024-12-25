package com.fiap.springdatamongodb.controller;

import com.fiap.springdatamongodb.model.Artigo;
import com.fiap.springdatamongodb.model.ArtigoComAutoRequest;
import com.fiap.springdatamongodb.model.ArtigoStatusCount;
import com.fiap.springdatamongodb.model.Autor;
import com.fiap.springdatamongodb.model.AutorTotalArtigo;
import com.fiap.springdatamongodb.service.ArtigoService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/artigos")
public class ArtigoController {

  @Autowired
  private ArtigoService artigoService;

/*  @PostMapping
  public Artigo criar(@RequestBody Artigo artigo) {
    return artigoService.criar(artigo);
  }*/

  @PostMapping
  public ResponseEntity<?> criar(@RequestBody Artigo artigo) {
    return artigoService.criar(artigo);
  }

  @PutMapping("/atualiza-artigo/{id}")
  public ResponseEntity<?> atualizarArtigo(@PathVariable("id") String id, @Valid @RequestBody Artigo artigo) {
    return artigoService.atualizarArtigo(id,artigo);
  }

  @GetMapping
  public List<Artigo> obterTodos() {
    return artigoService.obterTodos();
  }

  @ExceptionHandler(OptimisticLockingFailureException.class)
  public ResponseEntity<String> handleOptimisticLockingFailureException(
      OptimisticLockingFailureException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body("Erro de concorrência: O artigo foi atualizado por outro usuario, Por favor tente novamente");
  }

  @GetMapping("/{codigo}")
  public Artigo obterArtigo(@PathVariable String codigo) {
    return artigoService.obterArtigo(codigo);
  }

  // No MongoDB  Consigo utilizar somente com replicas sets ou clusters com sharding.
//  @PostMapping
//  public ResponseEntity<?> criarArtigoComAutor(@RequestBody ArtigoComAutoRequest request) {
//
//    Artigo artigo = request.getArtigo();
//    Autor autor = request.getAutor();
//
//    return this.artigoService.criarArtigoComAutor(artigo,autor);
//
//  }

  // No MongoDB  Consigo utilizar somente com replicas sets ou clusters com sharding.
//  @DeleteMapping("delete-artigo-autor")
//  public void excluirArtigoEAutor(@RequestBody Artigo artigo) {
//    this.artigoService.excluirArtigoEAutor(artigo);
//  }

  @GetMapping("/maiordata")
  public List<Artigo> findByDataGreaterThan(@RequestParam("data")LocalDateTime data) {
    return artigoService.findByDataGreaterThan(data);
  }

  @GetMapping("/data-status")
  public List<Artigo> findByDataAndStatus(@RequestParam("data") LocalDateTime data,
                                          @RequestParam("status") Integer status) {
    return artigoService.findByDataAndStatus(data, status);
  }

  @PutMapping
  public void atualizar(@RequestBody Artigo artigo) {
    artigoService.atualizar(artigo);
  }

  @PutMapping("/{codigo}")
  public void atualizarArtigo(@PathVariable String codigo, @RequestBody String url) {
    artigoService.atualizarArtigo(codigo, url);
  }

  @DeleteMapping("{codigo}")
  public void deleteArtigo(@PathVariable String codigo) {
    artigoService.deleteById(codigo);
  }

  @DeleteMapping("/delete/{codigo}")
  public void deleteArtigoById(@PathVariable String codigo) {
    artigoService.deleteArtigoById(codigo);
  }

  @GetMapping("/status-maiordata")
  public List<Artigo> findByStatusAndDataGreaterThan(@RequestParam("status") Integer status,
                                                     @RequestParam("data") LocalDateTime data) {
    return artigoService.findByStatusAndDataGreaterThan(status, data);
  }

  @GetMapping("/range-data-hora")
  public List<Artigo> obterArtigoPorDataHora(@RequestParam("de") LocalDateTime de,
                                             @RequestParam("ate") LocalDateTime ate) {
    return artigoService.obterArtigoPorDataHora(de, ate);
  }

  @GetMapping("/artigo-complexo")
  public List<Artigo> encontrarArtigosComplexos(@RequestParam("status") Integer status,
                                                @RequestParam("data") LocalDateTime data,
                                                @RequestParam("titulo") String titulo){
    return artigoService.encontrarArtigosComplexos(status, data, titulo);

  }

  // Obter arquivos paginados
  @GetMapping("/obter-arquivos-paginados")
  public ResponseEntity<Page<Artigo>> listaArtigos(Pageable pageable) {
    Page<Artigo> artigos = artigoService.listaArtigos(pageable);
    return ResponseEntity.ok(artigos);
  }

  @GetMapping("/status-ordenados")
  public List<Artigo> findByStatusOrderByTituloAsc(@RequestParam("status") Integer status) {
    return artigoService.findByStatusOrderByTitleAsc(status);
  }

  @GetMapping("/status-query-ordenacao")
  public List<Artigo> obterArtigoPorStatusComOrdenacao(@RequestParam("status") Integer status) {
    return artigoService.obterArtigoPorStatusComOrdenacao(status);
  }

  @GetMapping("/buscatexto")
  public List<Artigo> findByTexto(@RequestParam("searchTerm") String termo){
    return artigoService.findByTexto(termo);
  }

  @GetMapping("/contar-artigo")
  public List<ArtigoStatusCount> contarArtigosPorStatus() {
    return artigoService.contarArtigosPorStatus();
  }

  @GetMapping("/calcular-total-artigo-por-autor-no-periodo")
  public List<AutorTotalArtigo> calcularTotalArtigosPorAutoNoPeriodo(
      @RequestParam("dataInicial") LocalDate dataInicial,
      @RequestParam("dataFinal") LocalDate dataFinal) {
    return artigoService.calcularTotalArtigosPorAutoNoPeriodo(dataInicial,dataFinal);
  }

}
