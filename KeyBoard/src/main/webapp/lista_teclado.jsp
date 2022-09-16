<%@page import="com.mycompany.keyboard.model.domain.Teclado"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.keyboard.model.domain.EntidadeDominio"%>
<%@page import="com.mycompany.keyboard.util.Resultado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Resultado resultado = (Resultado) session.getAttribute("resultado");
        %>
        
            TECLADOS:
            <table border="1px">
                <tr>
                    <td>
                        ID:
                    </td>
                    <td>
                        Marca:
                    </td>
                    <td>
                        Modelo:
                    </td>
                    <td>
                        Visualizar:
                    </td>
                    <td>
                        
                    </td>
                </tr>
                <%
                    if (resultado != null) {
                        List<EntidadeDominio> entidades = resultado.getEntidades();
                        StringBuilder sbRegistro = new StringBuilder();
                        StringBuilder sbLink = new StringBuilder();

                        if (entidades != null) {
                            for (int i = 0; i < entidades.size(); i++) {
                                Teclado teclado = (Teclado) entidades.get(i);
                                sbRegistro.setLength(0);
                                sbLink.setLength(0);

                                sbLink.append("<a href=teclado?");
                                sbLink.append("teclado_id=");
                                sbLink.append(teclado.getId());
                                sbLink.append("&");
                                sbLink.append("operacao=");
                                sbLink.append("VISUALIZAR");
                                
                                sbRegistro.append(
                                        "<tr>"
                                        + "<td>"
                                        + teclado.getId()
                                        + "</td>"
                                        + "<td>"
                                        + teclado.getMarca()
                                        + "</td>"
                                        + "<td>"
                                        + teclado.getModelo()
                                        + "</td>"
                                        + "<td>"
                                        + sbLink.toString() + ">" + "Visualizar" + "</a>"
                                        + "</td>"

                                );

                                out.print(sbRegistro.toString());
                            }

                        }
                    }
                %>
            </table>
        </form>    
    </body>
</html>
