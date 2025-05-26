package module;

import java.time.LocalDate;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String numCarteNationale;
    private String doseMedicament;
    private String medicaments;
    private String medecinsSuivi;

    // Constructeur sans id (pour insertion avant affectation id auto généré)
    public Patient(String nom, String prenom, LocalDate dateNaissance, String numCarteNationale,
                   String doseMedicament, String medicaments, String medecinsSuivi) {
        this(0, nom, prenom, dateNaissance, numCarteNationale, doseMedicament, medicaments, medecinsSuivi);
    }

    // Constructeur complet
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

    // Getters et setters
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
