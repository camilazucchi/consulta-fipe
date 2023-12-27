package com.consultafipe.consulta.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class Conversor implements IConversor {

    // Declara um atributo "objectMapper" que será usado para realizar as operações de conversão JSON.
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T pegaDados(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> pegaDadosLista(String json, Class<T> classe) {
        CollectionType lista = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, classe);
        try {
            return objectMapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
