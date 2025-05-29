package module;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO (Data Access Object) pour gérer les opérations CRUD
 * sur la table 'visite' dans la base de données.
 */
public class VisiteDAO {

    // URL de connexion à la base de données MySQL 'traitement'
    private static final String URL = "jdbc:mysql://localhost:3306/traitement";
    private static final String USER = "root";         // utilisateur de la BDD
    private static final String PASSWORD = "";         // mot de passe de la BDD (vide ici)

    /**
     * Méthode privée pour obtenir une connexion à la base de données.
     * @return Connection active à la base
     * @throws SQLException en cas de problème de connexion
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Récupère la liste de toutes les visites avec le nom du patient associé.
     * Utilise une jointure entre 'visite' et 'patient' pour obtenir le nom.
     * @return List<Visite> toutes les visites de la base
     */
    public List<Visite> getAllVisites() {
        List<Visite> visites = new ArrayList<>();
        String sql = "SELECT v.id, v.patient_id, p.nom AS patientNom, v.date_visite, v.etat_rendu " +
                "FROM visite v " +
                "JOIN patient p ON v.patient_id = p.id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Parcours des résultats et création des objets Visite
            while (rs.next()) {
                Visite v = new Visite(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("patientNom"),
                        rs.getDate("date_visite").toLocalDate(),  // conversion SQL Date -> LocalDate
                        rs.getString("etat_rendu")
                );
                visites.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Affiche la trace d'erreur en cas de problème SQL
        }
        return visites;
    }

    /**
     * Ajoute une nouvelle visite en base.
     * Utilise une requête préparée avec récupération de la clé générée (id auto-incrémenté).
     * @param visite objet Visite à insérer
     * @return true si insertion réussie, false sinon
     */
    public boolean insertVisite(Visite visite) {
        String sql = "INSERT INTO visite(patient_id, date_visite, etat_rendu) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Fixer les paramètres dans la requête préparée
            stmt.setInt(1, visite.getPatientId());
            stmt.setDate(2, Date.valueOf(visite.getDateVisite()));
            stmt.setString(3, visite.getEtatRendu());

            int rows = stmt.executeUpdate();

            // Si insertion OK, récupérer l'id généré et l'affecter à l'objet Visite
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    visite.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Affichage des erreurs SQL
        }
        return false;
    }

    /**
     * Met à jour une visite existante en base selon son id.
     * @param visite objet Visite avec les nouvelles valeurs (id non modifié)
     * @return true si la mise à jour a affecté au moins une ligne, false sinon
     */
    public boolean updateVisite(Visite visite) {
        String sql = "UPDATE visite SET patient_id = ?, date_visite = ?, etat_rendu = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, visite.getPatientId());
            stmt.setDate(2, Date.valueOf(visite.getDateVisite()));
            stmt.setString(3, visite.getEtatRendu());
            stmt.setInt(4, visite.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Supprime une visite de la base selon son identifiant.
     * @param id identifiant de la visite à supprimer
     * @return true si suppression réussie (au moins une ligne supprimée), false sinon
     */
    public boolean deleteVisite(int id) {
        String sql = "DELETE FROM visite WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Note : tu peux ajouter une méthode getAllPatients() pour alimenter un ComboBox,
    // renvoyant une liste d'objets Patient avec id et nom,
    // utile pour lier les visites aux patients dans ton interface.
}
