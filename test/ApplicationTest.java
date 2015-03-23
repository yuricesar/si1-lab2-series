import java.util.List;

import base.AbstractTest;
import models.Episodio;
import models.Temporada;
import models.Serie;
import org.junit.*;

import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends AbstractTest{

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void deveIniciarComSeries() {
        List<Serie> series = dao.findAllByClassName(Serie.class.getName());
        assertThat(series.size()).isNotEqualTo(0);
    }


    @Test
    public void deveSeguirSeries() {
        Serie serie = new Serie("Serie");
        dao.persist(serie);

        Serie serie1 = dao.findByEntityId(Serie.class, serie.getId());
        assertThat(serie1.estaSeguindo()).isEqualTo(false);
        serie1.setSeguindo(true);
        dao.merge(serie1);

        serie1 = dao.findByEntityId(Serie.class, serie.getId());
        assertThat(serie1.estaSeguindo()).isEqualTo(true);
    }

    @Test
    public void deveAtualizarEpisodioParaAssistido() {
        Serie serie = new Serie("Serie");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episodio", 1, temporada);
        temporada.addEpisodio(episodio);
        serie.addTemporada(temporada);
        dao.persist(serie);

        Episodio episodio1 = dao.findByEntityId(Episodio.class, episodio.getId());
        assertThat(episodio1.estaAssistido()).isEqualTo(false);
        episodio1.setAssistido(true);
        dao.merge(episodio1);

        episodio1 = dao.findByEntityId(Episodio.class, episodio.getId());
        assertThat(episodio1.estaAssistido()).isEqualTo(true);
    }

    @Test
    public void deveAtualizarParaAssistindo() {
        Serie serie = new Serie("Serie");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episodio", 1, temporada);
        Episodio episode0 = new Episodio("Episode0", 1, temporada);
        temporada.addEpisodio(episodio);
        temporada.addEpisodio(episode0);
        serie.addTemporada(temporada);
        dao.persist(serie);

        Episodio episodio1 = dao.findByEntityId(Episodio.class, episodio.getId());
        assertThat(episodio1.getTemporada().getStatus()).isEqualTo(0);
        episodio1.setAssistido(true);
        dao.merge(episodio1);

        episodio1 = dao.findByEntityId(Episodio.class, episodio.getId());
        assertThat(episodio1.getTemporada().getStatus()).isEqualTo(1);
    }

    @Test
    public void deveAtualizarParaAsssitido() {
        Serie serie = new Serie("Serie");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episodio", 1, temporada);
        temporada.addEpisodio(episodio);
        serie.addTemporada(temporada);
        dao.persist(serie);

        Episodio episodio1 = dao.findByEntityId(Episodio.class, episodio.getId());
        assertThat(episodio1.getTemporada().getStatus()).isEqualTo(0);
        episodio1.setAssistido(true);
        dao.merge(episodio1);

        episodio1 = dao.findByEntityId(Episodio.class, episodio.getId());
        assertThat(episodio1.getTemporada().getStatus()).isEqualTo(2);
    }
}
