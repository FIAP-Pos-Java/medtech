package br.com.medtech.ms_medtech.mappers;

import br.com.medtech.ms_medtech.dtos.pacientes.AtualizarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.CadastrarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.MostrarPacienteDTO;
import br.com.medtech.ms_medtech.dtos.pacientes.MostrarTodosPacientesDTO;
import br.com.medtech.ms_medtech.entities.Paciente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    Paciente toCadastrarPaciente(CadastrarPacienteDTO cadastrarPacienteDTO);
    Paciente toAtualizarPaciente(AtualizarPacienteDTO atualizarPacienteDTO);
    MostrarPacienteDTO toMostrarPacienteDTO(Paciente paciente);
    MostrarTodosPacientesDTO toMostrarTodosPacientesDTO(Paciente paciente);
}