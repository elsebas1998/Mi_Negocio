package com.example.springbootrelationship.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ValidatorService {

    public boolean validarCedula(String cedula) {
        int suma = 0;
        if (cedula.length() != 10) {
            System.out.println("Ingrese su cedula de 10 digitos");
            return false;
        } else {
            int a[] = new int[cedula.length() / 2];
            int b[] = new int[cedula.length() / 2];
            int c = 0;
            int d = 1;
            for (int i = 0; i < cedula.length() / 2; i++) {
                a[i] = Integer.parseInt(String.valueOf(cedula.charAt(c)));
                c = c + 2;
                if (i < (cedula.length() / 2) - 1) {
                    b[i] = Integer.parseInt(String.valueOf(cedula.charAt(d)));
                    d = d + 2;
                }
            }

            for (int i = 0; i < a.length; i++) {
                a[i] = a[i] * 2;
                if (a[i] > 9) {
                    a[i] = a[i] - 9;
                }
                suma = suma + a[i] + b[i];
            }
            int aux = suma / 10;
            int dec = (aux + 1) * 10;
            if ((dec - suma) == Integer.parseInt(String.valueOf(cedula.charAt(cedula.length() - 1)))) {
                return true;
            } else {
                if (suma % 10 == 0 && cedula.charAt(cedula.length() - 1) == '0') {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
    // Método para validar provincia
    public boolean validarProvincia(String provincia) {
        Set<String> provinciasValidas = new HashSet<>(Set.of(
                "Azuay", "Bolívar", "Cañar", "Carchi", "Chimborazo", "Cotopaxi", "El Oro", "Esmeraldas",
                "Galápagos", "Guayas", "Imbabura", "Loja", "Los Ríos", "Manabí", "Morona Santiago",
                "Napo", "Orellana", "Pastaza", "Pichincha", "Santa Elena", "Santo Domingo de los Tsáchilas",
                "Sucumbíos", "Tungurahua", "Zamora-Chinchipe"
        ));

        return provinciasValidas.contains(provincia);
    }

    public boolean validarCorreoElectronico(String correoElectronico) {
        // Validar el correo electrónico utilizando una expresión regular o cualquier otra lógica que desees implementar
        // En este ejemplo, se utiliza una expresión regular para validar el formato del correo electrónico
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return correoElectronico.matches(emailRegex);
    }
}
