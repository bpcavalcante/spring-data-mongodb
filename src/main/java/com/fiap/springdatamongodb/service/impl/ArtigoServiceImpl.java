package com.fiap.springdatamongodb.service.impl;

import com.fiap.springdatamongodb.model.Artigo;
import com.fiap.springdatamongodb.model.ArtigoStatusCount;
import com.fiap.springdatamongodb.model.Autor;
import com.fiap.springdatamongodb.model.AutorTotalArtigo;
import com.fiap.springdatamongodb.repository.ArtigoRepository;
import com.fiap.springdatamongodb.repository.AutorRepository;
import com.fiap.springdatamongodb.service.ArtigoService;
import com.mongodb.client.MongoClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtigoServiceImpl implements ArtigoService {

  private final MongoTemplate mongoTemplate;
  @Autowired
  private MongoClient mongo;

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
    return artigoRepository.findById(codigo)
        .orElseThrow(() -> new IllegalArgumentException("Artigo nao encontrado"));
  }

  @Override
  public ResponseEntity<?> criar(Artigo artigo) {

    if (artigo.getAutor().getCodigo() != null) {
      Autor autor = autorRepository.findById(artigo.getAutor().getCodigo())
          .orElseThrow(() -> new IllegalArgumentException("Autor nao encontrado"));

      artigo.setAutor(autor);
    } else {
      artigo.setAutor(null);
    }

    try {
      this.artigoRepository.save(artigo);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (DuplicateKeyException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Artigo já existe na coleção");
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Erro ao criar artigo" + ex.getMessage());
    }

  }

  @Override
  public ResponseEntity<?> atualizarArtigo(String id, Artigo artigo) {
    try {
      Artigo existenteArtigo = artigoRepository.findById(id).orElse(null);

      if (existenteArtigo == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artigo nao encontrado");
      }

      existenteArtigo.setTitulo(artigo.getTitulo());
      existenteArtigo.setData(artigo.getData());
      existenteArtigo.setTexto(artigo.getTexto());

      artigoRepository.save(existenteArtigo);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Erro ao atualizar artigo" + ex.getMessage());
    }

  }

  // Estou transformando para um metodo Transacional
/*  @Transactional
  @Override
  public Artigo criar(Artigo artigo) {

    if (artigo.getAutor().getCodigo() != null) {
      Autor autor = autorRepository.findById(artigo.getAutor().getCodigo())
          .orElseThrow(() -> new IllegalArgumentException("Autor nao encontrado"));

      artigo.setAutor(autor);
    } else {
      artigo.setAutor(null);
    }

    try {
      return artigoRepository.save(artigo);
    } catch (OptimisticLockingFailureException ex) {
      // Recuperar o documento mais recente do banco de dados
      Artigo atualizado = artigoRepository.findById(artigo.getCodigo()).orElse(null);
      if (atualizado != null) {
        // Atualizar os campos desejados
        atualizado.setTitulo(artigo.getTitulo());
        atualizado.setTexto(artigo.getTexto());
        atualizado.setStatus(artigo.getStatus());

        // Incrementar a versao manualmente
        atualizado.setVersion(atualizado.getVersion() + 1);

        return artigoRepository.save(atualizado);
      } else {
        // Documento não encontrado
        throw new RuntimeException(
            "Artigo nao encontrado: " + artigo.getCodigo()
        );
      }
    }
  }*/

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

  @Transactional
  @Override
  public void atualizar(Artigo updateArtigo) {
    // Se o documento existe na coleção ele sobreescreve, alterando somente os dados que mudou
    artigoRepository.save(updateArtigo);
  }

  @Transactional
  @Override
  public void atualizarArtigo(String id, String novaUrl) {
    // Criterio de busca pelo "_id"
    Query query = new Query(Criteria.where("_id").is(id));
    // Definindo os campos que serão atualizados
    Update update = new Update().set("url", novaUrl);
    // Executo a atualização
    mongoTemplate.updateFirst(query, update, Artigo.class);
  }

  @Transactional
  @Override
  public void deleteById(String id) {
    artigoRepository.deleteById(id);
  }

  @Override
  public void deleteArtigoById(String id) {
    Query query = new Query(Criteria.where("_id").is(id));
    mongoTemplate.remove(query, Artigo.class);
  }

  @Override
  public List<Artigo> findByStatusAndDataGreaterThan(Integer status, LocalDateTime data) {
    return artigoRepository.findByStatusAndDataGreaterThan(status, data);
  }

  @Override
  public List<Artigo> obterArtigoPorDataHora(LocalDateTime de, LocalDateTime ate) {
    return artigoRepository.obterArtigoPorDataHora(de, ate);
  }

  @Override
  public List<Artigo> encontrarArtigosComplexos(Integer status, LocalDateTime data, String titulo) {

    Criteria criteria = new Criteria();
    // filtrar artigos com data menor ou igual ao valor fornecido
    criteria.and("data").lte(data);

    // filtrar artigos com o status especificado
    if (status != null) {
      criteria.and("status").is(status);
    }

    // filtrar artigos cujo o titulo exista
    if (titulo != null && !titulo.isEmpty()) {
      // regex i , eu ignoro o case sensitive
      criteria.and("titulo").regex(titulo, "i");
    }

    Query query = new Query(criteria);
    return mongoTemplate.find(query, Artigo.class);
  }

  @Override
  public Page<Artigo> listaArtigos(Pageable pageable) {
    Sort sort = Sort.by("titulo").ascending();
    // Estou redefinindo a paginacao para fazer com o sort , mantendo as variaveis que recebo na request
    Pageable paginacao = PageRequest.of(pageable.getPageNumber(),
        pageable.getPageSize(),
        sort);

    return this.artigoRepository.findAll(paginacao);
  }

  @Override
  public List<Artigo> findByStatusOrderByTitleAsc(Integer status) {
    return artigoRepository.findByStatusOrderByTituloAsc(status);
  }

  @Override
  public List<Artigo> obterArtigoPorStatusComOrdenacao(Integer status) {
    return artigoRepository.obterArtigoPorStatusComOrdenacao(status);
  }

  @Override
  public List<Artigo> findByTexto(String searchTerm) {
    // TextCriteria eu faço busca por frase
    TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm);
    // TextQuery utilizo para fazer consultas de texto de forma completa , ela combina o TextCriteria com outras
    // configurações. São muito úteis quando quero buscar frases
    // sortByScore ele pega os documentos mais relevantes dado a palavra chave que passamos
    Query query = TextQuery.queryText(criteria).sortByScore();
    return mongoTemplate.find(query, Artigo.class);
  }

  @Override
  public List<ArtigoStatusCount> contarArtigosPorStatus() {
    TypedAggregation<Artigo> aggregation =
        Aggregation.newAggregation(
            Artigo.class,
            Aggregation.group("status").count().as("quantidade"),
            Aggregation.project("quantidade").and("status")
                .previousOperation()
        );

    AggregationResults<ArtigoStatusCount> results =
        mongoTemplate.aggregate(aggregation, ArtigoStatusCount.class);

    return results.getMappedResults();
  }

  @Override
  public List<AutorTotalArtigo> calcularTotalArtigosPorAutoNoPeriodo(LocalDate dataInicial,
                                                                     LocalDate dataFinal) {
    TypedAggregation<Artigo> aggregation =
        Aggregation.newAggregation(
            Artigo.class,
            Aggregation.match(
                Criteria.where("data").gte(dataInicial.atStartOfDay())
                    .lt(dataFinal.plusDays(1).atStartOfDay())
            ),
            Aggregation.group("autor").count().as("totalArtigos"),
            Aggregation.project("totalArtigos").and("autor")
                .previousOperation()
        );

    AggregationResults<AutorTotalArtigo> results =
        mongoTemplate.aggregate(aggregation, AutorTotalArtigo.class);

    return results.getMappedResults();
  }

}
