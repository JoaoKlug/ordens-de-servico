package br.com.copel.os.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.copel.os.domain.OrdemServico;

@Stateless
public class OrdemServicoService {

    @PersistenceContext
    private EntityManager em;

    public List<OrdemServico> findAll() {
        return em.createQuery("SELECT os FROM OrdemServico os", OrdemServico.class).getResultList();
    }
}
