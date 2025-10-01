package br.com.copel.os.view.list;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.copel.os.domain.Tecnico;
import br.com.copel.os.service.TecnicoService;

@Named
@ViewScoped
public class TecnicoListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TecnicoService tecnicoService;

    private List<Tecnico> tecnicos;

    @PostConstruct
    public void init() {
        tecnicos = tecnicoService.findAll();
    }

    public void delete(Long id) {
        tecnicoService.delete(id);
        tecnicos.removeIf(t -> t.getId().equals(id));
    }

    public List<Tecnico> getTecnicos() {
        return tecnicos;
    }
}
