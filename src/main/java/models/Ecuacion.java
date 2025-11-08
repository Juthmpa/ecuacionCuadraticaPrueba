package models;

public class Ecuacion {
    private double a;
    private double b;
    private double c;
    private String resultado;
    private boolean esValida = true; // Para manejar a=0 o discriminante negativo

    // Constructor, Getters y Setters...
    public Ecuacion(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // Getters y Setters (Necesarios para la vista)
    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public boolean isEsValida() { return esValida; }
    public void setEsValida(boolean esValida) { this.esValida = esValida; }
}
