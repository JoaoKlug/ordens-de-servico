package br.com.copel.os.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.copel.os.domain.Tecnico;

@Stateless
public class TecnicoService {

    @PersistenceContext
    private EntityManager em;

    public List<Tecnico> findAll() {
        return em.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();
    }

    public Tecnico save(Tecnico tecnico) {
        return em.merge(tecnico);
    }

    public void delete(Long id) {
        em.remove(em.getReference(Tecnico.class, id));
    }

    public Tecnico findById(Long id) {
        return em.find(Tecnico.class, id);
    }

}
