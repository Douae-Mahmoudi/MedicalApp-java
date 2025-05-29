package module;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    /**
     * Établit une connexion à la base de données MySQL.
     * @return une connexion JDBC à la base 'traitement'.
     * @throws SQLException en cas d'erreur de connexion.
     */
    private Connection getConnection() throws SQLException {
        // URL de connexion à la base locale, port 3306, base 'traitement'
        String url = "jdbc:mysql://localhost:3306/traitement";
        String user = "root";       // utilisateur MySQL
        String password = "";       // mot de passe vide (à sécuriser en prod)
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Récupère la liste complète des patients depuis la base de données.
     * @return une liste d'objets Patient.
     */
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient"; // Requête SQL simple pour tout sélectionner

        // Essayer avec ressources pour fermer automatiquement connexions, statements et resultSet
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours de tous les enregistrements retournés
            while (rs.next()) {
                // Construction d'un objet Patient à partir des colonnes retournées
                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance").toLocalDate(),  // conversion java.sql.Date -> LocalDate
                        rs.getString("num_carte_nationale"),
                        rs.getString("dose_medicament"),
                        rs.getString("medicaments"),
                        rs.getString("medecins_suivi")
                );
                // Ajout du patient à la liste
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Affiche l'erreur en cas d'exception SQL
        }

        // Retourne la liste complète (vide si aucune donnée)
        return patients;
    }

    /**
     * Insère un nouveau patient dans la base de données.
     * L'id est auto-généré et mis à jour dans l'objet Patient passé en paramètre.
     * @param p le patient à insérer.
     */
    public void insertPatient(Patient p) {
        String sql = "INSERT INTO patient (nom, prenom, date_naissance, num_carte_nationale, dose_medicament, medicaments, medecins_suivi) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Remplissage des paramètres de la requête préparée avec les données du patient
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, Date.valueOf(p.getDateNaissance()));  // conversion LocalDate -> java.sql.Date
            ps.setString(4, p.getNumCarteNationale());
            ps.setString(5, p.getDoseMedicament());
            ps.setString(6, p.getMedicaments());
            ps.setString(7, p.getMedecinsSuivi());

            // Exécution de l'insertion
            ps.executeUpdate();

            // Récupération de l'id auto-généré par la base et mise à jour de l'objet patient
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));  // récupère la première clé générée
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un patient dans la base de données en fonction de son identifiant.
     * @param id identifiant du patient à supprimer.
     */
    public void deletePatientById(int id) {
        String sql = "DELETE FROM patient WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);  // Remplir le paramètre id
            ps.executeUpdate();  // Exécuter la suppression

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les informations d'un patient existant dans la base.
     * @param p le patient avec les nouvelles données (doit avoir un id valide).
     */
    public void updatePatient(Patient p) {
        String sql = "UPDATE patient SET nom = ?, prenom = ?, date_naissance = ?, num_carte_nationale = ?, dose_medicament = ?, medicaments = ?, medecins_suivi = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Remplissage des paramètres avec les nouvelles valeurs
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, Date.valueOf(p.getDateNaissance()));
            ps.setString(4, p.getNumCarteNationale());
            ps.setString(5, p.getDoseMedicament());
            ps.setString(6, p.getMedicaments());
            ps.setString(7, p.getMedecinsSuivi());
            ps.setInt(8, p.getId());

            ps.executeUpdate();  // Exécution de la mise à jour

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
