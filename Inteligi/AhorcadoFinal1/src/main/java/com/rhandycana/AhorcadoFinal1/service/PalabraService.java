package com.rhandycana.AhorcadoFinal1.service;

import com.rhandycana.AhorcadoFinal1.model.Palabra;
import java.util.List;

public interface PalabraService {
    List<Palabra> getAllPalabras();
    Palabra getPalabraById(Integer id);
    Palabra createPalabra(Palabra palabra);
    Palabra updatePalabra(Integer id, Palabra palabra);
    void deletePalabra(Integer id);
}