package com.rhandycana.AhorcadoFinal1.service;

import com.rhandycana.AhorcadoFinal1.exceptiones.DuplicateResourceException;
import com.rhandycana.AhorcadoFinal1.exceptiones.ResourceNotFoundException;
import com.rhandycana.AhorcadoFinal1.model.Palabra;
import com.rhandycana.AhorcadoFinal1.repository.PalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalabraServiceImpl implements PalabraService {

    @Autowired
    private PalabraRepository palabraRepository;

    @Override
    public List<Palabra> getAllPalabras() {
        return palabraRepository.findAll();
    }

    @Override
    public Palabra getPalabraById(Integer id) {
        return palabraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Palabra no encontrada con ID: " + id));
    }

    @Override
    public Palabra createPalabra(Palabra palabra) {
        // Validar mayúsculas
        if (!palabra.getPalabra().equals(palabra.getPalabra().toUpperCase()) ||
                !palabra.getPista().equals(palabra.getPista().toUpperCase())) {
            throw new DuplicateResourceException("La palabra y la pista deben estar en MAYUSCULAS.");
        }
        if (palabraRepository.existsByPalabraAndCategoria(palabra.getPalabra(), palabra.getCategoria())) {
            throw new DuplicateResourceException("Ya existe la palabra '" + palabra.getPalabra() + "' en la categoría '" + palabra.getCategoria() + "'.");        }
        return palabraRepository.save(palabra);
    }

    @Override
    public Palabra updatePalabra(Integer id, Palabra palabraDetails) {
        Palabra palabra = getPalabraById(id);

        // Validar mayúsculas
        if (!palabraDetails.getPalabra().equals(palabraDetails.getPalabra().toUpperCase()) ||
                !palabraDetails.getPista().equals(palabraDetails.getPista().toUpperCase())) {
            throw new DuplicateResourceException("La palabra y la pista deben estar en MAYUSCULAS.");
        }

        // Si cambia la palabra o la categoría, validar que no exista ya esa combinación
        if ((!palabra.getPalabra().equals(palabraDetails.getPalabra()) ||
                !palabra.getCategoria().equals(palabraDetails.getCategoria())) &&
                palabraRepository.existsByPalabraAndCategoria(palabraDetails.getPalabra(), palabraDetails.getCategoria())) {
            throw new DuplicateResourceException("Ya existe la palabra '" + palabraDetails.getPalabra() + "' en la categoría '" + palabraDetails.getCategoria() + "'.");        }

        palabra.setPalabra(palabraDetails.getPalabra());
        palabra.setPista(palabraDetails.getPista());
        palabra.setCategoria(palabraDetails.getCategoria());

        return palabraRepository.save(palabra);
    }

    @Override
    public void deletePalabra(Integer id) {
        Palabra palabra = getPalabraById(id);
        palabraRepository.delete(palabra);
    }
}