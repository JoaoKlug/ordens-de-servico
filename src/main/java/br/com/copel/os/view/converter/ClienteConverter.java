package br.com.copel.os.view.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.copel.os.domain.Cliente;
import br.com.copel.os.service.ClienteService;

@Named
@RequestScoped
public class ClienteConverter implements Converter<Cliente> {

    @Inject
    private ClienteService clienteService;

    @Override
    public Cliente getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return clienteService.findById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Cliente value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
