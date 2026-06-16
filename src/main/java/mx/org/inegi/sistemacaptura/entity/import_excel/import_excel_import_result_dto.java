/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.import_excel;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.ArrayList;
import java.util.List;

public class import_excel_import_result_dto {

    private boolean ok;
    private String message;

    private int filasTotales;
    private int filasConDatos;
    private int filasImportadas;
    private int filasConError;

    private int fuentesInsertadas;
    private int fuentesActualizadas;

    private int variablesInsertadas;
    private int variablesActualizadas;

    private int mdeaInsertados;
    private int mdeaOmitidos;

    private int odsInsertados;
    private int odsOmitidos;

    private int pertinenciaInsertadas;
    private int pertinenciaActualizadas;

    private List<import_excel_error_fila_dto> errors =
            new ArrayList<import_excel_error_fila_dto>();

    public import_excel_import_result_dto() {
    }

    public import_excel_import_result_dto(
            boolean ok,
            String message,
            int filasTotales,
            int filasConDatos,
            int filasImportadas,
            int filasConError,
            int fuentesInsertadas,
            int fuentesActualizadas,
            int variablesInsertadas,
            int variablesActualizadas,
            int mdeaInsertados,
            int mdeaOmitidos,
            int odsInsertados,
            int odsOmitidos,
            int pertinenciaInsertadas,
            int pertinenciaActualizadas,
            List<import_excel_error_fila_dto> errors) {
        this.ok = ok;
        this.message = message;
        this.filasTotales = filasTotales;
        this.filasConDatos = filasConDatos;
        this.filasImportadas = filasImportadas;
        this.filasConError = filasConError;
        this.fuentesInsertadas = fuentesInsertadas;
        this.fuentesActualizadas = fuentesActualizadas;
        this.variablesInsertadas = variablesInsertadas;
        this.variablesActualizadas = variablesActualizadas;
        this.mdeaInsertados = mdeaInsertados;
        this.mdeaOmitidos = mdeaOmitidos;
        this.odsInsertados = odsInsertados;
        this.odsOmitidos = odsOmitidos;
        this.pertinenciaInsertadas = pertinenciaInsertadas;
        this.pertinenciaActualizadas = pertinenciaActualizadas;
        this.errors = errors;
    }

    public static import_excel_import_result_dto ok(
            String msg,
            int tot,
            int conDatos,
            int importadas,
            int fuentesIns,
            int fuentesUpd,
            int varsIns,
            int varsUpd,
            int mdeaIns,
            int mdeaOmi,
            int odsIns,
            int odsOmi,
            int perIns,
            int perUpd) {
        return new import_excel_import_result_dto(
                true,
                msg,
                tot,
                conDatos,
                importadas,
                0,
                fuentesIns,
                fuentesUpd,
                varsIns,
                varsUpd,
                mdeaIns,
                mdeaOmi,
                odsIns,
                odsOmi,
                perIns,
                perUpd,
                new ArrayList<import_excel_error_fila_dto>());
    }

    public static import_excel_import_result_dto fail(
            String msg,
            int tot,
            int conDatos,
            int importadas,
            int fuentesIns,
            int fuentesUpd,
            int varsIns,
            int varsUpd,
            int mdeaIns,
            int mdeaOmi,
            int odsIns,
            int odsOmi,
            int perIns,
            int perUpd,
            List<import_excel_error_fila_dto> errs) {
        return new import_excel_import_result_dto(
                false,
                msg,
                tot,
                conDatos,
                importadas,
                errs == null ? 0 : errs.size(),
                fuentesIns,
                fuentesUpd,
                varsIns,
                varsUpd,
                mdeaIns,
                mdeaOmi,
                odsIns,
                odsOmi,
                perIns,
                perUpd,
                errs == null
                        ? new ArrayList<import_excel_error_fila_dto>()
                        : errs);
    }

    public static import_excel_import_result_dto fail(
            String msg,
            int tot,
            int conDatos,
            List<import_excel_error_fila_dto> errs) {
        return fail(
                msg,
                tot,
                conDatos,
                0,
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                errs);
    }

    public static import_excel_import_result_dto ok(
            String msg,
            int tot,
            int conDatos,
            int importadas) {
        return ok(
                msg,
                tot,
                conDatos,
                importadas,
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                0, 0);
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFilasTotales() {
        return filasTotales;
    }

    public void setFilasTotales(int filasTotales) {
        this.filasTotales = filasTotales;
    }

    public int getFilasConDatos() {
        return filasConDatos;
    }

    public void setFilasConDatos(int filasConDatos) {
        this.filasConDatos = filasConDatos;
    }

    public int getFilasImportadas() {
        return filasImportadas;
    }

    public void setFilasImportadas(int filasImportadas) {
        this.filasImportadas = filasImportadas;
    }

    public int getFilasConError() {
        return filasConError;
    }

    public void setFilasConError(int filasConError) {
        this.filasConError = filasConError;
    }

    public int getFuentesInsertadas() {
        return fuentesInsertadas;
    }

    public void setFuentesInsertadas(int fuentesInsertadas) {
        this.fuentesInsertadas = fuentesInsertadas;
    }

    public int getFuentesActualizadas() {
        return fuentesActualizadas;
    }

    public void setFuentesActualizadas(int fuentesActualizadas) {
        this.fuentesActualizadas = fuentesActualizadas;
    }

    public int getVariablesInsertadas() {
        return variablesInsertadas;
    }

    public void setVariablesInsertadas(int variablesInsertadas) {
        this.variablesInsertadas = variablesInsertadas;
    }

    public int getVariablesActualizadas() {
        return variablesActualizadas;
    }

    public void setVariablesActualizadas(int variablesActualizadas) {
        this.variablesActualizadas = variablesActualizadas;
    }

    public int getMdeaInsertados() {
        return mdeaInsertados;
    }

    public void setMdeaInsertados(int mdeaInsertados) {
        this.mdeaInsertados = mdeaInsertados;
    }

    public int getMdeaOmitidos() {
        return mdeaOmitidos;
    }

    public void setMdeaOmitidos(int mdeaOmitidos) {
        this.mdeaOmitidos = mdeaOmitidos;
    }

    public int getOdsInsertados() {
        return odsInsertados;
    }

    public void setOdsInsertados(int odsInsertados) {
        this.odsInsertados = odsInsertados;
    }

    public int getOdsOmitidos() {
        return odsOmitidos;
    }

    public void setOdsOmitidos(int odsOmitidos) {
        this.odsOmitidos = odsOmitidos;
    }

    public int getPertinenciaInsertadas() {
        return pertinenciaInsertadas;
    }

    public void setPertinenciaInsertadas(int pertinenciaInsertadas) {
        this.pertinenciaInsertadas = pertinenciaInsertadas;
    }

    public int getPertinenciaActualizadas() {
        return pertinenciaActualizadas;
    }

    public void setPertinenciaActualizadas(int pertinenciaActualizadas) {
        this.pertinenciaActualizadas = pertinenciaActualizadas;
    }

    public List<import_excel_error_fila_dto> getErrors() {
        return errors;
    }

    public void setErrors(List<import_excel_error_fila_dto> errors) {
        this.errors = errors;
    }
}