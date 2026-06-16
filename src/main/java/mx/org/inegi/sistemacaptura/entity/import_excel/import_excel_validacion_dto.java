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

public class import_excel_validacion_dto {

    private boolean ok;
    private String message;
    private List<import_excel_error_fila_dto> errors = new ArrayList<import_excel_error_fila_dto>();

    public import_excel_validacion_dto() {
    }

    public import_excel_validacion_dto(boolean ok, String message,
            List<import_excel_error_fila_dto> errors) {
        this.ok = ok;
        this.message = message;
        this.errors = errors;
    }

    public static import_excel_validacion_dto ok(String message) {
        return new import_excel_validacion_dto(
                true,
                message,
                new ArrayList<import_excel_error_fila_dto>());
    }

    public static import_excel_validacion_dto fail(
            String message,
            List<import_excel_error_fila_dto> errors) {
        return new import_excel_validacion_dto(false, message, errors);
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

    public List<import_excel_error_fila_dto> getErrors() {
        return errors;
    }

    public void setErrors(List<import_excel_error_fila_dto> errors) {
        this.errors = errors;
    }
}
