package module;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/traitement";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance").toLocalDate(),
                        rs.getString("num_carte_nationale"),
                        rs.getString("dose_medicament"),
                        rs.getString("medicaments"),
                        rs.getString("medecins_suivi")
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public void insertPatient(Patient p) {
        String sql = "INSERT INTO patient (nom, prenom, date_naissance, num_carte_nationale, dose_medicament, medicaments, medecins_suivi) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, Date.valueOf(p.getDateNaissance()));
            ps.setString(4, p.getNumCarteNationale());
            ps.setString(5, p.getDoseMedicament());
            ps.setString(6, p.getMedicaments());
            ps.setString(7, p.getMedecinsSuivi());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatientById(int id) {
        String sql = "DELETE FROM patient WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatient(Patient p) {
        String sql = "UPDATE patient SET nom = ?, prenom = ?, date_naissance = ?, num_carte_nationale = ?, dose_medicament = ?, medicaments = ?, medecins_suivi = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setDate(3, Date.valueOf(p.getDateNaissance()));
            ps.setString(4, p.getNumCarteNationale());
            ps.setString(5, p.getDoseMedicament());
            ps.setString(6, p.getMedicaments());
            ps.setString(7, p.getMedecinsSuivi());
            ps.setInt(8, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
