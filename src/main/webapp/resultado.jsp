<%@ page import="ecuacionCuadraticaPrueba.models.Ecuacion" %>
<%
    // Obtener el Modelo del request
    Ecuacion ecuacion = (Ecuacion) request.getAttribute("ecuacion");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resultado del Cálculo</title>
</head>
<body>
<h1>Resultado de la Ecuación Cuadrática</h1>
<h3>Ecuación: <%= ecuacion.getA() %>x² + <%= ecuacion.getB() %>x + <%= ecuacion.getC() %> = 0</h3>

<%
    // Mostrar el resultado (viene como HTML desde el Servicio)
    out.println(ecuacion.getResultado());
%>

<%
    // Construir la URL para el link de descarga del PDF (GET request)
    String urlPDF = "EcuacionServletPDF?a=" + ecuacion.getA() +
            "&b=" + ecuacion.getB() +
            "&c=" + ecuacion.getC();
%>

<p>
    <a href="<%= urlPDF %>" target="_blank">**Descargar resultado en PDF**</a>
</p>
<p><a href="index.jsp">Volver al formulario</a></p>
</body>
</html>