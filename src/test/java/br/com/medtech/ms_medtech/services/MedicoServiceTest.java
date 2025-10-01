package br.com.medtech.ms_medtech.services;

import br.com.medtech.ms_medtech.converters.UUIDUtils;
import br.com.medtech.ms_medtech.dtos.medicos.AtualizarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.CadastrarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.MostrarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.MostrarTodosMedicosDTO;
import br.com.medtech.ms_medtech.entities.Login;
import br.com.medtech.ms_medtech.entities.Medico;
import br.com.medtech.ms_medtech.exceptions.LoginNaoEncontradoException;
import br.com.medtech.ms_medtech.exceptions.UsuarioCadastradoException;
import br.com.medtech.ms_medtech.exceptions.UsuarioNaoEncontradoException;
import br.com.medtech.ms_medtech.mappers.MedicoMapper;
import br.com.medtech.ms_medtech.repositories.LoginRepository;
import br.com.medtech.ms_medtech.repositories.MedicoRepository;
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
class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private MedicoMapper medicoMapper;

    @Mock
    private UUIDUtils uuidUtils;

    @InjectMocks
    private MedicoService medicoService;

    @Test
    @DisplayName("cadastrando um Medico com sucesso")
    void testeCadastrarMedicoComSucesso(){
        // given
        var dto = mock(CadastrarMedicoDTO.class);
        var login = mock(Login.class);
        UUID loginId = UUID.randomUUID();

        given(dto.login()).willReturn(login);
        given(login.getId()).willReturn(loginId);
        given(this.loginRepository.findById(loginId)).willReturn(Optional.of(login));
        given(this.medicoRepository.findByLogin_Id(loginId)).willReturn(Optional.empty());

        Medico Medico = new Medico();
        given(this.medicoMapper.toCadastrarMedico(dto)).willReturn(Medico);

        // when
        this.medicoService.cadastrarMedico(dto);

        // then
        assertThat(Medico.getLogin(), equalTo(login));
        verify(medicoRepository, times(1)).save(Medico);
    }

    @Test
    @DisplayName("cadastrando um medico com login sem sucesso")
    void testeCadastrandoUmMedicoComLoginSemSucesso(){
        // given
        var dto = mock(CadastrarMedicoDTO.class);
        var login = mock(Login.class);
        UUID loginId = UUID.randomUUID();
        given(dto.login()).willReturn(login);
        given(login.getId()).willReturn(loginId);
        given(this.loginRepository.findById(any())).willReturn(Optional.empty());

        // when
        LoginNaoEncontradoException exception = assertThrows(LoginNaoEncontradoException.class, () -> this.medicoService.cadastrarMedico(dto));

        // then
        assertThat(exception.getMessage(), containsString("este login não existe no sistema, por isso não pode ser feito referencia"));
    }

    @Test
    @DisplayName("cadastrandum medico que ja existe sem sucesso")
    void testCadastrandoUmMedicoQueJaExisteSemSucesso(){
        var dto = mock(CadastrarMedicoDTO.class);
        var login = mock(Login.class);
        UUID loginId = UUID.randomUUID();
        given(dto.login()).willReturn(login);
        given(login.getId()).willReturn(loginId);
        given(this.loginRepository.findById(loginId)).willReturn(Optional.of(login));
        given(this.medicoRepository.findByLogin_Id(loginId)).willReturn(Optional.of(new Medico()));

        // when
        UsuarioCadastradoException exception = assertThrows(UsuarioCadastradoException.class, () -> this.medicoService.cadastrarMedico(dto));

        // then
        assertThat(exception.getMessage(), containsString("este medico já existe no sistema"));
    }

    @Test
    @DisplayName("buscando medico pelo id com sucesso")
    void testBuscandoMedicoPorIdComSucesso(){
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        Medico medico = mock(Medico.class);

        medico.setId(id);

        MostrarMedicoDTO dto = mock(MostrarMedicoDTO.class);

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.medicoRepository.findById(id)).willReturn(Optional.of(medico));
        given(this.medicoMapper.toMostrarMedicoDTO(medico)).willReturn(dto);

        // when
        MostrarMedicoDTO resultado = this.medicoService.buscarMedicoPorId(idStr);

        // then
        assertThat(resultado, is(notNullValue()));
    }

    @Test
    @DisplayName("buscando medico por id sem sucesso")
    void testBuscandoMedicoPorIdSemSucesso(){
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.medicoRepository.findById(id)).willReturn(Optional.empty());

        // when
        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> this.medicoService.buscarMedicoPorId(idStr));

        // then
        assertThat(exception.getMessage(), containsString("este medico não está cadastrado"));
    }

    @Test
    @DisplayName("buscando todos os medicos com sucesso")
    void testBuscandoTodosOsMedicosComSucesso(){
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Medico p1 = mock(Medico.class);
        Medico p2 = mock(Medico.class);

        List<Medico> medicos = List.of(p1, p2);
        Page<Medico> pageMedicos = new PageImpl<>(medicos, pageable, medicos.size());

        MostrarTodosMedicosDTO m1 = mock(MostrarTodosMedicosDTO.class);
        MostrarTodosMedicosDTO m2 = mock(MostrarTodosMedicosDTO.class);

        given(this.medicoRepository.findAll(pageable)).willReturn(pageMedicos);
        given(this.medicoMapper.toMostrarTodosMedicosDTO(p1)).willReturn(m1);
        given(this.medicoMapper.toMostrarTodosMedicosDTO(p2)).willReturn(m2);

        // when
        Page<MostrarTodosMedicosDTO> resultado = this.medicoService.buscarTodosMedicos(page, size);

        //then
        assertThat(resultado, is(notNullValue()));
        assertThat(resultado.getContent(), hasSize(2));
    }

    @Test
    @DisplayName("deletando medico com sucesso")
    void testDeletandoMedicoComSucesso(){
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        Medico medico = mock(Medico.class);

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.medicoRepository.findById(id)).willReturn(Optional.of(medico));

        // when
        this.medicoService.deletarMedicoPorId(idStr);

        // then
        verify(this.medicoRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("deletando medico por id sem sucesso")
    void testDeletandoMedicoPorIdSemSucesso(){
        //given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.medicoRepository.findById(id)).willReturn(Optional.empty());

        // when
        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> this.medicoService.deletarMedicoPorId(idStr));

        // then
        assertThat(exception.getMessage(), containsString("este medico não está cadastrado"));
    }

    @Test
    @DisplayName("atualizando um medico com sucesso")
    void testAtualizandomedicoComSucesso(){
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        Medico medico = mock(Medico.class);
        Medico medicoAtualizado = mock(Medico.class);
        medicoAtualizado.setId(id);
        medicoAtualizado.setNome("nome atualizado medico");
        AtualizarMedicoDTO dto = mock(AtualizarMedicoDTO.class);

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.medicoRepository.findById(id)).willReturn(Optional.of(medico));
        given(this.medicoMapper.toAtualizarMedico(dto)).willReturn(medicoAtualizado);

        // when
        this.medicoService.atualizarMedico(idStr, dto);

        // then
        verify(this.medicoRepository, times(1)).save(any(Medico.class));
    }

    @Test
    @DisplayName("atualizando um medico sem sucesso")
    void testAtualizandomedicoSemSucesso(){
        // given
        String idStr = "12345678901234567890123456789012";
        UUID id = UUID.randomUUID();

        AtualizarMedicoDTO dto = mock(AtualizarMedicoDTO.class);

        given(this.uuidUtils.retornaStringSemHifen(idStr)).willReturn(id);
        given(this.medicoRepository.findById(id)).willReturn(Optional.empty());

        // when
        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> this.medicoService.atualizarMedico(idStr, dto));

        // then
        assertThat(exception.getMessage(), containsString("este medico não está cadastrado"));
    }

}