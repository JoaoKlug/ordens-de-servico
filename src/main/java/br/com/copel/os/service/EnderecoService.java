package br.com.copel.os.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.copel.os.domain.Endereco;

@Stateless
public class EnderecoService {

    @PersistenceContext
    private EntityManager em;

    public List<Endereco> findAll() {
        return em.createQuery("SELECT e FROM Endereco e", Endereco.class).getResultList();
    }
}
