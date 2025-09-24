package br.com.medtech.ms_medtech.mappers;


import br.com.medtech.ms_medtech.dtos.logins.AtualizarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.CadastrarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.MostrarLoginDTO;
import br.com.medtech.ms_medtech.dtos.logins.MostrarTodosLoginsTDO;
import br.com.medtech.ms_medtech.entities.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    Login toCadastrarLogin(CadastrarLoginDTO cadastrarLoginDTO);
    Login toAtualizarLogin(AtualizarLoginDTO atualizarLoginDTO);
    MostrarLoginDTO toMostrarLoginDTO(Login login);
    MostrarTodosLoginsTDO toMostrarTodosLoginsTDO(Login login);
}