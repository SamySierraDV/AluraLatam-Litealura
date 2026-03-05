package com.aluracursos.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
//Representación del Json completo que se obtiene
public record DatosBookObject(
        @JsonAlias("results") List<DatosLibro> bookObjectData
) {
}
