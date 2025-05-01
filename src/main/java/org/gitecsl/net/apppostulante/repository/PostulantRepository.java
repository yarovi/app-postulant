package org.gitecsl.net.apppostulante.repository;

import org.gitecsl.net.apppostulante.entity.Postulant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PostulantRepository extends ReactiveMongoRepository<Postulant, String> {
    // Custom query methods can be defined here if needed
    // For example, findByEmail(String email) or findByDocumentNumber(String documentNumber)
    Mono<Postulant> findByEmail(String email);
}
