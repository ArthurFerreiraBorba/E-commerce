package com.arthur.e_commerce.infra.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;

import java.util.List;

@Slf4j
public abstract class GenericController<S extends GenericService<RES, REQ>, RES, REQ> {

    @Autowired
    protected S service;
    private final String nomeEntity;

    protected GenericController() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.nomeEntity = parameterizedType.getActualTypeArguments()[0].getTypeName().substring(32).replace("Service", "");
    }

    @GetMapping("{id}")
    public ResponseEntity<RES> buscarId(@PathVariable Long id) {

        log.info("buscando {} com id {}", nomeEntity, id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @GetMapping("")
    public ResponseEntity<List<RES>> buscarTodas() {

        log.info("buscando todas as entidades {}", nomeEntity);

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

    @PostMapping("")
    public ResponseEntity<RES> criar(@RequestBody REQ requestDto) {

        log.info("criando {}", nomeEntity);

        return ResponseEntity.status(201).body(service.criar(requestDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleter(@PathVariable Long id) {

        log.info("deletando {} com id {}", nomeEntity, id);

        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@RequestBody REQ requestDto, @PathVariable Long id) {

        log.info("atualizando {} com id {}", nomeEntity, id);

        service.atualizar(requestDto, id);
        return ResponseEntity.status(204).build();
    }

}
