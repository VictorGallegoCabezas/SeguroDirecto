package test.sd.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MatriculaUtils {

    private static final String RUTA_FICHERO = "./UltimaMatricula.txt";

    /**
     * Lee el contenido del fichero ./UltimaMatricula.txt y devuelve la matrícula como String.
     */
    public static String leerUltimaMatricula() {
        try {
            return new String(Files.readAllBytes(Paths.get(RUTA_FICHERO))).trim();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el fichero de matrícula: " + RUTA_FICHERO, e);
        }
    }

    /**
     * Lee la matrícula del fichero, incrementa en 1 su parte numérica manteniendo el formato 
     * (4 dígitos con ceros a la izquierda) y guarda el nuevo resultado en el fichero.
     * 
     * @return La nueva matrícula generada.
     */
    public static String incrementarYActualizarMatricula() {
        // 1. Leer la matrícula actual
        String matriculaActual = leerUltimaMatricula();

        // 2. Separar los 4 números del resto de letras (ejemplo: "0012CYD" -> "0012" y "CYD")
        String parteNumericaStr = matriculaActual.substring(0, 4);
        String parteLetras = matriculaActual.substring(4);

        // 3. Sumar 1 al número
        int numero = Integer.parseInt(parteNumericaStr);
        numero++;

        // 4. Formatear para mantener siempre los 4 dígitos (ej: 13 -> "0013")
        String nuevaParteNumerica = String.format("%04d", numero);
        String nuevaMatricula = nuevaParteNumerica + parteLetras;

        // 5. Sobrescribir el fichero con la nueva matrícula
        try {
            Files.write(Paths.get(RUTA_FICHERO), nuevaMatricula.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el fichero de matrícula: " + RUTA_FICHERO, e);
        }

        return nuevaMatricula;
    }
}