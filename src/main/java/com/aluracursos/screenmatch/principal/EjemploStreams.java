package com.aluracursos.screenmatch.principal;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombre = Arrays.asList("Luis", "Jose", "Mario", "Pablo");

        nombre.stream()
                .sorted()
                //Solo mostrar los primeros dos nombres de la lista
                .limit(2)
                //Filtrar por primer aletra
                .filter(n -> n.startsWith("L"))
                //Letras Mayusculas
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);

    }
}
