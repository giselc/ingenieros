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
public class LoteMunicion {
    private int id;
    private String idLote;
    private Municion municion;
    private int cantidad;
    private int cantMunicionViva;
    private int vainas;

    public LoteMunicion(int id, String idLote, Municion municion, int cantidad, int cantMunicionViva, int vainas) {
        this.id = id;
        this.idLote = idLote;
        this.municion = municion;
        this.cantidad = cantidad;
        this.cantMunicionViva = cantMunicionViva;
        this.vainas = vainas;
    }

    public int getId() {
        return id;
    }

    public String getIdLote() {
        return idLote;
    }

    public Municion getMunicion() {
        return municion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getCantMunicionViva() {
        return cantMunicionViva;
    }

    public int getVainas() {
        return vainas;
    }
    
    
    
}
