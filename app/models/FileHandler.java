package models;

import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.Play;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileHandler {

    public static void read() {
        String csvFile = Play.application().getFile("/app/assets/seriesFinalFile.csv").getAbsolutePath();
        BufferedReader reader = null;
        String line = "";
        String splitBy = ",";
        try {
            reader = new BufferedReader(new FileReader(csvFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            populateDatabase(reader, splitBy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void populateDatabase(BufferedReader reader, String splitBy) throws IOException {
        GenericDAO dao = new GenericDAOImpl();
        String line = reader.readLine();
        String[] args = line.split(splitBy);
        String nomeSerie = args[0];
        int numeroTemporada = Integer.parseInt(args[1]);
        int numeroEpisodio = Integer.parseInt(args[2]);
        String nomeEpisodio = args[3];
        Serie serie = new Serie(nomeSerie);
        Temporada temporada = new Temporada(numeroTemporada, serie);
        Episodio episodio = new Episodio(nomeEpisodio, numeroEpisodio, temporada);
        temporada.addEpisodio(episodio);
        serie.addTemporada(temporada);

        while ((line = reader.readLine()) != null) {
            args = line.split(splitBy);
            nomeSerie = args[0];
            numeroTemporada = Integer.parseInt(args[1]);
            numeroEpisodio = Integer.parseInt(args[2]);
            if (args.length > 3) {
                nomeEpisodio = args[3];
            } else {
                nomeEpisodio = "";
            }

            if(nomeSerie.equals(serie.getNome())) {
                if(numeroTemporada == temporada.getNumero()) {
                    episodio = new Episodio(nomeEpisodio, numeroEpisodio, temporada);
                    temporada.addEpisodio(episodio);
                } else {
                    temporada = new Temporada(numeroTemporada, serie);
                    episodio = new Episodio(nomeEpisodio,numeroEpisodio,temporada);
                    temporada.addEpisodio(episodio);
                    serie.addTemporada(temporada);
                }
            } else {
                dao.persist(serie);
                dao.flush();
                serie = new Serie(nomeSerie);
                temporada = new Temporada(numeroTemporada, serie);
                episodio = new Episodio(nomeEpisodio,numeroEpisodio,temporada);
                temporada.addEpisodio(episodio);
                serie.addTemporada(temporada);
            }
        }
    }

}
