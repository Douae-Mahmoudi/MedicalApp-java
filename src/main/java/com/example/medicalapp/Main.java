package com.example.medicalapp; // Déclaration du package de l'application

// Import des classes nécessaires pour JavaFX et autres utilitaires
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

// Classe principale qui hérite de Application (point d'entrée JavaFX)
public class Main extends Application {

    // Layout principal de l'application après connexion
    private static BorderPane rootLayout;

    // Méthode principale appelée au lancement de l'application
    @Override
    public void start(Stage primaryStage) {
        try {
            // Chargement de la vue de connexion à partir du fichier FXML
            Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/medicalapp/LoginView.fxml")));

            // Création de la scène avec la vue chargée
            Scene scene = new Scene(loginRoot);

            // Définition du titre de la fenêtre
            primaryStage.setTitle("Connexion Sécurisée");

            // Application de la scène à la fenêtre principale (stage)
            primaryStage.setScene(scene);

            // Affichage de la fenêtre
            primaryStage.show();
        } catch (IOException e) {
            // Affichage d'une erreur si le fichier FXML est introuvable ou autre problème
            e.printStackTrace();
        }
    }

    // Méthode statique pour afficher l'interface principale après une connexion réussie
    public static void showMainApp(Stage stage) {
        // Création d’un layout principal de type BorderPane
        rootLayout = new BorderPane();

        // Création d’un conteneur horizontal (HBox) pour le menu du haut
        HBox menuBar = new HBox(10); // espacement de 10 pixels entre les boutons
        menuBar.setStyle("-fx-background-color: #0e8f95; -fx-padding: 10;"); // style du menu

        // Création des boutons du menu
        Button btnPatients = new Button("Patients");
        Button btnMedecins = new Button("Médecins");
        Button btnTraitements = new Button("Traitements");
        Button btnVisites = new Button("Visites");

        // Style commun à tous les boutons
        String btnStyle = "-fx-background-color: white; -fx-text-fill: #0e8f95; -fx-font-weight: bold;";
        btnPatients.setStyle(btnStyle);
        btnMedecins.setStyle(btnStyle);
        btnTraitements.setStyle(btnStyle);
        btnVisites.setStyle(btnStyle);

        // Ajout des boutons au menu
        menuBar.getChildren().addAll(btnPatients, btnMedecins, btnTraitements, btnVisites);

        // Positionnement du menu en haut du layout principal
        rootLayout.setTop(menuBar);

        // Chargement initial de la vue "Patients" au centre de l'écran
        loadView("PatientView.fxml");

        // Définition des actions à faire quand on clique sur chaque bouton
        btnPatients.setOnAction(e -> loadView("PatientView.fxml"));
        btnMedecins.setOnAction(e -> loadView("MedecinVIEW.fxml"));
        btnTraitements.setOnAction(e -> loadView("TraitementView.fxml"));
        btnVisites.setOnAction(e -> loadView("VisiteView.fxml"));

        // Création de la scène principale avec des dimensions fixes
        Scene mainScene = new Scene(rootLayout, 1000, 700);

        // Titre de la fenêtre principale de l'application
        stage.setTitle("MedicalTrack - Application Médicale");

        // Définition de la scène principale
        stage.setScene(mainScene);

        // Affichage de la scène
        stage.show();
    }

    // Méthode utilitaire pour charger une vue (FXML) et l'afficher au centre du layout
    private static void loadView(String fxmlFile) {
        try {
            // Chargement du fichier FXML correspondant au nom passé
            Parent view = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/example/medicalapp/" + fxmlFile)));

            // Affichage de la vue dans la zone centrale de l'interface
            rootLayout.setCenter(view);
        } catch (IOException e) {
            // Affichage d'une erreur si le fichier FXML est introuvable
            e.printStackTrace();
        }
    }

    // Point d’entrée du programme Java (lancement de l'application JavaFX)
    public static void main(String[] args) {
        launch(args); // Appelle la méthode start()
    }
}
