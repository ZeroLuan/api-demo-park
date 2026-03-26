package com.api.demo_park.usuario.mapper;

import com.api.demo_park.usuario.dto.UsuarioRequestDto;
import com.api.demo_park.usuario.dto.UsuarioResponseDto;
import com.api.demo_park.usuario.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Source: É o objeto que já tem os dados (o parâmetro que entra no método).
    // Target: É o objeto que você quer criar ou preencher (o tipo de retorno do método).

    // Se o nome for igual no dto e no entity não é preciso criar o @Mapping
    // Esse é um exemplo do uso

    /*
     * O uso do @Mapping
     * Você só precisa da anotação @Mapping em três cenários principais:
     * Nomes diferentes: Como você disse, nomeCompleto vs nome.
     * Conversões de formato: Quando você quer formatar uma LocalDate para uma String seguindo um padrão (ex: dd/MM/yyyy).
     * Acessar objetos aninhados: Se o seu Usuario tem um Endereco e você quer mapear o endereco.rua direto para um campo rua no seu DTO.
    */

    @Mapping(source = "nameDto", target = "name")
    @Mapping(source = "passwordDto", target = "password")
    @Mapping(source = "roleDto", target = "role")
    @Mapping(source= "emailDto", target = "email")
    Usuario toEntity(UsuarioRequestDto usuarioDto);

    @Mapping(source = "id", target = "idDto")
    @Mapping(source = "name", target = "nameDto")
    @Mapping(source = "email", target = "emailDto")
    @Mapping(source = "role", target = "roleDto")
    UsuarioResponseDto toDto(Usuario usuario);

    /*
        E se eu quiser atualizar uma Entidade existente?
        Existe um terceiro caso muito usado no mercado: o Mapping de Atualização. Imagine que você recebeu um DTO com novos dados e quer aplicar esses dados em uma Entidade que já existe no banco de dados.

        Nesse caso, usamos a anotação @MappingTarget:

        Exemplo:
        @Mapping(source = "novoTelefone", target = "telefone")
        void updateUsuarioFromDto(UsuarioRequestDto dto, @MappingTarget Usuario entity);
     */

}
