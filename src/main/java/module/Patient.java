package module;

import java.time.LocalDate;

public class Patient {

    // Identifiant unique du patient (ex: clé primaire en base)
    private int id;

    // Nom de famille du patient
    private String nom;

    // Prénom du patient
    private String prenom;

    // Date de naissance du patient (format LocalDate pour gérer les dates facilement)
    private LocalDate dateNaissance;

    // Numéro de carte nationale du patient (identification officielle)
    private String numCarteNationale;

    // Dose du médicament prescrite au patient
    private String doseMedicament;

    // Médicaments prescrits au patient (peut contenir plusieurs noms)
    private String medicaments;

    // Médecins qui suivent le patient (peut être une liste ou une chaîne)
    private String medecinsSuivi;

    /**
     * Constructeur pour créer un patient sans id (id généré automatiquement par la base).
     * Utilisé généralement avant l'insertion en base de données.
     */
    public Patient(String nom, String prenom, LocalDate dateNaissance, String numCarteNationale,
                   String doseMedicament, String medicaments, String medecinsSuivi) {
        this(0, nom, prenom, dateNaissance, numCarteNationale, doseMedicament, medicaments, medecinsSuivi);
    }

    /**
     * Constructeur complet pour créer un patient avec un id (ex : récupération depuis la base).
     */
    public Patient(int id, String nom, String prenom, LocalDate dateNaissance, String numCarteNationale,
                   String doseMedicament, String medicaments, String medecinsSuivi) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numCarteNationale = numCarteNationale;
        this.doseMedicament = doseMedicament;
        this.medicaments = medicaments;
        this.medecinsSuivi = medecinsSuivi;
    }

    // --- Getters et setters pour accéder et modifier les propriétés ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNumCarteNationale() {
        return numCarteNationale;
    }

    public void setNumCarteNationale(String numCarteNationale) {
        this.numCarteNationale = numCarteNationale;
    }

    public String getDoseMedicament() {
        return doseMedicament;
    }

    public void setDoseMedicament(String doseMedicament) {
        this.doseMedicament = doseMedicament;
    }

    public String getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(String medicaments) {
        this.medicaments = medicaments;
    }

    public String getMedecinsSuivi() {
        return medecinsSuivi;
    }

    public void setMedecinsSuivi(String medecinsSuivi) {
        this.medecinsSuivi = medecinsSuivi;
    }

    /**
     * Méthode toString pour afficher les informations complètes du patient sous forme de chaîne.
     * Utile pour le débogage ou l'affichage en console.
     */
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", numCarteNationale='" + numCarteNationale + '\'' +
                ", doseMedicament='" + doseMedicament + '\'' +
                ", medicaments='" + medicaments + '\'' +
                ", medecinsSuivi='" + medecinsSuivi + '\'' +
                '}';
    }
}
