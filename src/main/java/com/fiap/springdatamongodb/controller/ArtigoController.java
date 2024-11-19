package com.fiap.springdatamongodb.controller;

import com.fiap.springdatamongodb.model.Artigo;
import com.fiap.springdatamongodb.service.ArtigoService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @PostMapping
  public Artigo criar(@RequestBody Artigo artigo) {
    return artigoService.criar(artigo);
  }

  @GetMapping
  public List<Artigo> obterTodos() {
    return artigoService.obterTodos();
  }

  @GetMapping("/{codigo}")
  public Artigo obterArtigo(@PathVariable String codigo) {
    return artigoService.obterArtigo(codigo);
  }

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

}
