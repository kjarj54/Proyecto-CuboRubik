/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1_datos.model;

/**
 *
 * @author kevin
 */
public class Movimientos {

    private int numero;
    private String direccion;

    public Movimientos(int numero, String direccion) {
        this.numero = numero;
        this.direccion = direccion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Movimiento{" + "numero=" + numero + ", descripcion=" + direccion + '}';
    }
}
