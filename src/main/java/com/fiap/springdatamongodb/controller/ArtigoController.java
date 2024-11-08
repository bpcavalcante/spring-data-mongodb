package com.fiap.springdatamongodb.controller;

import com.fiap.springdatamongodb.model.Artigo;
import com.fiap.springdatamongodb.service.ArtigoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
