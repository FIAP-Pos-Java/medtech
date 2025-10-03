package br.com.medtech.ms_medtech.mappers;

import br.com.medtech.ms_medtech.dtos.medicos.AtualizarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.CadastrarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.MostrarMedicoDTO;
import br.com.medtech.ms_medtech.dtos.medicos.MostrarTodosMedicosDTO;
import br.com.medtech.ms_medtech.entities.Medico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
    Medico toCadastrarMedico(CadastrarMedicoDTO cadastrarMedicoDTO);
    Medico toAtualizarMedico(AtualizarMedicoDTO atualizarMedicoDTO);
    MostrarMedicoDTO toMostrarMedicoDTO(Medico medico);
    MostrarTodosMedicosDTO toMostrarTodosMedicosDTO(Medico medico);
}
