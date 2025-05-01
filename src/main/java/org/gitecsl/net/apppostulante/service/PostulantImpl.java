package org.gitecsl.net.apppostulante.service;

import lombok.RequiredArgsConstructor;
import org.gitecsl.net.apppostulante.entity.Postulant;
import org.gitecsl.net.apppostulante.repository.PostulantRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostulantImpl implements PostulantService {

    private final PostulantRepository postulantRepository;

    @Override
    public Mono<Postulant> createPostulant(Postulant postulant) {
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
    public Mono<Void> deletePostulant(String id) {
       return postulantRepository.findById(id)
                .flatMap(existingPostulant -> postulantRepository.delete(existingPostulant))
                .switchIfEmpty(Mono.error(new RuntimeException("Postulant not found")));
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
