package com.example.prueba.tecnica.pruebaTecnica.model;

public enum Currency {
  EUR("EUR");

  private String value;

  Currency(String value) {this.value = value;}

  @Override
  public String toString() {return String.valueOf(value);}

}
