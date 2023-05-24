package model;

public class ConsumoCombustivel {
    private int numeroDeSerie;
    private double capacidade;
    private String portador;
    private double combustivelDisponivel;

    public ConsumoCombustivel() {
        this.capacidade = 0;
        this.portador = "";
        this.combustivelDisponivel = 0;
    }

    public ConsumoCombustivel(double capacidade, String portador) {
        this.capacidade = capacidade;
        this.portador = portador;
        this.combustivelDisponivel = 0;
    }
    
    public ConsumoCombustivel(int numeroDeSerie, double capacidade, String portador) {
        this.numeroDeSerie = numeroDeSerie;
    	this.capacidade = capacidade;
        this.portador = portador;
    }
    
    public ConsumoCombustivel(int numeroDeSerie, double capacidade, String portador, double combustivelDisponivel) {
        this.numeroDeSerie = numeroDeSerie;
    	this.capacidade = capacidade;
        this.portador = portador;
        this.combustivelDisponivel = combustivelDisponivel;
    }

    public void abastecer(double litros) {
        combustivelDisponivel += litros;
    }

    public double rodar(double distancia) {
    	double combustivelNecessario = distancia * 0.5;
    	return combustivelNecessario;
    }

    public double contar() {
        return combustivelDisponivel;
    }

    public int getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(int numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }

    public String getPortador() {
        return portador;
    }

    public void setPortador(String portador) {
        this.portador = portador;
    }

    // Este get é uma redundancia pois o método contar() tem a mesma função
    public double getCombustivelDisponivel() {
        return combustivelDisponivel;
    }

    public void setCombustivelDisponivel(double combustivelDisponivel) {
        this.combustivelDisponivel = combustivelDisponivel;
    }
}
