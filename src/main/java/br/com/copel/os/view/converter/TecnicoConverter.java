package br.com.copel.os.view.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.copel.os.domain.Tecnico;
import br.com.copel.os.service.TecnicoService;

@Named
@RequestScoped
public class TecnicoConverter implements Converter<Tecnico> {

    @Inject
    private TecnicoService tecnicoService;

    @Override
    public Tecnico getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return tecnicoService.findById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Tecnico value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
