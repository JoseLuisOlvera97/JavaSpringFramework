package com.aluracursos.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double evalucacion;
    private LocalDate fechadelanzamiento;

    public Episodio(Integer numero, DatosEpisodio d) {
        this.temporada = numero;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try{
            this.evalucacion = Double.valueOf(d.evaluacion());
        }catch (NumberFormatException e){
            this.evalucacion = 0.0;
        }
        try{
            this.fechadelanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
        }catch (DateTimeParseException e){
            this.fechadelanzamiento = null;
        }
    }

//    public Episodio(Integer numero, DatosEpisodio d){
//        this.temporada = numero;
//        this.titulo = d.titulo();
//        this.numeroEpisodio = d.numeroEpisodio();
//        try{
//            this.evalucacion = Double.valueOf(d.evaluacion());
//        }catch (NumberFormatException e){
//            this.evalucacion = 0.0;
//        }
//        this.fechadelanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
//    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvalucacion() {
        return evalucacion;
    }

    public void setEvalucacion(Double evalucacion) {
        this.evalucacion = evalucacion;
    }

    public LocalDate getFechadelanzamiento() {
        return fechadelanzamiento;
    }

    public void setFechadelanzamiento(LocalDate fechadelanzamiento) {
        this.fechadelanzamiento = fechadelanzamiento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evalucacion=" + evalucacion +
                ", fechadelanzamiento=" + fechadelanzamiento;
    }
}
