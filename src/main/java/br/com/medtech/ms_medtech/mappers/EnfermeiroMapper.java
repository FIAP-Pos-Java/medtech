package br.com.medtech.ms_medtech.mappers;
import br.com.medtech.ms_medtech.dtos.enfermeiros.AtualizarEnfermeiroDTO;
import br.com.medtech.ms_medtech.dtos.enfermeiros.CadastrarEnfermeiroDTO;
import br.com.medtech.ms_medtech.dtos.enfermeiros.MostrarEnfermeiroDTO;
import br.com.medtech.ms_medtech.dtos.enfermeiros.MostrarTodosEnfermeirosDTO;
import br.com.medtech.ms_medtech.entities.Enfermeiro;
import br.com.medtech.ms_medtech.entities.Medico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnfermeiroMapper {
    @Mapping(target = "coren", source = "coren")
    Enfermeiro toCadastrarEnfermeiro(CadastrarEnfermeiroDTO cadastrarEnfermeiroDTO);

    @Mapping(target = "coren", source = "coren")
    Enfermeiro toAtualizarEnfermeiro(AtualizarEnfermeiroDTO atualizarEnfermeiroDTO);

    MostrarEnfermeiroDTO toMostrarEnfermeiroDTO(Enfermeiro enfermeiro);

    MostrarTodosEnfermeirosDTO toMostrarTodosEnfermeirosDTO(Enfermeiro enfermeiro);
}
