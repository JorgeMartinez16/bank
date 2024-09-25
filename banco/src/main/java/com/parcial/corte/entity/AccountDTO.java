package com.parcial.corte.entity;

public class AccountDTO {
    private String nombre;
    private String apellidos;
    private String numeroCuenta;
    private double saldo;

    // Constructor
    public AccountDTO(String nombre, String apellidos, String numeroCuenta, double saldo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

  
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
