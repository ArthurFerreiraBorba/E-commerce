package com.arthur.e_commerce.infra.generic;

import java.util.List;

public interface GenericService<RES, REQ> {
    RES criar (REQ requestDto);

    RES buscarPorId(Long id);

    List<RES> buscarTodos();

    void atualizar (REQ requestDto, Long id);

    void deletar (Long id);
}
