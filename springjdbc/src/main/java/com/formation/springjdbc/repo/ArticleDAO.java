package com.formation.springjdbc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.formation.springjdbc.metier.Article;

// une spécialisation de @component
// gerera les erreur SQL pour les reformatter
@Repository
public class ArticleDAO {
    
    // injecte mois le jdbc template
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Article> listeArticles() {
        // le jdbcTemplate vas içi executer la requette transmise
        // et pour chaque ligne de résultat, exécuter le lambda fournie
        // le lambda envoyant un objet pour chaque ligne fournie
        // le jdbcTemplate les ajoute dans une liste et la renvoie à la fin
        // la connection, le nettoyage, etc. sont géré automatiquement
        return jdbcTemplate.query("select id,nom,prix from article", (row, nb) -> {
            return new Article(row.getInt("id"), 
                                row.getString("nom"),
                                row.getDouble("prix"));
        });
    }

    public int creerArticle(String nom, double prix) {
        // attention, update içi ne signifie pas une requete de type UPDATE
        // mais une requette modifiant les données (INSERT,UPDATE, DELETE)
        return jdbcTemplate.update("INSERT INTO article(nom, prix) VALUES(?, ?)", nom, prix);
    }

    public int modifierArticle(int id, Optional<String> nom, Optional<Double> prix) {
        if (nom.isPresent() && prix.isPresent()) 
            return jdbcTemplate.update("update article set nom=?, prix=? WHERE id=?", nom.get(), prix.get(), id);
        else if (nom.isPresent()) 
            return jdbcTemplate.update("update article set nom=? WHERE id=?", nom.get(), id);
        else if (prix.isPresent())
            return jdbcTemplate.update("update article set prix=? WHERE id=?", prix.get(), id);
        else return 0;
    }

    public int deleteArticle(int id) {
        return jdbcTemplate.update("delete from article where id=?", id);
    }
}
