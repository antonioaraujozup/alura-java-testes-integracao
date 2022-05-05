package br.com.alura.leilao.util.builder;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Usuario;

import java.math.BigDecimal;

public class LanceBuilder {

    private BigDecimal valor;
    private Usuario usuario;

    public LanceBuilder comValor(String valor) {
        this.valor = new BigDecimal(valor);
        return this;
    }

    public LanceBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Lance criar() {
        return new Lance(usuario,valor);
    }
}
