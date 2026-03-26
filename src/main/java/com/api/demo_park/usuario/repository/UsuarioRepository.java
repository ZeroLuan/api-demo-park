package com.api.demo_park.usuario.repository;

import com.api.demo_park.usuario.dto.UsuarioFiltroDto;
import com.api.demo_park.usuario.dto.UsuarioResponseDto;
import com.api.demo_park.usuario.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByName(@NotBlank(message = "O nome não pode ser vazio") String nameDto);

    @Query("""
        SELECT u FROM Usuario u
        WHERE
            u.dataFim IS NULL AND
            (:#{#usuarioFiltro.nameDto} IS NULL OR :#{#usuarioFiltro.nameDto} = '' OR LOWER(u.name) LIKE LOWER(CONCAT('%', :#{#usuarioFiltro.nameDto}, '%')))
            AND (:#{#usuarioFiltro.emailDto} IS NULL OR :#{#usuarioFiltro.emailDto} = '' OR LOWER(u.email) LIKE LOWER(CONCAT('%', :#{#usuarioFiltro.emailDto}, '%')))
            ORDER BY u.name ASC
    """)
    Page<Usuario> filtrarUsuarioPaginado(UsuarioFiltroDto usuarioFiltro, Pageable page);

}
