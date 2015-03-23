package controllers;

import models.Episodio;
import models.Serie;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.db.jpa.Transactional;
import play.mvc.*;

import java.util.LinkedList;
import java.util.List;

public class Application extends Controller {

    static GenericDAO dao = new GenericDAOImpl();

    @Transactional
    public static Result index() {
        List<Serie> series;
        series = dao.findAllByClassName(Serie.class.getName());
        List<Serie> assistindo = new LinkedList<Serie>();
        List<Serie> desassistido = new LinkedList<Serie>();
        for (Serie serie: series) {
            if(!serie.estaSeguindo()) {
            	desassistido.add(serie);
            } else {
            	assistindo.add(serie);
            }
        }
        return ok(views.html.index.render(desassistido, assistindo));
    }

    @Transactional
    public  static Result serie() {
        return redirect(routes.Application.index());
    }

    @Transactional
    public static Result assistir(long id) {
        Serie serie = dao.findByEntityId(Serie.class, id);
        serie.setSeguindo(true);
        dao.merge(serie);
        dao.flush();
        return redirect(routes.Application.index());
    }

    @Transactional
    public static Result assistirEpisodio(long id) {
        Episodio episodio = dao.findByEntityId(Episodio.class, id);
        episodio.setAssistido(true);
        dao.merge(episodio);
        dao.flush();
        return redirect(routes.Application.index());
    }

}
