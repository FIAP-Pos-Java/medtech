package br.com.medtech.ms_medtech.services;

import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private LoginMapper loginMapper;

    @Mock
    private UUIDUtils uuidUtils;

    @InjectMocks
    private LoginService loginService;

    @Test
    @DisplayName("cadastrando um login com sucesso.")
    void testCadastrandoLoginComSucesso() {
        // given
        CadastrarLoginDTO cadastrarLoginDTO = new CadastrarLoginDTO(UUID.randomUUID(), "teste@gmail.com", "12345");
        Login login = new Login();

        given(this.loginRepository.findByEmail(cadastrarLoginDTO.email())).willReturn(Collections.emptyList());
        given(this.loginMapper.toCadastrarLogin(cadastrarLoginDTO)).willReturn(login);

        // when
        this.loginService.cadastrarLogin(cadastrarLoginDTO);

        // then
        then(this.loginRepository).should(times(1)).save(login);
        assertThat(login.getDataUltimoLogin() != null, is(true));
    }

    @Test
    @DisplayName("cadastrando login sem sucesso")
    void testCadastrandoLoginSemSucesso() {
        // given
        CadastrarLoginDTO cadastrarLoginDTO = new CadastrarLoginDTO(UUID.randomUUID(), "teste@gmail.com", "12345");
        Login existente = new Login();

        given(this.loginRepository.findByEmail(cadastrarLoginDTO.email())).willReturn(List.of(existente));

        // when
        LoginCadastradoException exception = assertThrows(LoginCadastradoException.class, () -> this.loginService.cadastrarLogin(cadastrarLoginDTO));

        // then
        assertThat(exception.getMessage(), containsString("este login já existe no sistema"));
    }

    @Test
    @DisplayName("buscando login por id com sucesso")
    void testBuscandoLoginPorIdComSucesso() {
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        Login login = new Login();
        MostrarLoginDTO mostrarLoginDTO = new MostrarLoginDTO(login.getId(), login.getEmail(), login.getPassword(), login.getDataUltimoLogin());

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.loginRepository.findById(id)).willReturn(Optional.of(login));
        given(this.loginMapper.toMostrarLoginDTO(login)).willReturn(mostrarLoginDTO);

        // when
        MostrarLoginDTO resultado = this.loginService.buscarLoginPorId(idStr);

        // then
        assertThat(resultado, is(notNullValue()));
    }

    @Test
    @DisplayName("buscando login por id sem sucesso")
    void testBuscandoLoginSemSucesso() {
        // when
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.loginRepository.findById(id)).willReturn(Optional.empty());

        // when
        LoginNaoEncontradoException exception = assertThrows(LoginNaoEncontradoException.class, () -> this.loginService.buscarLoginPorId(idStr));

        // then
        assertThat(exception.getMessage(), containsString("este login não está cadastrado"));
    }

    @Test
    @DisplayName("buscando por todos login com sucesso")
    void testBuscandoLoginComSucesso() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Login l1 = new Login();
        l1.setId(UUID.randomUUID());
        Login l2 = new Login();
        l2.setId(UUID.randomUUID());

        List<Login> logins = Arrays.asList(l1, l2);
        Page<Login> pageLogins = new PageImpl<>(logins, pageable, logins.size());

        MostrarTodosLoginsTDO m1 = new MostrarTodosLoginsTDO(l1.getId(), l1.getEmail(), l1.getPassword(), l1.getDataUltimoLogin());
        MostrarTodosLoginsTDO m2 = new MostrarTodosLoginsTDO(l2.getId(), l2.getEmail(), l2.getPassword(), l2.getDataUltimoLogin());

        given(this.loginRepository.findAll(pageable)).willReturn(pageLogins);
        given(this.loginMapper.toMostrarTodosLoginsTDO(l1)).willReturn(m1);
        given(this.loginMapper.toMostrarTodosLoginsTDO(l2)).willReturn(m2);

        // when
        Page<MostrarTodosLoginsTDO> resultado = this.loginService.buscarTodosLogins(page, size);

        // then
        assertThat(resultado, is(notNullValue()));
        assertThat(resultado.getContent(), hasSize(2));

    }

    @Test
    @DisplayName("atualizando login com sucesso")
    void testAtualizandoLoginComSucesso() {
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();
        AtualizarLoginDTO atualizarLoginDTO = new AtualizarLoginDTO(UUID.randomUUID(), "teste@gmail.com", "12345");
        Login loginExistente = new Login();
        Login loginAtualizado = new Login();

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.loginRepository.findById(id)).willReturn(Optional.of(loginExistente));
        given(this.loginMapper.toAtualizarLogin(atualizarLoginDTO)).willReturn(loginAtualizado);

        // when
        this.loginService.atualizarLogin(idStr, atualizarLoginDTO);

        // then
        assertThat(loginAtualizado.getId(), is(id));
    }

    @Test
    @DisplayName("atualizando login sem sucesso")
    void testAtualizandoLoginSemSucesso() {
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();
        AtualizarLoginDTO atualizarLoginDTO = new AtualizarLoginDTO(UUID.randomUUID(), "teste@gmail.com", "12345");

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.loginRepository.findById(id)).willReturn(Optional.empty());

        // when
        LoginNaoEncontradoException exception = assertThrows(LoginNaoEncontradoException.class, () -> this.loginService.atualizarLogin(idStr, atualizarLoginDTO));

        // then
        assertThat(exception.getMessage(), containsString("este login não está cadastrado"));
    }

    @Test
    @DisplayName("deletando login por id com sucesso")
    void testDeletandoLoginPorIdComSucesso() {
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();
        Login loginExistente = new Login();

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.loginRepository.findById(id)).willReturn(Optional.of(loginExistente));

        // when
        this.loginService.deletarLoginPorId(idStr);

        // then
        assertThat(loginExistente.getId(), is(nullValue()));
    }

    @Test
    @DisplayName("deletando login por id sem sucessso")
    void testDeletandoLoginSemSucesso() {
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.loginRepository.findById(id)).willReturn(Optional.empty());

        // when
        LoginNaoEncontradoException exception = assertThrows(LoginNaoEncontradoException.class, () -> this.loginService.deletarLoginPorId(idStr));

        // then
        assertThat(exception.getMessage(), containsString("este login não está cadastrado"));
    }
}