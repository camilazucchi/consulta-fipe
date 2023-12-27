package com.consultafipe.consulta.service;

import java.util.List;

public interface IConversor {
    <T> T pegaDados(String json, Class<T> tClass);

    <T> List<T> pegaDadosLista(String json, Class<T> tClass);
}
