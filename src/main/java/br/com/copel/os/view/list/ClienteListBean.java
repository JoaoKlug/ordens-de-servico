package br.com.copel.os.view.list;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.copel.os.domain.Cliente;
import br.com.copel.os.service.ClienteService;

@Named
@ViewScoped
public class ClienteListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ClienteService clienteService;

    private List<Cliente> clientes;

    @PostConstruct
    public void init() {
        clientes = clienteService.findAll();
    }

    public void delete(Long id) {
        clienteService.delete(id);
        clientes.removeIf(c -> c.getId().equals(id));
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
