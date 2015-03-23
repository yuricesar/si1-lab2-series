package models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Temporada")
public class Temporada {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int numero;

    @Column
    private int status;

    @OneToOne(cascade = CascadeType.ALL)
    private Episodio proximoEpisodio;

    @ManyToOne(cascade = CascadeType.ALL)
    private Serie serie;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="EPISODES")
    private List<Episodio> episodios;

    public Temporada(int numero, Serie serie) {
        this.numero = numero;
        this.serie = serie;
        episodios = new LinkedList<Episodio>();
        status = 0;
    }

    public Temporada() {
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void addEpisodio(Episodio episodio) {
        episodios.add(episodio);
    }

    public void setStatus() {
        boolean todosAssistidos = true;
        boolean umAssistido = false;
        for(int i = 0; i < episodios.size(); i++) {
            if(episodios.get(i).estaAssistido()) {
                umAssistido = true;
            }
            if(!episodios.get(i).estaAssistido()) {
                todosAssistidos = false;
            }
        }
        if(todosAssistidos) {
            status = 2;
        } else if(umAssistido){
            status = 1;
        } else {
            status = 0;
        }
    }

    public int getStatus() {
        setStatus();
        return status;
    }

    public void setProximoEpisodio(Episodio ultimoEpisodio) {
        for(int i = 0; i < episodios.size()-1; i++) {
            if(ultimoEpisodio.equals(episodios.get(i))) {
                proximoEpisodio = episodios.get(i+1);
            }
        }
    }

    public Episodio getProximoEpisodio() {
        if (proximoEpisodio == null) {
            return episodios.get(0);
        } else {
            return proximoEpisodio;
        }
    }
}
