package models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Serie")
public class Serie {
   @Id
   @GeneratedValue
   @SequenceGenerator(name = "TVSHOW_SEQUENCE", sequenceName = "TVSHOW_SEQUENCE", allocationSize = 1, initialValue = 0)
    private Long id;

    @Column
    private String nome;

    @Column
    private boolean seguindo;

    @JoinColumn(name="SEASON")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Temporada> temporadas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean estaSeguindo() {
        return seguindo;
    }

    public void setSeguindo(boolean following) {
        this.seguindo = following;
    }

    public Serie(String nome) {
        this.nome = nome;
        temporadas = new LinkedList<Temporada>();
        seguindo = false;
    }


    public Serie() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    public void addTemporada(Temporada temporada) {
        temporadas.add(temporada);
    }
}
