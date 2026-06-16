/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.import_excel;

/**
 *
 * @author LUIS.CASTANEDAL
 */

public class import_excel_error_fila_dto {

    private int fila;
    private String columna;
    private String detalle;

    public import_excel_error_fila_dto() {
    }

    public import_excel_error_fila_dto(int fila, String columna, String detalle) {
        this.fila = fila;
        this.columna = columna;
        this.detalle = detalle;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}