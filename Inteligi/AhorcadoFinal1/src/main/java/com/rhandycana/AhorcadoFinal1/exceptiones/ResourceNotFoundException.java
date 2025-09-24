package com.rhandycana.AhorcadoFinal1.exceptiones;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
