package br.com.medtech.ms_medtech.services;

import br.com.medtech.ms_medtech.entities.Usuario;
import br.com.medtech.ms_medtech.exceptions.LoginNaoEncontradoException;
import br.com.medtech.ms_medtech.repositories.MedicoRepository;
import br.com.medtech.ms_medtech.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
//    private final EnfermeiroRepository enfermeiroRepository;

    private final String MESSAGE_LOGIN_NAO_ENCONTRADO = "este login não existe no sistema, por isso não pode ser feito referencia";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario buscandoUsuario =
                this.pacienteRepository.findByLoginEmail(username)
                        .or(() -> this.medicoRepository.findByLoginEmail(username))
                        .orElseThrow(() -> new LoginNaoEncontradoException(MESSAGE_LOGIN_NAO_ENCONTRADO));

        String adicionandoPrefixoRole = "ROLE_" + buscandoUsuario.getRole().name();
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(adicionandoPrefixoRole));

        User retornandoUsuario = new User(
                buscandoUsuario.getLogin().getEmail(),
                buscandoUsuario.getLogin().getPassword(),
                buscandoUsuario.isEnabled(),
                true,
                true,
                true,
                authorities
        );

        return retornandoUsuario;
    }
}
