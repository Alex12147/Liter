package challegeLiter.Liter.principal;

import challegeLiter.Liter.model.*;
import challegeLiter.Liter.repository.AutoresRepository;
import challegeLiter.Liter.repository.LibrosRepository;
import challegeLiter.Liter.service.ConsumoAPI;
import challegeLiter.Liter.service.ConversorDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String URL = "https://gutendex.com/books/?search=";
    private ConversorDatos conversor = new ConversorDatos();
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Scanner teclado = new Scanner(System.in);
    private List<Libro> libro = new ArrayList<>();
    private LibrosRepository repositorioLibros;
    private AutoresRepository repositorioAutor;

    public Principal (LibrosRepository repositorioLibros, AutoresRepository repositorioAutor){
        this.repositorioLibros = repositorioLibros;
        this.repositorioAutor = repositorioAutor;
    }
    public Principal (){}

    public void menu(){
        int count = 99;
        while (count != 0){
            System.out.println(""" 
                Eliga la opcion a través del numero:
                
                1.- Buscar libro por titulo
                2.- Listar libros registrados
                3.- Listar autores registrados
                4.- Listar autores vivos en un determinado año
                5.- Listar libros por Idioma
                
                """);
            try {
                count = teclado.nextInt();
                teclado.nextLine();
            }catch (InputMismatchException e){
                teclado.nextLine();
            }
            switch (count){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mosrtarLibros();
                    break;
                case 3:
                    mosrtarAutores();
                    break;
                case 4:
                    mosrtarAutoresPorAnioVivos();
                    break;
                case 5:
                    mosrtarLibrosPorIdioma();
                    break;
                default:
                    System.out.println("Opción Invalida");
            }
        }
    }

    private void mosrtarLibrosPorIdioma() {
        System.out.println("""
                
                Ingresa el idioma para buscar libros:
                es - español
                en - ingles
                fr - rfancés
                pt - portugués
                """ );
        try {
            var anioAutor = teclado.nextLine();
            Idioma idiomaABuscar = Idioma.fromString(anioAutor);
            List<Libro> librosPorIdioma = repositorioLibros.findByIdioma(idiomaABuscar);
            librosPorIdioma.forEach(System.out::println);
        }catch (InputMismatchException | NullPointerException | IllegalArgumentException e){
            teclado.nextLine();
            System.out.println("Ingresa una opcion valida (LL)");
        }
    }

    private void mosrtarAutoresPorAnioVivos() {
        System.out.println("Ingresa el año vivo de autor(es) que desea buscar: ");
        try {
            var anioAutor = teclado.nextInt();
            teclado.nextLine();
            imprimirEnPantallaAutores(buscarAutoresPorAnio(anioAutor));
        }catch (InputMismatchException | NullPointerException e){
            teclado.nextLine();
            System.out.println("Ingresa una opcion valida (YYYY)");
        }
    }

    private void mosrtarAutores() {
        imprimirEnPantallaAutores(buscarAutores());
    }

    private void imprimirEnPantallaAutores(List<DatosAutor> resultadosBusquedaAutor){
        resultadosBusquedaAutor.forEach(e -> System.out.println(
                "----------Libros-----------\n" +
                        "Autor: " + e.nombre() + "\n" +
                        "Fecha Nacimiento: " + e.anioNacimiento() + "\n" +
                        "Fecha de fallecimiento: " + e.anioMuerte() +
                        "\nLibros: " + repositorioAutor.buscarLibrosPorAutor(e.nombre()) +
                        "\n---------------------------\n"
        ));
    }

    private List<DatosAutor> buscarAutores(){
        List<IListarAutores> busquedaAutores = repositorioAutor.ListarAutoresAgrupados();
        return busquedaAutores.stream()
                .map(e-> new DatosAutor (
                        e.getNombre(), e.getAnioNacimiento(), e.getAnioMuerte()))
                .toList();
    }
    private List<DatosAutor> buscarAutoresPorAnio(Integer Anio){
        List<IListarAutores> busquedaAutores = repositorioAutor.ListarAutoresAgrupadosVivosPorAnio(Anio);
        return busquedaAutores.stream()
                .map(e-> new DatosAutor (
                        e.getNombre(), e.getAnioNacimiento(), e.getAnioMuerte()))
                .toList();
    }

    private void mosrtarLibros() {
        ListarLibros();
        libro.forEach(System.out::println);
    }

    private void ListarLibros() {
        libro = repositorioLibros.findAll();
    }

    private void buscarLibro() {
        DatosLibro libroBuscado = getLibros();
        if (libroBuscado==null){
            return;
        }
        Libro libro = new Libro(libroBuscado);
        List<Autor> datosAutors = libroBuscado.autores().stream()
                        .map(e->new Autor(e))
                .collect(Collectors.toList());
        libro.setAutors(datosAutors);
        System.out.println(libro);
        try {
            repositorioLibros.save(libro);
        }catch (DataIntegrityViolationException e){
            System.out.println("Ya se enceuntra registrado");
        }
    }

    public DatosLibro getLibros(){
        System.out.println("Ingresa el libro que desea buscar");
        var libroBuscar = teclado.nextLine();
        ListarLibros();
        Optional<Libro> libroEncontrar = libro.stream()
                .filter(s->s.getTitulo().toLowerCase().contains(libroBuscar.toLowerCase()))
                .findFirst();
        if (libroEncontrar.isPresent()){
            System.out.println("Libro ya registrado: "+libroBuscar+"\n");
        }else{
            //String libroEncontrarFormateado =
            System.out.println("aaaa"+libroEncontrar);
            String json = consumoAPI.obtenerDatos(URL+libroBuscar.replace(" ", "%20"));
            try {
                return conversor.obtenerDatos(json, Datos.class).libros().getFirst();
            }catch (NoSuchElementException | IllegalArgumentException e){
                System.out.println("No se encontro el libro solicitado");
            }
        }
        return null;
    }



}
