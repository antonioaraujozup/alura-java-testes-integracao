package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LanceBuilder;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LanceDaoTest {

    private LanceDao lanceDao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.lanceDao = new LanceDao(em);
        this.em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        this.em.getTransaction().rollback();
    }

    @Test
    void deveriaRetornarMaiorLanceDoLeilao() {

        // Cria usuários.
        Usuario usuario1 = new UsuarioBuilder()
                .comNome("Antonio")
                .comEmail("antonio@email.com.br")
                .comPassword("password")
                .criar();

        Usuario usuario2 = new UsuarioBuilder()
                .comNome("Maiana")
                .comEmail("antonio@email.com.br")
                .comPassword("password")
                .criar();

        Usuario usuario3 = new UsuarioBuilder()
                .comNome("Rodolfo")
                .comEmail("antonio@email.com.br")
                .comPassword("password")
                .criar();

        this.em.persist(usuario1);
        this.em.persist(usuario2);
        this.em.persist(usuario3);

        // Cria leilão.
        Leilao leilao = new LeilaoBuilder()
                .comNome("Celular")
                .comValorInicial("500")
                .comDataAbertura(LocalDate.now())
                .comUsuario(usuario3)
                .criar();

        leilao = this.em.merge(leilao);

        // Cria lances.
        Lance lance1 = new LanceBuilder()
                .comValor("800")
                .comUsuario(usuario1)
                .criar();

        Lance lance2 = new LanceBuilder()
                .comValor("1000")
                .comUsuario(usuario2)
                .criar();

        // Propõe lances no leilão.
        leilao.propoe(lance1);
        leilao.propoe(lance2);

        leilao = this.em.merge(leilao);

        // Busca o leilao.
        Leilao leilaoComLances = this.em.find(Leilao.class, leilao.getId());

        // Busca o maior lance do leilão.
        Lance maiorLanceDoLeilao = this.lanceDao.buscarMaiorLanceDoLeilao(leilaoComLances);

        // Verifica se o maior lance foi retornado.
        Assertions.assertEquals(new BigDecimal("1000"), maiorLanceDoLeilao.getValor());
    }
}
