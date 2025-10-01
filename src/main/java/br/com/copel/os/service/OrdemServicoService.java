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

    public OrdemServico save(OrdemServico ordemServico) {
        if (ordemServico.getCliente() != null && ordemServico.getCliente().getEndereco() != null) {
            ordemServico.setEndereco(ordemServico.getCliente().getEndereco());
        }
        return em.merge(ordemServico);
    }

    public List<OrdemServico> findAll() {
        return em.createQuery("SELECT os FROM OrdemServico os", OrdemServico.class).getResultList();
    }

    public void delete(Long id) {
        em.remove(em.getReference(OrdemServico.class, id));
    }

    public OrdemServico findById(Long id) {
        return em.find(OrdemServico.class, id);
    }
}
