package com.betha.springaop.service;

import com.betha.springaop.dto.RelatorioDto;
import com.betha.springaop.utils.Retry;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class RelatorioService {

    private int tentativas = 0;

    public RelatorioDto find(Long id, List<String> referencias) {
        return new RelatorioDto(id, LocalDate.now(), "[...]", referencias);
    }

    @Retry
    public List<String> getReferencias() {
        simularErro();
        return Collections.singletonList("wikipedia");
    }

    private void simularErro() {
        if (tentativas < 2) {
            tentativas++;
            throw new RuntimeException("Erro ao buscar referências");
        }
        tentativas = 0;
    }
}
