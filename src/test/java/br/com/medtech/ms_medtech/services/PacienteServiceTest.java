package br.com.medtech.ms_medtech.services;

import br.com.medtech.ms_medtech.converters.UUIDUtils;
import br.com.medtech.ms_medtech.dtos.pacientes.AtualizarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.CadastrarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.MostrarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.MostrarTodosPacientesDTO;
import br.com.medtech.ms_medtech.entities.Login;
import br.com.medtech.ms_medtech.entities.Paciente;
import br.com.medtech.ms_medtech.exceptions.LoginNaoEncontradoException;
import br.com.medtech.ms_medtech.exceptions.UsuarioCadastradoException;
import br.com.medtech.ms_medtech.exceptions.UsuarioNaoEncontradoException;
import br.com.medtech.ms_medtech.mappers.PacienteMapper;
import br.com.medtech.ms_medtech.repositories.LoginRepository;
import br.com.medtech.ms_medtech.repositories.PacienteRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;


@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private PacienteMapper pacienteMapper;

    @Mock
    private UUIDUtils uuidUtils;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    @DisplayName("cadastrando um paciente com sucesso")
    void testeCadastrarPacienteComSucesso(){
        // given
        var dto = mock(CadastrarPacienteDTO.class);
        var login = mock(Login.class);
        UUID loginId = UUID.randomUUID();

        given(dto.login()).willReturn(login);
        given(login.getId()).willReturn(loginId);
        given(this.loginRepository.findById(loginId)).willReturn(Optional.of(login));
        given(this.pacienteRepository.findByLogin_Id(loginId)).willReturn(Optional.empty());

        Paciente paciente = new Paciente();
        given(this.pacienteMapper.toCadastrarPaciente(dto)).willReturn(paciente);

        // when
        this.pacienteService.cadastrarPaciente(dto);

        // then
        assertThat(paciente.getLogin(), equalTo(login));
        verify(pacienteRepository, times(1)).save(paciente);
    }


    @Test
    @DisplayName("cadastrando um paciente com login sem sucesso")
    void testeCadastrandoUmPacienteComLoginSemSucesso(){
        // given
        var dto = mock(CadastrarPacienteDTO.class);
        var login = mock(Login.class);
        UUID loginId = UUID.randomUUID();
        given(dto.login()).willReturn(login);
        given(login.getId()).willReturn(loginId);
        given(this.loginRepository.findById(any())).willReturn(Optional.empty());

        // when
        LoginNaoEncontradoException exception = assertThrows(LoginNaoEncontradoException.class, () -> this.pacienteService.cadastrarPaciente(dto));

        // then
        assertThat(exception.getMessage(), containsString("este login não existe no sistema, por isso não pode ser feito referencia"));
    }

    @Test
    @DisplayName("cadastrandum paciente que ja existe sem sucesso")
    void testCadastrandoUmPacienteQueJaExisteSemSucesso(){
        var dto = mock(CadastrarPacienteDTO.class);
        var login = mock(Login.class);
        UUID loginId = UUID.randomUUID();
        given(dto.login()).willReturn(login);
        given(login.getId()).willReturn(loginId);
        given(this.loginRepository.findById(loginId)).willReturn(Optional.of(login));
        given(this.pacienteRepository.findByLogin_Id(loginId)).willReturn(Optional.of(new Paciente()));

        // when
        UsuarioCadastradoException exception = assertThrows(UsuarioCadastradoException.class, () -> this.pacienteService.cadastrarPaciente(dto));

        // then
        assertThat(exception.getMessage(), containsString("este paciente já existe no sistema"));
    }

    @Test
    @DisplayName("buscando paciente pelo id com sucesso")
    void testBuscandoPacientePorIdComSucesso(){
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        Paciente paciente = mock(Paciente.class);
        paciente.setId(id);

        MostrarPacienteDTO dto = mock(MostrarPacienteDTO.class);

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.pacienteRepository.findById(id)).willReturn(Optional.of(paciente));
        given(this.pacienteMapper.toMostrarPacienteDTO(paciente)).willReturn(dto);

        // when
        MostrarPacienteDTO resultado = this.pacienteService.buscarPacientePorId(idStr);

        // then
        assertThat(resultado, is(notNullValue()));
    }

    @Test
    @DisplayName("buscando paciente por id sem sucesso")
    void testBuscandoPacientePorIdSemSucesso(){
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.pacienteRepository.findById(id)).willReturn(Optional.empty());

        // when
        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> this.pacienteService.buscarPacientePorId(idStr));

        // then
        assertThat(exception.getMessage(), containsString("este paciente não está cadastrado"));
    }

    @Test
    @DisplayName("buscando todos os pacientes com sucesso")
    void testBuscandoTodosOsPacienteComSucesso(){
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Paciente p1 = mock(Paciente.class);
        Paciente p2 = mock(Paciente.class);

        List<Paciente> pacientes = List.of(p1, p2);
        Page<Paciente> pagePacientes = new PageImpl<>(pacientes, pageable, pacientes.size());

        MostrarTodosPacientesDTO m1 = mock(MostrarTodosPacientesDTO.class);
        MostrarTodosPacientesDTO m2 = mock(MostrarTodosPacientesDTO.class);

        given(this.pacienteRepository.findAll(pageable)).willReturn(pagePacientes);
        given(this.pacienteMapper.toMostrarTodosPacientesDTO(p1)).willReturn(m1);
        given(this.pacienteMapper.toMostrarTodosPacientesDTO(p2)).willReturn(m2);

        // when
        Page<MostrarTodosPacientesDTO> resultado = this.pacienteService.buscarTodosPacientes(page, size);

        //then
        assertThat(resultado, is(notNullValue()));
        assertThat(resultado.getContent(), hasSize(2));
    }

    @Test
    @DisplayName("deletando paciente com sucesso")
    void testDeletandoPacienteComSucesso(){
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        Paciente paciente = mock(Paciente.class);

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.pacienteRepository.findById(id)).willReturn(Optional.of(paciente));

        // when
        this.pacienteService.deletarPacientePorId(idStr);

        // then
        verify(this.pacienteRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("deletando paciente por id sem sucesso")
    void testDeletandoPacientePorIdSemSucesso(){
        //given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.pacienteRepository.findById(id)).willReturn(Optional.empty());

        // when
        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> this.pacienteService.deletarPacientePorId(idStr));

        // then
        assertThat(exception.getMessage(), containsString("este paciente não está cadastrado"));
    }

    @Test
    @DisplayName("atualizando um paciente com sucesso")
    void testAtualizandoPacienteComSucesso(){
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        Paciente paciente = mock(Paciente.class);
        Paciente pacienteAtualizado = mock(Paciente.class);
        pacienteAtualizado.setId(id);
        pacienteAtualizado.setNome("nome atualizado paciente");
        AtualizarPacienteDTO dto = mock(AtualizarPacienteDTO.class);

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.pacienteRepository.findById(id)).willReturn(Optional.of(paciente));
        given(this.pacienteMapper.toAtualizarPaciente(dto)).willReturn(pacienteAtualizado);

        // when
        this.pacienteService.atualizarPaciente(idStr, dto);

        // then
        verify(this.pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    @DisplayName("atualizando um paciente sem sucesso")
    void testAtualizandoPacienteSemSucesso(){
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        AtualizarPacienteDTO dto = mock(AtualizarPacienteDTO.class);

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.pacienteRepository.findById(id)).willReturn(Optional.empty());

        // when
        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> this.pacienteService.atualizarPaciente(idStr, dto));

        // then
        assertThat(exception.getMessage(), containsString("este paciente não está cadastrado"));
    }
}