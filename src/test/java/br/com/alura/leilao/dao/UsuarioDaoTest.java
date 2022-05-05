package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDaoTest {

    private UsuarioDao usuarioDao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.usuarioDao = new UsuarioDao(em);
        this.em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        this.em.getTransaction().rollback();
    }

    @Test
    void deveriaEncontrarUsuarioCadastrado() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Antonio")
                .comEmail("antonio@email.com.br")
                .comPassword("password")
                .criar();

        this.em.persist(usuario);

        Usuario encontrado = this.usuarioDao.buscarPorUsername(usuario.getNome());

        Assertions.assertNotNull(encontrado);
    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastrado() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Antonio")
                .comEmail("antonio@email.com.br")
                .comPassword("password")
                .criar();

        this.em.persist(usuario);

        Assertions.assertThrows(NoResultException.class,
                () -> this.usuarioDao.buscarPorUsername("Maiana"));
    }

    @Test
    void deveriaRemoverUmUsuarioCadastrado() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Antonio")
                .comEmail("antonio@email.com.br")
                .comPassword("password")
                .criar();

        this.em.persist(usuario);

        this.usuarioDao.deletar(usuario);

        Assertions.assertThrows(NoResultException.class,
                () -> this.usuarioDao.buscarPorUsername(usuario.getNome()));
    }
}
