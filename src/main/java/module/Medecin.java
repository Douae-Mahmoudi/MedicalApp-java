package module;

public class Medecin {
    // Identifiant unique du médecin
    private int id;

    // Nom du médecin
    private String nom;

    // Prénom du médecin
    private String prenom;

    // Spécialité médicale du médecin
    private String specialite;

    // Constructeur sans argument (par défaut)
    public Medecin() {}

    // Constructeur avec tous les attributs
    public Medecin(int id, String nom, String prenom, String specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
    }

    // Getter pour l'id
    public int getId() {
        return id;
    }

    // Setter pour l'id
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour le nom
    public String getNom() {
        return nom;
    }

    // Setter pour le nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour le prénom
    public String getPrenom() {
        return prenom;
    }

    // Setter pour le prénom
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Getter pour la spécialité
    public String getSpecialite() {
        return specialite;
    }

    // Setter pour la spécialité
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
