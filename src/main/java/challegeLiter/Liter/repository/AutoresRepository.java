package challegeLiter.Liter.repository;

import challegeLiter.Liter.model.Autor;
import challegeLiter.Liter.model.IListarAutores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoresRepository extends JpaRepository<Autor, Long> {
    @Query(value="SELECT a.nombre, a.anio_nacimiento, a.anio_muerte FROM autores a GROUP BY a.nombre, a.anio_nacimiento, a.anio_muerte", nativeQuery = true)
    List<IListarAutores> ListarAutoresAgrupados();

    @Query(value="SELECT a.nombre, a.anio_nacimiento, a.anio_muerte " +
            "FROM autores a " +
            "WHERE a.anio_nacimiento <= :anio " +
            "AND (a.anio_muerte IS NULL OR anio_muerte >= :anio) " +
            "GROUP BY a.nombre, a.anio_nacimiento, a.anio_muerte", nativeQuery = true)
    List<IListarAutores> ListarAutoresAgrupadosVivosPorAnio(@Param("anio") Integer anio);

    @Query(value="SELECT l.titulo FROM libros l INNER JOIN autores a ON l.id = a.libro_id where a.nombre LIKE :nombreAutor", nativeQuery = true)
    List<String> buscarLibrosPorAutor(@Param("nombreAutor") String autorABuscar);

}
