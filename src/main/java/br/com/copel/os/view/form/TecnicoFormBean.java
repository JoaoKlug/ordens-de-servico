package br.com.copel.os.view.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.copel.os.domain.Tecnico;
import br.com.copel.os.service.TecnicoService;

@Named
@ViewScoped
public class TecnicoFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TecnicoService tecnicoService;

    private Tecnico tecnico = new Tecnico();

    public void init() {
        String idParam = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (idParam != null && !idParam.isEmpty()) {
            tecnico = tecnicoService.findById(Long.valueOf(idParam));
        }
    }

    public String save() {
        tecnicoService.save(tecnico);
        return "/list/tecnicoList.xhtml?faces-redirect=true";
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
}
