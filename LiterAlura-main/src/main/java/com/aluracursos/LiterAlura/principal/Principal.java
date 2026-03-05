package com.aluracursos.LiterAlura.principal;

import com.aluracursos.LiterAlura.model.*;
import com.aluracursos.LiterAlura.repositry.AutorRepository;
import com.aluracursos.LiterAlura.repositry.LibroRepository;
import com.aluracursos.LiterAlura.service.ConsumoAPI;
import com.aluracursos.LiterAlura.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "http://gutendex.com/books/";
    private final String PARAM_SEARCH ="?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
    }


    public void muestraMenu(){
        var opcion = 0;
        while(opcion!=6){
            try{
                var menu = """
                    -------------------
                    Elija la opción a través de su número:
                    1- Buscar libro por título.
                    2- Listar libros registrados.
                    3- Listar autores registrados.
                    4- Listar autores vivos en determinado año.
                    5- Listar libros por idioma.
                    6- Salir.
                    --------------------
                    """;
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine(); //Para evitar problemas de lectura con el nextInt

                switch (opcion) {
                    case 1:
                        getLibroPorTitulo();
                        break;
                    case 2:
                        getLibrosRegistrados();
                        break;
                    case 3:
                        getAutoresRegistrados();
                        break;
                    case 4:
                        getAutoresPorAnio();
                        break;
                    case 5:
                        getLibrosPorIdioma();
                        break;
                    case 6:
                        System.out.println("Hasta luego. Cerrando aplicación.....-");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida. Por favor, ingrese un número entero, entre 1 y 6.");
                teclado.next();
            }
        }
    }

    //Funciones del menú
    private void getLibroPorTitulo() {
        //Variables locales
        System.out.println("Escriba el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + PARAM_SEARCH + nombreLibro.replace(" ", "%20"));
        DatosBookObject datosBookObject = conversor.obtenerDatos(json, DatosBookObject.class);

        //Búsqueda por título
        Optional <DatosLibro> libroEncontrado = datosBookObject.bookObjectData().stream()
                .findFirst();

        //Verificar si se encuentra el libro
        if (libroEncontrado.isPresent()){
            var autor = libroEncontrado.get().autor().stream().map(DatosAutor::nombre).limit(1).collect(Collectors.joining());
            var idioma = libroEncontrado.get().idioma().stream().limit(1).collect(Collectors.joining());;

            //Muestra los datos en conosla
            System.out.printf("""
                
                ----------Libro----------------
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d
                -------------------------------
                """,libroEncontrado.get().titulo(), autor,  idioma, libroEncontrado.get().numeroDeDescargas());
            DatosLibro libro = libroEncontrado.get();
            guardaLibroBaseDatos(libro);
        }else{
            System.out.println("\nLibro no encontrado :(");
        }
    }
    private void guardaLibroBaseDatos(DatosLibro libroEncontrado){
        try{
            Libro libro = new Libro(libroEncontrado);
            Autor autor = new Autor(libroEncontrado.autor().get(0));
            Optional<Autor> autorExistente = autorRepositorio.findByNombre(autor.getNombre());
            if (autorExistente.isPresent()) {
                libro.setAutor(autorExistente.get());
            } else {
                autorRepositorio.save(autor);
                libro.setAutor(autor);
            }
            repositorio.save(libro);
        }catch (DataIntegrityViolationException e){
            System.out.println("!!!!!!!\nNo se puede registrar el libro más de una vez");
        }
    }

    private void muestraDatosLibro(List<Libro> libros){
        libros.forEach(l-> System.out.println("-----------LIBRO---------\nTítulo: "+l.getTitulo()+
                "\nAutor: "+l.getAutor().getNombre()+
                "\nIdioma: "+l.getIdioma()+
                "\nNumero de descargas: "+l.getNumeroDeDescargas()));
    }

    private void getLibrosRegistrados() {
        System.out.println("---------------Libros registrados-------------");
        List<Libro> libros = repositorio.findAll();
        muestraDatosLibro(libros);
    }

    private void muestraDatosAutores(List<Autor> autores){
        autores.forEach(a-> System.out.println("-----------AUTOR---------\nNombre: "+a.getNombre()+
                "\nFecha de Nacimiento: "+a.getFechaNacimiento()+
                "\nFecha de Fallecimiento: "+a.getFechaFallecimiento()+
                "\nLibros: "+a.getLibros().stream().map(l->l.getTitulo()).collect(Collectors.toList())));
    }

    private void getAutoresRegistrados() {
        System.out.println("---------------Autores registrados-------------");
        List<Autor> autores = autorRepositorio.findAll();
        muestraDatosAutores(autores);
    }

    private void getAutoresPorAnio() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar");
        var anio = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autroesPorAnio = autorRepositorio.getAutoresPorAnio(anio);
        if(!autroesPorAnio.isEmpty()){
            muestraDatosAutores(autroesPorAnio);
        }else{
            System.out.println("No se registró ningún autor vivo en dicho año");
        }
    }

    private void getLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma que desea:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """);
        var idioma = teclado.nextLine().trim().toLowerCase(); // Convertir entrada a minúsculas

        try {
            Idioma idiomaABuscar = Idioma.fromString(idioma);
            List<Libro> librosPorIdioma = repositorio.findByIdioma(idiomaABuscar);
            if (!librosPorIdioma.isEmpty()) {
                muestraDatosLibro(librosPorIdioma);
            } else {
                System.out.println("No se registró ningún libro con ese idioma");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Manejar el caso donde el idioma no existe
        }
    }
}
