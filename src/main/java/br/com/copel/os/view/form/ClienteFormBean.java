package br.com.copel.os.view.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.copel.os.domain.Cliente;
import br.com.copel.os.domain.Endereco;
import br.com.copel.os.service.ClienteService;

@Named
@ViewScoped
public class ClienteFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ClienteService clienteService;

    private Cliente cliente = new Cliente();

    public void init() {
        String idParam = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (idParam != null && !idParam.isEmpty()) {
            cliente = clienteService.findById(Long.valueOf(idParam));
        } else {
            cliente.setEndereco(new Endereco());
        }
    }

    public String save() {
        clienteService.save(cliente);
        return "/list/clienteList.xhtml?faces-redirect=true";
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
