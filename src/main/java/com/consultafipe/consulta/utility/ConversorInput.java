package com.consultafipe.consulta.utility;

import java.text.Normalizer;

public class ConversorInput {
    public String normalizeString(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }
}
