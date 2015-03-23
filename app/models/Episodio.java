package models;

import javax.persistence.*;

@Entity(name = "Episodio")
public class Episodio {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nome;

    @Column
    private int numero;
    @Column
    private boolean assistido;

    @ManyToOne(cascade = CascadeType.ALL)
    private Temporada temporada;


    public Episodio(String nome, int numero, Temporada temporada) {
        this.nome = nome;
        this.numero = numero;
        this.temporada = temporada;
        this.assistido = false;
    }

    public Episodio() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeason(Temporada temporada) {
        this.temporada = temporada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public boolean estaAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        temporada.setProximoEpisodio(this);
        this.assistido = assistido;
        temporada.setStatus();
    }

    public Temporada getTemporada() {
        return temporada;
    }

}
