package com.fiap.springdatamongodb.repository;

import com.fiap.springdatamongodb.model.Artigo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ArtigoRepository extends MongoRepository<Artigo, String> {

  public void deleteById(String id);

  public List<Artigo> findByStatusAndDataGreaterThan(Integer status, LocalDateTime data);

  @Query("{ $and: [ {'data': { $gte: ?0 }}, {'data':  { $lte:  ?1}}] }")
  public List<Artigo> obterArtigoPorDataHora(LocalDateTime de, LocalDateTime ate);

  Page<Artigo> findAll(Pageable pageable);

  // Query method , proprio mongodb cria a query
  public List<Artigo> findByStatusOrderByTituloAsc(Integer status);

  // O numero 1 indica que a ordenação será de forma ascendente , caso queremos fazer descendente colocamos -1
  @Query(value = "{ 'status': {$eq : ?0 } }", sort = "{'titulo' :  1}")
  public List<Artigo> obterArtigoPorStatusComOrdenacao(Integer status);

}
