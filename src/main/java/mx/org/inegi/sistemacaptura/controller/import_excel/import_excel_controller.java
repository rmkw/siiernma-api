/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller.import_excel;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.security.Principal;
import mx.org.inegi.sistemacaptura.entity.import_excel.import_excel_import_result_dto;
import mx.org.inegi.sistemacaptura.entity.import_excel.import_excel_validacion_dto;
import mx.org.inegi.sistemacaptura.service.import_excel.import_excel_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import-excel")
public class import_excel_controller {

    @Autowired
    private import_excel_service service;

    @PostMapping(value = "/validar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public import_excel_validacion_dto validar(
            @RequestPart("file") MultipartFile file) {
        return service.validar(file);
    }

    @PostMapping(value = "/importar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public import_excel_import_result_dto importar(
            @RequestPart("file") MultipartFile file,
            Principal principal) {
        return service.importar(file, principal);
    }
}
