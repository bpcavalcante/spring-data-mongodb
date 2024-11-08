package com.fiap.springdatamongodb.repository;

import com.fiap.springdatamongodb.model.Autor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AutorRepository extends MongoRepository<Autor, String> {
}
