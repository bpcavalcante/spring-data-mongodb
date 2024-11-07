package com.fiap.springdatamongodb.service.impl;

import com.fiap.springdatamongodb.model.Artigo;
import com.fiap.springdatamongodb.model.Autor;
import com.fiap.springdatamongodb.repository.ArtigoRepository;
import com.fiap.springdatamongodb.repository.AutorRepository;
import com.fiap.springdatamongodb.service.ArtigoService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ArtigoServiceImpl implements ArtigoService {

  private final MongoTemplate mongoTemplate;

  public ArtigoServiceImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Autowired
  private ArtigoRepository artigoRepository;

  @Autowired
  private AutorRepository autorRepository;

  @Override
  public List<Artigo> obterTodos() {
    return artigoRepository.findAll();
  }

  @Override
  public Artigo obterArtigo(String codigo) {
    return artigoRepository.findById(codigo).orElseThrow(() -> new IllegalArgumentException("Artigo nao encontrado"));
  }

  @Override
  public Artigo criar(Artigo artigo) {

    if (artigo.getAutor().getCodigo() != null){
      Autor autor = autorRepository.findById(artigo.getAutor().getCodigo())
          .orElseThrow(() -> new IllegalArgumentException("Autor nao encontrado"));

      artigo.setAutor(autor);
    } else {
      artigo.setAutor(null);
    }

    return artigoRepository.save(artigo);
  }

  @Override
  public List<Artigo> findByDataGreaterThan(LocalDateTime data) {

    // Estou definindo meu criterio de busca , onde a data seja maior que a data informada no parametro
    // Criteria é utilizado para criar criterios de consultas , exemplo olhar igualdade , comparação
    // Query utilizamos esta classe para construir consultas completas , onde combinamos com os criterios
    Query query = new Query(Criteria.where("data").gt(data));
    return mongoTemplate.find(query, Artigo.class);

  }

}
