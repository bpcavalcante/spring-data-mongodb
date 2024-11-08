package com.fiap.springdatamongodb.service.impl;

import com.fiap.springdatamongodb.model.Autor;
import com.fiap.springdatamongodb.repository.AutorRepository;
import com.fiap.springdatamongodb.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServiceImpl implements AutorService {

  @Autowired
  private AutorRepository autorRepository;

  @Override
  public Autor criar(Autor autor) {
    return autorRepository.save(autor);
  }

  @Override
  public Autor obterAutorPorCodigo(String codigo) {
    return autorRepository.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
  }
}
