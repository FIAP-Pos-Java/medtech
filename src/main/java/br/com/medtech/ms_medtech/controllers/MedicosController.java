package br.com.medtech.ms_medtech.controllers;

import br.com.medtech.ms_medtech.dtos.medicos.AtualizarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.CadastrarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.MostrarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.MostrarTodosMedicosDTO;
import br.com.medtech.ms_medtech.services.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
@RequiredArgsConstructor
public class MedicosController {

    private final MedicoService medicoService;

    private static final Logger logger = LoggerFactory.getLogger(MedicosController.class);

    @PostMapping
    public ResponseEntity<Void> cadastrarMedico(
            @RequestBody @Valid CadastrarMedicoDTO cadastrarMedicoDTO
    ) {
        this.logger.info("POST -> /medicos");
        this.medicoService.cadastrarMedico(cadastrarMedicoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MostrarTodosMedicosDTO>> buscarTodosMedicos(
            @RequestParam int page,
            @RequestParam int size
    ) {
        this.logger.info("GET -> /medicos");
        var resultado = this.medicoService.buscarTodosMedicos(page, size);
        return new ResponseEntity(resultado.getContent(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MostrarMedicoDTO> buscarMedicoPorId(
            @PathVariable String id
    ) {
        this.logger.info("GET -> /medicos/" + id);
        var resultado = this.medicoService.buscarMedicoPorId(id);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarMedicoPorId(
            @PathVariable String id
    ) {
        this.logger.info("DELETE -> /medicos/" + id);
        this.medicoService.deletarMedicoPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarMedico(
            @PathVariable String id,
            @RequestBody AtualizarMedicoDTO atualizarMedicoDTO
    ) {
        this.logger.info("PUT -> /Medicos/" + id);
        this.medicoService.atualizarMedico(id, atualizarMedicoDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
