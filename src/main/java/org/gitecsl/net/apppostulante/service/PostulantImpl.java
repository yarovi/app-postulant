package org.gitecsl.net.apppostulante.service;

import lombok.RequiredArgsConstructor;
import org.gitecsl.net.apppostulante.entity.Postulant;
import org.gitecsl.net.apppostulante.repository.PostulantRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostulantImpl implements PostulantService {

    Logger logger = org.slf4j.LoggerFactory.getLogger(PostulantImpl.class);
    private final PostulantRepository postulantRepository;

    @Override
    public Mono<Postulant> createPostulant(Postulant postulant) {
        logger.info("Creating new postulant: {}", postulant);
        return postulantRepository.save(postulant);
    }

    @Override
    public Mono<Postulant> getPostulantById(String id) {
        return postulantRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Postulant not found")));
    }

    @Override
    public Mono<Postulant> updatePostulant(String id, Postulant postulant) {
       return postulantRepository.findById(id)
                .flatMap(existingPostulant -> {
                    existingPostulant.setFirstName(postulant.getFirstName());
                    existingPostulant.setLastName(postulant.getLastName());
                    existingPostulant.setDocumentNumber(postulant.getDocumentNumber());
                    existingPostulant.setEmail(postulant.getEmail());
                    existingPostulant.setPhone(postulant.getPhone());
                    existingPostulant.setAddress(postulant.getAddress());
                    return postulantRepository.save(existingPostulant);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Postulant not found")));
    }

    @Override
    public Mono<Boolean> deletePostulant(String id) {
        return postulantRepository.findById(id)
                .flatMap(postulant -> postulantRepository.delete(postulant)
                        .then(Mono.just(true))
                        .defaultIfEmpty(false));
    }

    @Override
    public Mono<Postulant> findByEmail(String email) {
        return postulantRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Postulant not found")));
    }

    @Override
    public Flux<Postulant> getAllPostulants() {
        return postulantRepository.findAll()
                .switchIfEmpty(Mono.error(new RuntimeException("No postulants found")));
    }


}
