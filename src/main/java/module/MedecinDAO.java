package module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinDAO {

    /**
     * Établit une connexion à la base de données.
     * @return un objet Connection connecté à la base "traitement"
     * @throws SQLException en cas d'erreur de connexion
     */
    private Connection getConnection() throws SQLException {
        // À remplacer par ta vraie configuration de connexion JDBC
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/traitement", "root", "");
    }

    /**
     * Récupère la liste complète des médecins depuis la base de données.
     * @return une liste d'objets Medecin
     */
    public List<Medecin> getAllMedecins() {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM medecin";

        // Ouverture automatique des ressources avec try-with-resources
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours des résultats de la requête
            while (rs.next()) {
                // Création d'un objet Medecin avec les données récupérées
                Medecin m = new Medecin(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("specialite")
                );
                medecins.add(m);  // Ajout à la liste
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Affichage de l'erreur en cas de problème SQL
        }
        return medecins;
    }

    /**
     * Insère un nouveau médecin dans la base de données.
     * @param m l'objet Medecin à insérer (id non défini avant insertion)
     */
    public void insertMedecin(Medecin m) {
        String sql = "INSERT INTO medecin(nom, prenom, specialite) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplissage des paramètres préparés avec les données du médecin
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());

            ps.executeUpdate(); // Exécution de l'insertion

            // Récupération de la clé générée (id auto-incrémenté)
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    m.setId(generatedKeys.getInt(1)); // Mise à jour de l'id de l'objet
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion d'erreur
        }
    }

    /**
     * Met à jour les informations d'un médecin existant en base.
     * @param m le médecin avec les nouvelles données et un id existant
     */
    public void updateMedecin(Medecin m) {
        String sql = "UPDATE medecin SET nom=?, prenom=?, specialite=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Affectation des paramètres avec les nouvelles valeurs
            ps.setString(1, m.getNom());
            ps.setString(2, m.getPrenom());
            ps.setString(3, m.getSpecialite());
            ps.setInt(4, m.getId());

            ps.executeUpdate(); // Exécution de la mise à jour
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion d'erreur
        }
    }

    /**
     * Supprime un médecin de la base en fonction de son id.
     * @param id identifiant du médecin à supprimer
     */
    public void deleteMedecinById(int id) {
        String sql = "DELETE FROM medecin WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id); // Affectation de l'id
            ps.executeUpdate(); // Exécution de la suppression
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion d'erreur
        }
    }
}
