/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.keyboard.control;

import com.mycompany.keyboard.model.dao.ClienteDAO;
import com.mycompany.keyboard.model.dao.IDAO;
import com.mycompany.keyboard.model.domain.Cliente;
import com.mycompany.keyboard.model.domain.EntidadeDominio;
import com.mycompany.keyboard.model.strategy.IStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tiago
 */
public class Facade implements IFacade {

    private Map<String, IDAO> daos;
    private Map<String, List<IStrategy>> rns;

    public Facade() {
        daos = new HashMap<>();
        daos.put(Cliente.class.getName(), new ClienteDAO());
    }

    private String processStrategys(String operacao, EntidadeDominio entidade) {
        List<IStrategy> regras = rns.get(operacao + entidade.getClass().getName());

        StringBuilder final_message = new StringBuilder();
        if (regras != null) {
            for (IStrategy strategy : regras) {
                String message = strategy.process(entidade);

                if (message != null) {
                    if (final_message.length() > 0) {
                        final_message.append("<br>");
                    }
                    final_message.append(message);
                }
            }
        }

        return (final_message.length() > 0) ? final_message.toString() : null;
    }

    @Override
    public Object salvar(EntidadeDominio entidade) {
        String error_message = processStrategys("SALVAR", entidade);
        if (error_message == null) {
            IDAO dao = daos.get(entidade.getClass().getName());
            return dao.salvar(entidade);
        } else {
            return error_message;
        }
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        String error_message = processStrategys("ALTERAR", entidade);
        if (error_message == null) {
            IDAO dao = daos.get(entidade.getClass().getName());
            dao.alterar(entidade);
            return null;
        } else {
            return error_message;
        }
    }

    @Override
    public String deletar(EntidadeDominio entidade) {
               String error_message = processStrategys("ALTERAR", entidade);
        if (error_message == null) {
            IDAO dao = daos.get(entidade.getClass().getName());
            dao.(entidade.getId());
            return null;
        } else {
            return error_message;
        }
    }

    @Override
    public Object consultar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
