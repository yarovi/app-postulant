package org.gitecsl.net.apppostulante.controller;


import lombok.AllArgsConstructor;
import org.gitecsl.net.apppostulante.entity.Postulant;
import org.gitecsl.net.apppostulante.exception.ApiResponse;
import org.gitecsl.net.apppostulante.service.PostulantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/postulants")
@AllArgsConstructor
public class PostulantController {

    private  PostulantService postulantService;

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<Postulant>>> createPatient(@RequestBody Postulant postulant) {
        if(postulant.getId() != null) {
            return Mono.just(ResponseEntity.badRequest()
                    .body(ApiResponse.error("No debe proporcionar ID para nuevos postulantes")));
        }

        postulant.setId(UUID.randomUUID().toString());

        return postulantService.createPostulant(postulant)
                .map(savedPostulant -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ApiResponse<>("Postulante creado exitosamente", savedPostulant)))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError()
                        .body(ApiResponse.error("Error al crear postulante: " + e.getMessage()))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Postulant>>> getPatientById(@PathVariable String id) {
        return postulantService.getPostulantById(id)
                .map(postulant -> ResponseEntity.ok()
                        .body(new ApiResponse<>("Postulante encontrado", postulant)))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Postulante no encontrado con ID: " + id))));
    }

    @GetMapping
    public Flux<Postulant> getAllPatients() {
        return postulantService.getAllPostulants()
                .switchIfEmpty(Flux.error(new RuntimeException("No se encontraron postulantes")));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Postulant>>> updatePatient(@PathVariable String id, @RequestBody Postulant postulant) {
        if(!id.equals(postulant.getId())) {
            return Mono.just(ResponseEntity.badRequest()
                    .body(ApiResponse.error("ID del path no coincide con el ID del postulante")));
        }

        return postulantService.updatePostulant(id, postulant)
                .map(updated -> ResponseEntity.ok()
                        .body(new ApiResponse<>("Postulante actualizado exitosamente", updated)))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("No se encontró postulante con ID: " + id))));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<String>>> deletePatient(@PathVariable String id) {
        return postulantService.deletePostulant(id)
                .map(deleted -> {
                    if (deleted) {
                        return ResponseEntity.ok()
                                .body(new ApiResponse<>("Postulante eliminado exitosamente", "success"));
                    }
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ApiResponse<>("Postulante no encontrado", "error"));
                });
    }
}
