package com.aluracursos.LiterAlura.model;

public enum Idioma {
    es("es"),
    en("en"),
    fr("fr"),
    pt("pt"),
    tl ("tl");

    private String idioma;

    Idioma(String idioma) {
        this.idioma = idioma;
    }

    public String getCodigoIdioma() {
        return idioma; // Método para obtener el código de idioma en minúsculas
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idioma.equals(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
    }

}


