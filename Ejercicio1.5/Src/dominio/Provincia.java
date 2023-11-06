package Src.dominio;

import java.util.ArrayList;
import java.util.List;

public class Provincia {
    private String nombre;
    private List<Municipio> municipios = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void agregarMunicipio(Municipio municipio) {
        municipios.add(municipio);
    }

    public int contarNumeroDeHabitantes() {
        int totalHabitantes = 0;
        for (Municipio municipio : municipios) {
            totalHabitantes += municipio.contarNumeroTotalDeHabitantes();
        }
        return totalHabitantes;
    }

    @Override
    public String toString() {
        return "Provincia: " + nombre + ". " + "Municipios: " + municipios + ". ";
    }
}
