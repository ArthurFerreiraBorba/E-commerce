package com.arthur.e_commerce.infra.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
public abstract class GenericServiceImpl<E extends GenericEntity, RES, REQ> implements GenericService<RES, REQ> {

    private final JpaRepository<E, Long> repository;
    private final String nomeEntity;

    protected GenericServiceImpl(JpaRepository<E, Long> repository) {
        this.repository = repository;
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.nomeEntity = parameterizedType.getActualTypeArguments()[0].getTypeName().substring(31).replace("Entity", "");
    }

    protected abstract RES paraDto(E entity);

    protected abstract E paraEntity(REQ requestDto);

    protected abstract void validar(REQ requestDto);

    public RES buscarPorId(Long id) {
        return paraDto(buscarEntityPorId(id));
    }

    public E buscarEntityPorId(Long id) {

        entidadeExiste(id);

        E entity = repository.findById(id).get();

        log.info("entidade {} encontrada com sucesso", nomeEntity);

        return entity;
    }

    public List<RES> buscarTodos() {
        List<E> entidades= repository.findAll();

        log.info("todas as entidades {} encontradas com sucesso", nomeEntity);

        return entidades.stream().map(this::paraDto).toList();
    }

    public RES criar (REQ requestDto) {
        validar(requestDto);
        E entity = paraEntity(requestDto);

        entity.setId(null);

        repository.save(entity);

        log.info("entidade {} criada com sucesso", nomeEntity);

        return paraDto(entity);
    }

    public void deletar (Long id) {
        entidadeExiste(id);
        repository.deleteById(id);

        log.info("entidade {} deletada com sucesso", nomeEntity);
    }

    public void atualizar (REQ requestDto, Long id) {
        entidadeExiste(id);
        validar(requestDto);
        E entity = paraEntity(requestDto);

        entity.setId(id);

        repository.save(entity);

        log.info("entidade {} atualizada com sucesso", nomeEntity);
    }

    public void entidadeExiste(Long id) {

        log.info("verificando se {} com id {} existe", nomeEntity, id);

        if (!repository.existsById(id)) {
            throw new RuntimeException();
        }

        log.info("{} com id {} existe", nomeEntity, id);
    }
}