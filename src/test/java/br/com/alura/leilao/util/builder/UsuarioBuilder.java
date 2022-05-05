package br.com.alura.leilao.util.builder;

import br.com.alura.leilao.model.Usuario;

public class UsuarioBuilder {

    private String nome;
    private String email;
    private String password;

    public UsuarioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder comPassword(String password) {
        this.password = password;
        return this;
    }

    public Usuario criar() {
        return new Usuario(nome,email,password);
    }
}
