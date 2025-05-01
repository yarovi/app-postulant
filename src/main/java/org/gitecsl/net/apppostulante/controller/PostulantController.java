package org.gitecsl.net.apppostulante.controller;


import lombok.RequiredArgsConstructor;
import org.gitecsl.net.apppostulante.entity.Postulant;
import org.gitecsl.net.apppostulante.service.PostulantService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/postulants")
@RequiredArgsConstructor
public class PostulantController {

    private  PostulantService postulantService;

    @PostMapping
    public Mono<Postulant> createPatient(@RequestBody Postulant patient) {
        return postulantService.createPostulant(patient);
    }

    @GetMapping("/{id}")
    public Mono<Postulant> getPatientById(@PathVariable String id) {
        return postulantService.getPostulantById(id);
    }

    @GetMapping
    public Flux<Postulant> getAllPatients() {
        return postulantService.getAllPostulants();
    }

    @PutMapping("/{id}")
    public Mono<Postulant> updatePatient(@PathVariable String id, @RequestBody Postulant postulant) {
        return postulantService.updatePostulant(id, postulant);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePatient(@PathVariable String id) {
        return postulantService.deletePostulant(id);
    }

}
