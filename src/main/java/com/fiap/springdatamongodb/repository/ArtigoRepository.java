package com.fiap.springdatamongodb.repository;

import com.fiap.springdatamongodb.model.Artigo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtigoRepository extends MongoRepository<Artigo, String> {
}
