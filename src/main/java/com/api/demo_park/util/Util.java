package com.api.demo_park.util;
// final para não permitir herança, e o construtor privado para evitar instanciação da classe, já que é uma classe utilitária com métodos estáticos.
public final class Util {

    private Util() {
        throw new IllegalStateException("Classe Utilitária");
    }

    // o Uso do static é para que possa usar a util sem instanciar a classe pode se usar diretamente assim Util.validarEmail(); sem precisar fazer antes um new
    public static String validarEmail(String email) {
        return email;
    }

    public static String validarSenha(String senha) {
        return senha;
    }


}
