/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.import_excel;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.entity.import_excel.import_excel_error_fila_dto;

public class ImportExcelAbortException extends RuntimeException {

    private final List<import_excel_error_fila_dto> errors;

    public ImportExcelAbortException(
            String message,
            List<import_excel_error_fila_dto> errors) {
        super(message);
        this.errors = errors;
    }

    public List<import_excel_error_fila_dto> getErrors() {
        return errors;
    }
}
