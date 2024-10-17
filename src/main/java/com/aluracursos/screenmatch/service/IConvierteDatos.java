package com.aluracursos.screenmatch.service;

public interface IConvierteDatos {
    <T> T obtenerdatos(String json , Class <T> clase);
}
