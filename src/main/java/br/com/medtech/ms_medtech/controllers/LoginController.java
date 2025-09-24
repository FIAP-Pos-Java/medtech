package br.com.medtech.ms_medtech.controllers;


import br.com.medtech.ms_medtech.dtos.logins.AtualizarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.CadastrarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.MostrarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.MostrarTodosLoginsTDO;
import br.com.medtech.ms_medtech.services.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping
    public ResponseEntity<Void> cadastrarLogin(
            @RequestBody @Valid CadastrarLoginDTO cadastrarLoginDTO
    ) {
        this.logger.info("POST -> /login");
        this.loginService.cadastrarLogin(cadastrarLoginDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MostrarLoginDTO> buscarLoginPorId(
            @PathVariable String id
    ){
        this.logger.info("GET -> /login/"+id);
        var resultado = this.loginService.buscarLoginPorId(id);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<MostrarTodosLoginsTDO>> buscarTodosLogins(
            @RequestParam int page,
            @RequestParam int size
    ){
        this.logger.info("GET -> /login/");
        var resultado = this.loginService.buscarTodosLogins(page, size);
        return new ResponseEntity(resultado.getContent(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerLoginPorId(
            @PathVariable String id
    ){
        this.logger.info("DELETE -> /login/"+id);
        this.loginService.deletarLoginPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarLogin(
            @PathVariable String id,
            @RequestBody @Valid AtualizarLoginDTO atualizarLoginDTO
    ){
        this.logger.info("PUT -> /login/"+id);
        this.loginService.atualizarLogin(id, atualizarLoginDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}