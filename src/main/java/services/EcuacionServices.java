package services;

import models.Ecuacion;

public class EcuacionServices {

    public void calcularRaices(Ecuacion ecuacion) {
        double a = ecuacion.getA();
        double b = ecuacion.getB();
        double c = ecuacion.getC();
        double discriminante = b * b - 4 * a * c;

        StringBuilder sb = new StringBuilder();

        if (a == 0) {
            ecuacion.setEsValida(false);
            sb.append("<p>El parámetro 'a' debe ser distinto de cero para una ecuación cuadrática.</p>");
        } else if (discriminante > 0) {
            double raiz1 = (-b + Math.sqrt(discriminante)) / (2 * a);
            double raiz2 = (-b - Math.sqrt(discriminante)) / (2 * a);
            sb.append("<h3>**Dos raíces reales distintas:**</h3>");
            sb.append("<p>x1 = **").append(String.format("%.4f", raiz1)).append("**</p>");
            sb.append("<p>x2 = **").append(String.format("%.4f", raiz2)).append("**</p>");
        } else if (discriminante == 0) {
            double raiz = -b / (2 * a);
            sb.append("<h3>**Una raíz real (doble):**</h3>");
            sb.append("<p>x = **").append(String.format("%.4f", raiz)).append("**</p>");
        } else {
            double parteReal = -b / (2 * a);
            double parteImaginaria = Math.sqrt(-discriminante) / (2 * a);
            sb.append("<h3>**Dos raíces complejas conjugadas:**</h3>");
            sb.append("<p>x1 = **").append(String.format("%.4f", parteReal)).append(" + ").append(String.format("%.4f", parteImaginaria)).append("i**</p>");
            sb.append("<p>x2 = **").append(String.format("%.4f", parteReal)).append(" - ").append(String.format("%.4f", parteImaginaria)).append("i**</p>");
        }

        ecuacion.setResultado(sb.toString());
    }
}
