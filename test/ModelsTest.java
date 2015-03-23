import models.Episodio;
import models.Temporada;
import models.Serie;
import org.junit.*;

import static org.fest.assertions.Assertions.assertThat;

public class ModelsTest {
    Serie serie;
    Temporada temporada;
    Episodio episodio;
    Episodio episodio1;
    Temporada temporada2;

    @Before
    public void setUp() {
        serie = new Serie("Serie");
        temporada = new Temporada(1, serie);
        episodio = new Episodio("Episodio", 1, temporada);
        episodio1 = new Episodio("Episode1", 1, temporada);
        temporada2 = new Temporada(2, serie);
        temporada.addEpisodio(episodio);
        temporada.addEpisodio(episodio1);
        serie.addTemporada(temporada);
        serie.addTemporada(temporada2);
    }

    @Test
    public void deveAssistirEpisodio() {
        assertThat(temporada.getStatus()).isEqualTo(0);
        episodio.setAssistido(true);
        assertThat(episodio.estaAssistido()).isEqualTo(true);
        assertThat(temporada.getStatus()).isEqualTo(1);;
    }

    @Test
    public void deveAssistirTemporataCompleta(){
        assertThat(temporada.getStatus()).isEqualTo(0);
        episodio.setAssistido(true);
        episodio1.setAssistido(true);
        assertThat(episodio.estaAssistido()).isEqualTo(true);
        assertThat(temporada.getStatus()).isEqualTo(2);
    }

    @Test
    public void deveAcharEpisodioProximaTemporada() {
        assertThat(temporada.getProximoEpisodio()).isEqualTo(episodio);
        temporada.setProximoEpisodio(episodio);
        assertThat(temporada.getProximoEpisodio()).isEqualTo(episodio1);
    }

}
