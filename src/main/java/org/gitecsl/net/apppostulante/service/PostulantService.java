package org.gitecsl.net.apppostulante.service;

import org.gitecsl.net.apppostulante.entity.Postulant;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostulantService {
    Mono<Postulant> createPostulant(Postulant postulant);
    Mono<Postulant> getPostulantById(String id);
    Mono<Postulant> updatePostulant(String id, Postulant postulant);
    Mono<Boolean> deletePostulant(String id);
    Mono<Postulant> findByEmail(String email);
    Flux<Postulant> getAllPostulants();
}
