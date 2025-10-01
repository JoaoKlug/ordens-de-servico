package br.com.copel.os.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.copel.os.domain.Cliente;

@Stateless
public class ClienteService {

    @PersistenceContext
    private EntityManager em;

    public List<Cliente> findAll() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    public Cliente save(Cliente cliente) {
        return em.merge(cliente);
    }

    public void delete(Long id) {
        em.remove(em.getReference(Cliente.class, id));
    }

    public Cliente findById(Long id) {
        return em.find(Cliente.class, id);
    }
}
