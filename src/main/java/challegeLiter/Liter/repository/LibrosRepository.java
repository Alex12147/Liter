package challegeLiter.Liter.repository;

import challegeLiter.Liter.model.Idioma;
import challegeLiter.Liter.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibrosRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(Idioma idioma);
}
