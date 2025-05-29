package module;

/**
 * Classe représentant un traitement médical.
 * Elle contient les informations liées au traitement d'un patient par un médecin,
 * incluant les dates de début et de fin, le médicament et la dose prescrite.
 */
public class Traitement {
    // Identifiant unique du traitement
    private int id;

    // Identifiant du patient concerné par ce traitement
    private int patientId;

    // Identifiant du médecin qui suit ce traitement
    private int medecinId;

    // Nom du patient (pour affichage ou rapport, dupliqué pour commodité)
    private String patientNom;

    // Nom du médecin (idem, pour affichage ou rapport)
    private String medecinNom;

    // Nom du médicament prescrit dans ce traitement
    private String medicament;

    // Dose prescrite du médicament
    private String dose;

    // Date de début du traitement (format String, pourrait être LocalDate pour plus de robustesse)
    private String dateDebut;

    // Date de fin du traitement (idem, format String)
    private String dateFin;

    /**
     * Constructeur complet pour initialiser un traitement.
     *
     * @param id identifiant du traitement
     * @param patientId identifiant du patient
     * @param medecinId identifiant du médecin
     * @param patientNom nom du patient
     * @param medecinNom nom du médecin
     * @param medicament médicament prescrit
     * @param dose dose prescrite
     * @param dateDebut date de début du traitement
     * @param dateFin date de fin du traitement
     */
    public Traitement(int id, int patientId, int medecinId, String patientNom, String medecinNom, String medicament, String dose, String dateDebut, String dateFin) {
        this.id = id;
        this.patientId = patientId;
        this.medecinId = medecinId;
        this.patientNom = patientNom;
        this.medecinNom = medecinNom;
        this.medicament = medicament;
        this.dose = dose;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // ----- Getters : méthodes permettant d'accéder aux attributs privés -----
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getMedecinId() { return medecinId; }
    public String getPatientNom() { return patientNom; }
    public String getMedecinNom() { return medecinNom; }
    public String getMedicament() { return medicament; }
    public String getDose() { return dose; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }

    // ----- Setters : méthodes permettant de modifier les attributs privés -----
    public void setId(int id) { this.id = id; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setMedecinId(int medecinId) { this.medecinId = medecinId; }
    public void setPatientNom(String patientNom) { this.patientNom = patientNom; }
    public void setMedecinNom(String medecinNom) { this.medecinNom = medecinNom; }
    public void setMedicament(String medicament) { this.medicament = medicament; }
    public void setDose(String dose) { this.dose = dose; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }
}
