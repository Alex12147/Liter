package challegeLiter.Liter.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name="libros")
public class Libro {
    @Id
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer descargas;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autors;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.id = datosLibro.id();
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.fromString(datosLibro.idioma().getFirst());
        this.descargas = datosLibro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutors() {
        return autors;
    }

    public void setAutors(List<Autor> autors) {
        autors.forEach(e->e.setLibro(this));
        this.autors = autors;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "----------Libros-----------\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autors.getFirst().getNombre() + "\n" +
                "Idioma: " + idioma +
                "\nDescargas: " + descargas +
                "\n---------------------------\n";
    }
}


