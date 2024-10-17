package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "http://www.omdbapi.com/?";
    private final String API_KEY = "apikey=af6174de&t=";
    private ConvierteDatos conversor = new ConvierteDatos();
    public void muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.ObtenerDatos(URL_BASE + API_KEY + nombreSerie.replace(" " , "+"));
        //System.out.println(json);
        var datos = conversor.obtenerdatos(json, DatosSerie.class);

        //BUscar datos de la temporada
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1;i < datos.totalDeTemporadas(); i++){
            json =  consumoApi.ObtenerDatos(URL_BASE + API_KEY +nombreSerie.replace(" ", "+")+"&Season=" + i);
            var datosTemporada = conversor.obtenerdatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }
        //temporadas.forEach(System.out::println);

        //Mostrar solo el titulo de los episodios para las temporadas
        //funcion lambda
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir toda la informacion a una lista DatosEpisodios
//        List<DatosEpisodio>datosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());

        //Top 5 episodios
//        System.out.println("Top 5 episodios");
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro N/A" + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Segundo Ordenacion (M>m)" + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Tercer filtro mayusculas (m>M)" + e))
//                .limit(5)
//                .forEach(System.out::println);

        //Convirtiendo los datos a una lista del tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());
        //episodios.forEach(System.out::println);

        //Busqueda de episodios a partir de x año
//        System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechadelanzamiento() != null && e.getFechadelanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada " + e.getTemporada() +
//                                "Episodio " + e.getTitulo() +
//                                "Fecha de lanzamiento " + e.getFechadelanzamiento().format(dtf)
//                ));
        //Busc a episodio por pedazo de titulo
//        System.out.println("Por favor ecribe el titulo del episodio que desea ver");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodiobuscar = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodiobuscar.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("Los datos son; " + episodiobuscar.get());
//        }else {
//            System.out.println("Episodio no encontrado");
//        }

        Map<Integer , Double> evaluacionportemporada = episodios.stream()
                .filter(e -> e.getEvalucacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvalucacion)));
        System.out.println(evaluacionportemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvalucacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvalucacion));
        System.out.println("Media de evaluaciones: " + est.getAverage());
        System.out.println("Episodio mejor evaluado: " + est.getMax());
        System.out.println("Episodio peor evaluado: " + est.getMin());
    }
}
