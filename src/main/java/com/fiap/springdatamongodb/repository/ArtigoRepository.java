package com.fiap.springdatamongodb.repository;

import com.fiap.springdatamongodb.model.Artigo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtigoRepository extends MongoRepository<Artigo, String> {

  public void deleteById(String id);

  public List<Artigo> findByStatusAndDataGreaterThan(Integer status, LocalDateTime data);
}
