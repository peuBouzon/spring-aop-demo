package com.betha.springaop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class RelatorioDto {
    private Long id;
    private LocalDate data;
    private String texto;
    private List<String> referencias;
}
