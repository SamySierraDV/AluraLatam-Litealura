package com.aluracursos.LiterAlura.service;

public interface IConvierteDatos {
    //Método que transforma un json en una clase, trabaja con datos tipo genérico para que sea más escalable
    <T> T obtenerDatos(String json, Class<T> clase);
}
