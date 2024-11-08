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
import org.springframework.data.mongodb.core.query.Update;
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

  @Override
  public List<Artigo> findByDataAndStatus(LocalDateTime data, Integer status) {

    Query query = new Query(Criteria.where("data")
        .is(data).and("status").is(status));

    return mongoTemplate.find(query, Artigo.class);
  }

  @Override
  public void atualizar(Artigo updateArtigo) {
    // Se o documento existe na coleção ele sobreescreve, alterando somente os dados que mudou
    artigoRepository.save(updateArtigo);
  }

  @Override
  public void atualizarArtigo(String id, String novaUrl) {
    // Criterio de busca pelo "_id"
    Query query = new Query(Criteria.where("_id").is(id));
    // Definindo os campos que serão atualizados
    Update update = new Update().set("url", novaUrl);
    // Executo a atualização
    mongoTemplate.updateFirst(query, update, Artigo.class);
  }

  @Override
  public void deleteById(String id) {
    artigoRepository.deleteById(id);
  }

  @Override
  public void deleteArtigoById(String id) {
    Query query = new Query(Criteria.where("_id").is(id));
    mongoTemplate.remove(query, Artigo.class);
  }

}
