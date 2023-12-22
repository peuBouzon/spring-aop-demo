package com.betha.springaop.controller;

import com.betha.springaop.dto.RelatorioDto;
import com.betha.springaop.service.RelatorioService;
import com.betha.springaop.utils.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("relatorio")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("{id}")
    public RelatorioDto getRelatorio(@PathVariable(value = "id") long id) {
        List<String> referencias = service.getReferencias();
        return service.find(id, referencias);
    }
}
