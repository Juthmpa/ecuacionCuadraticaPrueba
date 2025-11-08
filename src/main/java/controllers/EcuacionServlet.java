package controllers;

import models.Ecuacion;
import services.EcuacionServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EcuacionServlet")
public class EcuacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EcuacionServices servicio = new EcuacionServices();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Obtener parámetros (a, b, c)
            double a = Double.parseDouble(request.getParameter("a"));
            double b = Double.parseDouble(request.getParameter("b"));
            double c = Double.parseDouble(request.getParameter("c"));

            // 2. Crear el Modelo
            Ecuacion ecuacion = new Ecuacion(a, b, c);

            // 3. Llamar al Servicio para el cálculo
            servicio.calcularRaices(ecuacion);

            // 4. Establecer el Modelo en la solicitud y redirigir a la Vista
            request.setAttribute("ecuacion", ecuacion);
            request.getRequestDispatcher("/resultado.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Por favor, introduce valores numéricos válidos.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
