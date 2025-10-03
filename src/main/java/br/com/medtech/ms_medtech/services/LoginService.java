package br.com.medtech.ms_medtech.services;

import br.com.medtech.ms_medtech.converters.UUIDUtils;
import br.com.medtech.ms_medtech.dtos.logins.AtualizarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.CadastrarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.MostrarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.MostrarTodosLoginsTDO;
import br.com.medtech.ms_medtech.entities.Login;
import br.com.medtech.ms_medtech.exceptions.LoginCadastradoException;
import br.com.medtech.ms_medtech.exceptions.LoginNaoEncontradoException;
import br.com.medtech.ms_medtech.mappers.LoginMapper;
import br.com.medtech.ms_medtech.repositories.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;
    private final UUIDUtils uuidUtils;
    private final PasswordEncoder passwordEncoder;

    private final String MESSAGE_LOGIN_ENCONTRADO = "este login já existe no sistema";
    private final String MESSAGE_lOGIN_NAO_ENCONTRADO = "este login não está cadastrado";

    public void cadastrarLogin(CadastrarLoginDTO cadastrarLoginDTO){
        var buscandoLogin = this.loginRepository.findByEmail(cadastrarLoginDTO.email());

        if(!buscandoLogin.isEmpty()){
            throw new LoginCadastradoException(MESSAGE_LOGIN_ENCONTRADO);
        }

        Login cadastrarLogin = this.loginMapper.toCadastrarLogin(cadastrarLoginDTO);
        cadastrarLogin.setDataUltimoLogin(LocalDateTime.now());
        cadastrarLogin.setPassword(this.passwordEncoder.encode(cadastrarLoginDTO.password()));
        this.loginRepository.save(cadastrarLogin);
    }

    public MostrarLoginDTO buscarLoginPorId(String idStr){
        UUID id = uuidUtils.retornaStringSemHifen(idStr);

        var buscandoLogin = this.loginRepository.findById(id).orElseThrow(() -> new LoginNaoEncontradoException(MESSAGE_lOGIN_NAO_ENCONTRADO));

        MostrarLoginDTO mostrarLoginDTO = this.loginMapper.toMostrarLoginDTO(buscandoLogin);
        return mostrarLoginDTO;
    }

    public Page<MostrarTodosLoginsTDO> buscarTodosLogins(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Login> logins = this.loginRepository.findAll(pageable);
        Page<MostrarTodosLoginsTDO> mostrarTodosLoginsTDO = logins.map(loginMapper::toMostrarTodosLoginsTDO);
        return mostrarTodosLoginsTDO;
    }

    public void deletarLoginPorId(String idStr){
        UUID id = uuidUtils.retornaStringSemHifen(idStr);

        var buscandoLogin = this.loginRepository.findById(id);

        if(buscandoLogin.isEmpty()){
            throw new LoginNaoEncontradoException(MESSAGE_lOGIN_NAO_ENCONTRADO);
        }

        this.loginRepository.deleteById(id);
    }

    public void atualizarLogin(String idStr, AtualizarLoginDTO atualizarLoginDTO){
        UUID id = uuidUtils.retornaStringSemHifen(idStr);
        var buscandoLogin = this.loginRepository.findById(id);

        if(buscandoLogin.isEmpty()){
            throw new LoginNaoEncontradoException(MESSAGE_lOGIN_NAO_ENCONTRADO);
        }

        Login loginAtualizado = this.loginMapper.toAtualizarLogin(atualizarLoginDTO);
        loginAtualizado.setId(id);
        loginAtualizado.setDataUltimoLogin(LocalDateTime.now());
        loginAtualizado.setPassword(passwordEncoder.encode(atualizarLoginDTO.password()));
        this.loginRepository.save(loginAtualizado);
    }
}