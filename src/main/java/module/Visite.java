package module;

import java.time.LocalDate;

/**
 * Classe représentant une visite médicale.
 * Elle contient les informations liées à une visite réalisée pour un patient,
 * comme la date, l'état de la visite, ainsi que des détails pour l'affichage.
 */
public class Visite {
    private int id;                 // Identifiant unique de la visite (clé primaire en base)
    private int patientId;          // Identifiant du patient concerné par la visite
    private String patientNom;      // Nom du patient (utile pour affichage dans une TableView ou interface)
    private LocalDate dateVisite;   // Date de la visite (sans heure, format date locale)
    private String etatRendu;       // État de la visite, ex : "Vu", "Absent", "Annulé"

    /**
     * Constructeur complet initialisant toutes les propriétés de la visite.
     * @param id Identifiant de la visite
     * @param patientId Identifiant du patient
     * @param patientNom Nom du patient (pour affichage)
     * @param dateVisite Date de la visite
     * @param etatRendu État de la visite (Vu, Absent, Annulé)
     */
    public Visite(int id, int patientId, String patientNom, LocalDate dateVisite, String etatRendu) {
        this.id = id;
        this.patientId = patientId;
        this.patientNom = patientNom;
        this.dateVisite = dateVisite;
        this.etatRendu = etatRendu;
    }

    /**
     * Constructeur simplifié utilisé lors de la création d'une nouvelle visite
     * où l'id et le nom du patient ne sont pas encore connus ou pas nécessaires.
     * Appelle le constructeur complet avec id à 0 et patientNom à null.
     * @param patientId Identifiant du patient
     * @param dateVisite Date de la visite
     * @param etatRendu État de la visite
     */
    public Visite(int patientId, LocalDate dateVisite, String etatRendu) {
        this(0, patientId, null, dateVisite, etatRendu);
    }

    // --- Getters et Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatientNom() { return patientNom; }
    public void setPatientNom(String patientNom) { this.patientNom = patientNom; }

    public LocalDate getDateVisite() { return dateVisite; }
    public void setDateVisite(LocalDate dateVisite) { this.dateVisite = dateVisite; }

    public String getEtatRendu() { return etatRendu; }
    public void setEtatRendu(String etatRendu) { this.etatRendu = etatRendu; }
}
