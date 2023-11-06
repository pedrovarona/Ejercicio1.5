package Src.Interfaz;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Src.dominio.Localidad;
import Src.dominio.Municipio;
import Src.dominio.Provincia;

public class Interfaz {
    private static List<Provincia> provincias = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILENAME = "provincias.dat";

    public static void main(String[] args) {
        cargarDatos();

        while (true) {
            System.out.println("Elija una opción:");
            System.out.println("1. Añadir provincia");
            System.out.println("2. Añadir municipio");
            System.out.println("3. Añadir localidad");
            System.out.println("4. Listar datos");
            System.out.println("5. Salir");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    agregarProvincia();
                    break;
                case 2:
                    agregarMunicipio();
                    break;
                case 3:
                    agregarLocalidad();
                    break;
                case 4:
                    listarDatos();
                    break;
                case 5:
                    guardarDatos();
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void cargarDatos() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILENAME))) {
            Object obj = inputStream.readObject();

            if (obj instanceof List<?>) {
                provincias = (List<Provincia>) obj;
            } else {
                System.out.println("El objeto en el archivo no es de tipo List<Provincia>.");
                // Puedes manejar este caso de manera apropiada
            }
        } catch (IOException | ClassNotFoundException e) {
            // No se pudo cargar ningún dato, es normal al principio.
        }
    }

    private static void guardarDatos() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            outputStream.writeObject(provincias);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void agregarProvincia() {
        Provincia provincia = new Provincia();
        System.out.print("Nombre de la provincia: ");
        String nombreProvincia = scanner.next();
        provincia.setNombre(nombreProvincia);
        provincias.add(provincia);
        System.out.println("Provincia añadida correctamente.");
    }

    private static void agregarMunicipio() {
        System.out.print("Nombre de la provincia a la que desea agregar un municipio: ");
        String nombreProvincia = scanner.next();

        Provincia provinciaSeleccionada = null;
        for (Provincia p : provincias) {
            if (p.getNombre().equals(nombreProvincia)) {
                provinciaSeleccionada = p;
                break;
            }
        }

        if (provinciaSeleccionada != null) {
            Municipio municipio = new Municipio();
            System.out.print("Nombre del municipio: ");
            String nombreMunicipio = scanner.next();
            municipio.setNombre(nombreMunicipio);
            provinciaSeleccionada.agregarMunicipio(municipio);
            System.out.println("Municipio añadido a la provincia " + provinciaSeleccionada.getNombre() + " correctamente.");
        } else {
            System.out.println("La provincia especificada no existe.");
        }
    }

    private static void agregarLocalidad() {
        System.out.print("Nombre de la provincia: ");
        String nombreProvincia = scanner.next();

        Provincia provinciaSeleccionada = null;
        for (Provincia p : provincias) {
            if (p.getNombre().equals(nombreProvincia)) {
                provinciaSeleccionada = p;
                break;
            }
        }

        if (provinciaSeleccionada != null) {
            System.out.print("Nombre del municipio en la provincia " + provinciaSeleccionada.getNombre() + ": ");
            String nombreMunicipio = scanner.next();

            Municipio municipioSeleccionado = null;
            for (Municipio m : provinciaSeleccionada.getMunicipios()) {
                if (m.getNombre().equals(nombreMunicipio)) {
                    municipioSeleccionado = m;
                    break;
                }
            }

            if (municipioSeleccionado != null) {
                Localidad localidad = new Localidad();
                System.out.print("Nombre de la localidad: ");
                String nombreLocalidad = scanner.next();
                localidad.setNombre(nombreLocalidad);
                System.out.print("Población de la localidad: ");
                int poblacion = scanner.nextInt();
                localidad.setNumeroDeHabitantes(poblacion);
                municipioSeleccionado.agregarLocalidad(localidad);
                System.out.println("Localidad añadida al municipio " + municipioSeleccionado.getNombre() + " correctamente.");
            } else {
                System.out.println("El municipio especificado no existe.");
            }
        } else {
            System.out.println("La provincia especificada no existe.");
        }
    }

    private static void listarDatos() {
        for (int i = 0; i < provincias.size(); i++) {
            Provincia provincia = provincias.get(i);
            System.out.println("Provincia " + i + ": " + provincia.getNombre());
            List<Municipio> municipios = provincia.getMunicipios();
            for (int j = 0; j < municipios.size(); j++) {
                Municipio municipio = municipios.get(j);
                System.out.println("  Municipio " + j + " en " + provincia.getNombre() + ": " + municipio.getNombre());
                List<Localidad> localidades = municipio.getLocalidades();
                for (int k = 0; k < localidades.size(); k++) {
                    Localidad localidad = localidades.get(k);
                    System.out.println("    Localidad " + k + " en " + municipio.getNombre() + ": " + localidad.getNombre() + " (Población: " + localidad.getNumeroDeHabitantes() + ")");
                }
            }
        }
    }
}
