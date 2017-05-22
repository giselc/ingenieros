/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Gisel
 */
public class Vehiculo {
    String matriculaONU;
    String matriculaUY;
    String marca;
    String modelo;
    int nroChasis;
    int nroMotor;
    double consumo;
    int tipoVehiculo;
    boolean horas;
    int horaskilometrosInicial;
    int horaskilometrosTotal;

    public Vehiculo(String matriculaONU, String matriculaUY, String marca, String modelo, int nroChasis, int nroMotor, double consumo, int tipoVehiculo, boolean horas, int horaskilometrosInicial, int horaskilometrosTotal) {
        this.matriculaONU = matriculaONU;
        this.matriculaUY = matriculaUY;
        this.marca = marca;
        this.modelo = modelo;
        this.nroChasis = nroChasis;
        this.nroMotor = nroMotor;
        this.consumo = consumo;
        this.tipoVehiculo = tipoVehiculo;
        this.horas = horas;
        this.horaskilometrosInicial = horaskilometrosInicial;
        this.horaskilometrosTotal = horaskilometrosTotal;
    }

    public String getMatriculaONU() {
        return matriculaONU;
    }

    public String getMatriculaUY() {
        return matriculaUY;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getNroChasis() {
        return nroChasis;
    }

    public int getNroMotor() {
        return nroMotor;
    }

    public double getConsumo() {
        return consumo;
    }

    public int getTipoVehiculo() {
        return tipoVehiculo;
    }

    public boolean isHoras() {
        return horas;
    }

    public int getHoraskilometrosTotal() {
        return horaskilometrosTotal;
    }

    public int getHoraskilometrosInicial() {
        return horaskilometrosInicial;
    }
    
    
    
}
