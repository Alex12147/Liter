package challegeLiter.Liter.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias({"name", "nombre"}) String nombre,
        @JsonAlias({"birth_year", "anio_nacimiento"}) Integer anioNacimiento,
        @JsonAlias({"death_year", "anio_muerte"}) Integer anioMuerte
) {
}
