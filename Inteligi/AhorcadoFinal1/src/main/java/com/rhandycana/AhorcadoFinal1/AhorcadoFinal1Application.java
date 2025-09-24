package com.rhandycana.AhorcadoFinal1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.ServerSocket;

@SpringBootApplication
public class AhorcadoFinal1Application {

    public static void main(String[] args) {
        int port = 8080;
        while (port <= 8090) {
            if (isPortAvailable(port)) {
                System.setProperty("server.port", String.valueOf(port));
                if (port != 8080) {
                    System.out.println("Puerto 8080 ocupado. Usando puerto " + port);
                }
                break;
            }
            port++;
        }
        if (port > 8090) {
            System.out.println("No hay puertos disponibles entre 8080 y 8090. Saliendo...");
            System.exit(1);
        }
        SpringApplication.run(AhorcadoApplication.class, args);
    }

    private static boolean isPortAvailable(int port) {
        try (ServerSocket ignored = new ServerSocket(port)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("*-*-*-API Camila esta funcioando-*-*-*");
    }
}
