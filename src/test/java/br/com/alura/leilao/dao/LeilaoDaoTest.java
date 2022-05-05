package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LeilaoDaoTest {

    private LeilaoDao leilaoDao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.leilaoDao = new LeilaoDao(em);
        this.em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        this.em.getTransaction().rollback();
    }

    @Test
    void deveriaCadastrarUmLeilao() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Antonio")
                .comEmail("antonio@email.com.br")
                .comPassword("password")
                .criar();

        this.em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Celular")
                .comValorInicial("500")
                .comDataAbertura(LocalDate.now())
                .comUsuario(usuario)
                .criar();

        leilao = this.leilaoDao.salvar(leilao);

        Leilao salvo = this.leilaoDao.buscarPorId(leilao.getId());

        Assertions.assertNotNull(salvo);
    }

    @Test
    void deveriaAtualizarUmLeilao() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Antonio")
                .comEmail("antonio@email.com.br")
                .comPassword("password")
                .criar();

        this.em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Celular")
                .comValorInicial("500")
                .comDataAbertura(LocalDate.now())
                .comUsuario(usuario)
                .criar();

        leilao = this.leilaoDao.salvar(leilao);

        leilao.setNome("Videogame");
        leilao.setValorInicial(new BigDecimal("5000"));

        leilao = this.leilaoDao.salvar(leilao);

        Leilao atualizado = this.leilaoDao.buscarPorId(leilao.getId());

        Assertions.assertEquals("Videogame", atualizado.getNome());
        Assertions.assertEquals(new BigDecimal("5000"), atualizado.getValorInicial());
    }
}
