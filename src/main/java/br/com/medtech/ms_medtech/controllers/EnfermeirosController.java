package br.com.medtech.ms_medtech.controllers;

import br.com.medtech.ms_medtech.dtos.enfermeiros.*;
import br.com.medtech.ms_medtech.services.EnfermeiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("enfermeiros")
@RequiredArgsConstructor
public class EnfermeirosController {

    private final EnfermeiroService enfermeiroService;

    private static final Logger logger = LoggerFactory.getLogger(EnfermeirosController.class);

    @PostMapping
    public ResponseEntity<Void> cadastrarenfermeiro(
            @RequestBody @Valid CadastrarEnfermeiroDTO cadastrarenfermeiroDTO
    ) {
        this.logger.info("POST -> /enfermeiros");
        this.enfermeiroService.cadastrarEnfermeiro(cadastrarenfermeiroDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MostrarTodosEnfermeirosDTO>> buscarTodosenfermeiros(
            @RequestParam int page,
            @RequestParam int size
    ) {
        this.logger.info("GET -> /enfermeiros");
        var resultado = this.enfermeiroService.buscarTodosEnfermeiros(page, size);
        return new ResponseEntity(resultado.getContent(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MostrarEnfermeiroDTO> buscarenfermeiroPorId(
            @PathVariable String id
    ) {
        this.logger.info("GET -> /enfermeiros/" + id);
        var resultado = this.enfermeiroService.buscarEnfermeiroPorId(id);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarEnfermeiroPorId(
            @PathVariable String id
    ) {
        this.logger.info("DELETE -> /enfermeiros/" + id);
        this.enfermeiroService.deletarEnfermeiroPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarenfermeiro(
            @PathVariable String id,
            @RequestBody AtualizarEnfermeiroDTO atualizarenfermeiroDTO
    ) {
        this.logger.info("PUT -> /enfermeiros/" + id);
        this.enfermeiroService.atualizarEnfermeiro(id, atualizarenfermeiroDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
