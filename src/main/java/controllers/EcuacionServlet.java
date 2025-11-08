package controllers;

import models.Ecuacion;
import services.EcuacionServices;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("ecuacionCuadraticaPrueba/EcuacionServlet")
public class EcuacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EcuacionServices servicio = new EcuacionServices();

    // 1. Maneja la solicitud inicial o el error (GET)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener el mensaje de error si existe
        String error = (String) req.getAttribute("error");

        // Llamar al métodos que genera el formulario
        generarFormulario(resp, error);
    }

    // 2. Maneja el envío del formulario (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener parámetros y validar
            double a = Double.parseDouble(request.getParameter("a"));
            double b = Double.parseDouble(request.getParameter("b"));
            double c = Double.parseDouble(request.getParameter("c"));

            // Crear el Modelo y calcular
            Ecuacion ecuacion = new Ecuacion(a, b, c);
            servicio.calcularRaices(ecuacion);

            // Generar la salida de resultados
            generarResultado(response, ecuacion);

        } catch (NumberFormatException e) {
            // Si hay error, reenviar al doGet para mostrar el formulario con el error
            request.setAttribute("error", "Por favor, introduce valores numéricos válidos.");
            doGet(request, response);
        }
    }

    // Métodos privado para generar la vista del formulario (reemplaza index.jsp)
    private void generarFormulario(HttpServletResponse response, String error) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><meta charset='UTF-8'><title>Calculadora Cuadrática</title></head>");
        out.println("<body>");
        out.println("<h1>Calculadora de Ecuación Cuadrática</h1>");
        out.println("<h2>ax<sup>2</sup> + bx + c = 0</h2>");

        out.println("<form action='EcuacionServlet' method='post'>");
        out.println("<label for='a'>Parámetro a:</label>");
        out.println("<input type='number' step='any' name='a' required><br><br>");
        out.println("<label for='b'>Parámetro b:</label>");
        out.println("<input type='number' step='any' name='b' required><br><br>");
        out.println("<label for='c'>Parámetro c:</label>");
        out.println("<input type='number' step='any' name='c' required><br><br>");
        out.println("<input type='submit' value='Calcular Raíces'>");
        out.println("</form>");

        // Mostrar el mensaje de error
        if (error != null) {
            out.println("<p style='color: red; font-weight: bold;'>" + error + "</p>");
        }

        out.println("</body></html>");
    }

    // Métodos privado para generar la vista de resultados (reemplaza resultado.jsp)
    private void generarResultado(HttpServletResponse response, Ecuacion ecuacion) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Construir la URL para la descarga del PDF
        String urlPDF = "EcuacionServletPDF?a=" + ecuacion.getA() +
                "&b=" + ecuacion.getB() +
                "&c=" + ecuacion.getC();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><meta charset='UTF-8'><title>Resultado del Cálculo</title></head>");
        out.println("<body>");
        out.println("<h1>Resultado de la Ecuación Cuadrática</h1>");
        out.println("<h3>Ecuación: " + ecuacion.getA() + "x² + " + ecuacion.getB() + "x + " + ecuacion.getC() + " = 0</h3>");

        // Imprimir el resultado HTML generado por el Servicio
        out.println(ecuacion.getResultado());

        out.println("<p><a href='" + urlPDF + "' target='_blank'>**Descargar resultado en PDF**</a></p>");
        out.println("<p><a href='EcuacionServlet'>Volver al formulario</a></p>"); // Link apunta al Servlet
        out.println("</body></html>");
    }
}