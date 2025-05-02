package org.gitecsl.net.apppostulante.controller;


import lombok.AllArgsConstructor;
import org.gitecsl.net.apppostulante.entity.Postulant;
import org.gitecsl.net.apppostulante.service.PostulantService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/postulants")
@AllArgsConstructor
public class PostulantController {

    private  PostulantService postulantService;

    @PostMapping
    public Mono<Postulant> createPatient(@RequestBody Postulant patient) {
        if(patient.getId() != null) {
            return Mono.error(new IllegalArgumentException("ID should not be provided for new patients"));
        }
        UUID myUUI = UUID.randomUUID();
        patient.setId(myUUI.toString());
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
