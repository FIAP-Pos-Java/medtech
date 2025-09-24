package br.com.medtech.ms_medtech.controllers;

import br.com.medtech.ms_medtech.dtos.pacientes.AtualizarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.CadastrarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.MostrarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.MostrarTodosPacientesDTO;
import br.com.medtech.ms_medtech.services.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
@RequiredArgsConstructor
public class PacientesController {

    private final PacienteService pacienteService;

    private static final Logger logger = LoggerFactory.getLogger(PacientesController.class);

    @PostMapping
    public ResponseEntity<Void> cadastrarPaciente(
            @RequestBody @Valid CadastrarPacienteDTO cadastrarPacienteDTO
    ) {
        this.logger.info("POST -> /pacientes");
        this.pacienteService.cadastrarPaciente(cadastrarPacienteDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MostrarTodosPacientesDTO>> buscarTodosPacientes(
            @RequestParam int page,
            @RequestParam int size
    ) {
        this.logger.info("GET -> /pacientes");
        var resultado = this.pacienteService.buscarTodosPacientes(page, size);
        return new ResponseEntity(resultado.getContent(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MostrarPacienteDTO> buscarPacientePorId(
            @PathVariable String id
    ) {
        this.logger.info("GET -> /pacientes/" + id);
        var resultado = this.pacienteService.buscarPacientePorId(id);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarPacientePorId(
            @PathVariable String id
    ) {
        this.logger.info("DELETE -> /pacientes/" + id);
        this.pacienteService.deletarPacientePorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarPaciente(
            @PathVariable String id,
            @RequestBody AtualizarPacienteDTO atualizarPacienteDTO
    ) {
        this.logger.info("PUT -> /pacientes/" + id);
        this.pacienteService.atualizarPaciente(id, atualizarPacienteDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}