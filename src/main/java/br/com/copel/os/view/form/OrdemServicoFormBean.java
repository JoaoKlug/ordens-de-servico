package br.com.copel.os.view.form;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.copel.os.domain.Cliente;
import br.com.copel.os.domain.OrdemServico;
import br.com.copel.os.domain.Tecnico;
import br.com.copel.os.service.ClienteService;
import br.com.copel.os.service.OrdemServicoService;
import br.com.copel.os.service.TecnicoService;

@Named
@ViewScoped
public class OrdemServicoFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrdemServicoService ordemServicoService;
    @Inject
    private ClienteService clienteService;
    @Inject
    private TecnicoService tecnicoService;

    private OrdemServico ordemServico = new OrdemServico();

    private List<Cliente> allClientes;
    private List<Tecnico> allTecnicos;

    @PostConstruct
    public void loadDropdowns() {
        allClientes = clienteService.findAll();
        allTecnicos = tecnicoService.findAll();
    }

    public void init() {
        String idParam = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (idParam != null && !idParam.isEmpty()) {
            ordemServico = ordemServicoService.findById(Long.valueOf(idParam));
        }
    }

    public String save() {
        ordemServicoService.save(ordemServico);
        return "/list/ordemServicoList.xhtml?faces-redirect=true";
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public List<Cliente> getAllClientes() {
        return allClientes;
    }

    public List<Tecnico> getAllTecnicos() {
        return allTecnicos;
    }
}
