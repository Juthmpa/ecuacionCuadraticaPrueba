package controllers;

import models.Ecuacion;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import services.EcuacionServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EcuacionServletPDF")
public class EcuacionServletPDF extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EcuacionServices servicio = new EcuacionServices();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Declaramos el Document fuera del try para poder acceder a él en el finally
        Document document = null;

        try {
            // 1. Obtener parámetros de la URL
            double a = Double.parseDouble(request.getParameter("a"));
            double b = Double.parseDouble(request.getParameter("b"));
            double c = Double.parseDouble(request.getParameter("c"));

            // 2. Crear Modelo y calcular
            Ecuacion ecuacion = new Ecuacion(a, b, c);
            servicio.calcularRaices(ecuacion);

            // 3. Generación del PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"ecuacion_cuadratica.pdf\"");

            // Inicialización del Document
            document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Contenido del PDF
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLUE);
            document.add(new Paragraph("Cálculo de Ecuación Cuadrática", fontTitulo));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Ecuación: " + a + "x² + " + b + "x + " + c + " = 0"));
            document.add(Chunk.NEWLINE);

            // Limpieza del HTML para el PDF
            String resultadoLimpio = ecuacion.getResultado()
                    .replaceAll("<h3>|</h3>|\\*\\*", "")
                    .replaceAll("<p>|</p>", "\n");

            document.add(new Paragraph("Resultado:\n" + resultadoLimpio));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos para PDF.");
        } catch (DocumentException e) {
            throw new IOException("Error al generar el PDF", e);
        } finally {
            // 4. Cierre obligatorio del Document
            if (document != null) {
                document.close();
            }
        }
    }
}