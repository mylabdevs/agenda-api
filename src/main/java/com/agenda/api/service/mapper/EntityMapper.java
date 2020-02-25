package com.agenda.api.service.mapper;

import java.util.List;

/**
 * Contrato para um dto genérico para o mapeador de entidades
 * @param <D>
 * @param <E>
 */
public interface EntityMapper<D, E>{

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
