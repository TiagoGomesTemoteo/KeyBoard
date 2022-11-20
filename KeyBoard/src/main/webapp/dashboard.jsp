<%@page import="com.mycompany.keyboard.model.domain.Teclado"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.keyboard.util.Resultado"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/css_padroes.css" rel="stylesheet">
        <link href="css/css_menu.css" rel="stylesheet">            
    </head>
    <body> 
        <%
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("tela_cadastrar_cliente.jsp");
            } else {
                Cliente cliente = (Cliente) session.getAttribute("usuario");

                if (cliente.getNivel_acesso() != 2) {
                    response.sendRedirect("tela_cadastrar_cliente.jsp");
                }
            }

            Resultado resultado = (Resultado) session.getAttribute("resultado");

        %>

        <%@ include file="links_menu.jsp" %>      
        '
        <div class="chart-container" style="position: relative; height:600px; width:700px">
            <canvas class="line-chart" id="canvas"></canvas>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>



        
        
        <%
            List teclados = new ArrayList();            
            teclados.add("Yamaha");
            teclados.add("Casio");
            teclados.add("Korg");
            teclados.add("XP");
            
            List valoresTotais = new ArrayList();            
            valoresTotais.add(500);
            valoresTotais.add(700);
            valoresTotais.add(300);
            valoresTotais.add(400);
            
            String [][] matriz = new String[teclados.size()][12];
            
            for (int linha = 0; linha < teclados.size(); linha++) {
                for (int coluna = 0; coluna < 12; coluna++){
                    matriz[linha][coluna] = (valoresTotais.get(linha).toString());
                }
            }
            
            List intervalos = new ArrayList();
            intervalos.add("Jan");
            intervalos.add("Fev");
            intervalos.add("Mar");
            intervalos.add("Abr");
            intervalos.add("Mai");
            intervalos.add("Jun");
            intervalos.add("Jul");
            intervalos.add("Ago");
            intervalos.add("Set");
            intervalos.add("Out");
            intervalos.add("Nov");
            intervalos.add("Dez");

            out.println("<input type='hidden' id='teclados' value='"+teclados+"'/>");
            out.println("<input type='hidden' id='valoresTotais' value='"+valoresTotais+"'/>");
            out.println("<input type='hidden' id='intervalos' value='"+intervalos+"'/>");
        %>

        <script>

            var valoresTotais = document.getElementById('valoresTotais').value;
            var teclados = document.getElementById('teclados').value;
            var intervalos = document.getElementById('intervalos').value;

            valoresTotais = valoresTotais.replaceAll("[","");
            valoresTotais = valoresTotais.replaceAll("]","");
            valoresTotais = valoresTotais.split(",");
            
            teclados = teclados.replaceAll("[","");
            teclados = teclados.replaceAll("]","");
            teclados = teclados.split(",");
            
            intervalos = intervalos.replaceAll("[","");
            intervalos = intervalos.replaceAll("]","");
            intervalos = intervalos.split(",");

            var initialData = [];                       
            
            
            for (var i = 0; i < teclados.length; i++) {
                initialData.push(
                    {                       
                        <%         
                            for (int coluna = 0; coluna < 12; coluna++){
                                out.println ("'"+(coluna+1)+"': " +matriz[0][coluna]+",");
                            }
                            
                        %>
                    }
                );      
            }
             const colors = [
                'green',
                'blue',
                'yellow',
                'red'
            ]
            
            var barChartData = {
                labels: intervalos,
                datasets: []
            };
            

            for (var i = 0; i < initialData.length; i++) {
                barChartData.datasets.push(
                    {
                        label: teclados[i],
                        borderColor: colors[i],
                        backgroundColor: 'transparent',
                        data: Object.values(initialData[i]),
                        tension: 0
                    }
                )
            }
            

            var ctx = document.getElementById('canvas').getContext('2d');
            window.myBar = new Chart(ctx, {
                type: 'line',
                data: barChartData
            });

        </script>    

    </body>
</html>
