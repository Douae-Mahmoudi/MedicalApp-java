package module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TraitementDAO {

    /**
     * Établit et retourne une connexion à la base de données MySQL.
     * La connexion cible la base 'traitement' sur localhost avec l'utilisateur 'root' sans mot de passe.
     * @return une connexion valide à la base de données
     * @throws SQLException si la connexion échoue
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/traitement", "root", "");
    }

    /**
     * Récupère la liste complète de tous les traitements enregistrés en base.
     * Effectue une jointure avec les tables Patient et Medecin pour récupérer
     * les noms et identifiants liés à chaque traitement.
     * @return une liste d'objets Traitement représentant chaque traitement trouvé
     */
    public List<Traitement> getAllTraitements() {
        List<Traitement> traitements = new ArrayList<>();
        String sql = "SELECT t.id, p.id AS patient_id, p.nom AS patient_nom, " +
                "m.id AS medecin_id, m.nom AS medecin_nom, " +
                "t.medicament, t.dose, t.date_debut, t.date_fin " +
                "FROM traitement t " +
                "JOIN Patient p ON t.patient_id = p.id " +
                "JOIN Medecin m ON t.medecin_id = m.id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Traitement t = new Traitement(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getInt("medecin_id"),
                        rs.getString("patient_nom"),
                        rs.getString("medecin_nom"),
                        rs.getString("medicament"),
                        rs.getString("dose"),
                        rs.getDate("date_debut").toString(),
                        rs.getDate("date_fin") != null ? rs.getDate("date_fin").toString() : ""
                );
                traitements.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return traitements;
    }

    /**
     * Ajoute un nouveau traitement dans la base de données.
     * Prépare une requête SQL INSERT et positionne les valeurs
     * issues de l'objet Traitement passé en paramètre.
     * Gère la possibilité que la date de fin soit absente (null ou chaîne vide).
     * @param t l'objet Traitement contenant les données à insérer
     * @return true si l'insertion a réussi, false sinon
     */
    public boolean addTraitement(Traitement t) {
        String sql = "INSERT INTO traitement (patient_id, medecin_id, medicament, dose, date_debut, date_fin) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getPatientId());
            ps.setInt(2, t.getMedecinId());
            ps.setString(3, t.getMedicament());
            ps.setString(4, t.getDose());
            ps.setDate(5, Date.valueOf(t.getDateDebut()));

            if (t.getDateFin() == null || t.getDateFin().isEmpty()) {
                ps.setNull(6, Types.DATE);
            } else {
                ps.setDate(6, Date.valueOf(t.getDateFin()));
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Met à jour un traitement existant dans la base de données.
     * Utilise l'identifiant du traitement pour cibler la ligne à modifier.
     * Met à jour tous les champs avec les valeurs fournies dans l'objet Traitement.
     * Gère la date de fin pouvant être nulle ou vide.
     * @param t l'objet Traitement contenant les nouvelles données
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateTraitement(Traitement t) {
        String sql = "UPDATE traitement SET patient_id = ?, medecin_id = ?, medicament = ?, dose = ?, date_debut = ?, date_fin = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getPatientId());
            ps.setInt(2, t.getMedecinId());
            ps.setString(3, t.getMedicament());
            ps.setString(4, t.getDose());
            ps.setDate(5, Date.valueOf(t.getDateDebut()));

            if (t.getDateFin() == null || t.getDateFin().isEmpty()) {
                ps.setNull(6, Types.DATE);
            } else {
                ps.setDate(6, Date.valueOf(t.getDateFin()));
            }

            ps.setInt(7, t.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un traitement de la base de données selon son identifiant.
     * Prépare et exécute une requête DELETE ciblant la ligne correspondante.
     * @param id identifiant du traitement à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteTraitement(int id) {
        String sql = "DELETE FROM traitement WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
