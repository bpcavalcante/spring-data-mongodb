package com.fiap.springdatamongodb.controller;

import com.fiap.springdatamongodb.model.Autor;
import com.fiap.springdatamongodb.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/autores")
public class AutorController {

  @Autowired
  private AutorService autorService;

  @PostMapping
  public Autor criar(@RequestBody Autor autor) {
    return autorService.criar(autor);
  }

  @GetMapping("/{codigo}")
  public Autor buscarPorCodigo(@PathVariable String codigo) {
    return autorService.obterAutorPorCodigo(codigo);
  }

}
