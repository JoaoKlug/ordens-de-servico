package br.com.copel.os.view.list;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.copel.os.domain.OrdemServico;
import br.com.copel.os.service.OrdemServicoService;

@Named
@ViewScoped
public class OrdemServicoListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrdemServicoService ordemServicoService;

    private List<OrdemServico> ordensServico;

    @PostConstruct
    public void init() {
        ordensServico = ordemServicoService.findAll();
    }

    public void delete(Long id) {
        ordemServicoService.delete(id);
        ordensServico.removeIf(os -> os.getId().equals(id));
    }

    public List<OrdemServico> getOrdensServico() {
        return ordensServico;
    }
}
