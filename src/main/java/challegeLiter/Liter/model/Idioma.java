package challegeLiter.Liter.model;

public enum Idioma {
    EN("en"),
    ES("es"),
    FR("fr"),
    PT("pt");

    private String IdiomaAPI;
    Idioma (String IdiomaAPI){
        this.IdiomaAPI = IdiomaAPI;
    }
    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.IdiomaAPI.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna Idioma encontrada: " + text);
    }
}
