package com.consultafipe.consulta.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record Dados(@JsonAlias("codigo") String codigo,
                    @JsonAlias("nome") String nome) {

    @Override
    public String toString() {
        // TODO: Colocar a primeira letra da segunda palavra também começando com letra maiúscula!
        return String.format("- Código: " + codigo + " - Nome: " + nome.substring(0, 1).toUpperCase()
                + nome.substring(1).toLowerCase());
    }
}
