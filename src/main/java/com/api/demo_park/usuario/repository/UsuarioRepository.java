package com.api.demo_park.usuario.repository;

import com.api.demo_park.usuario.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByName(@NotBlank(message = "O nome não pode ser vazio") String nameDto);

}
