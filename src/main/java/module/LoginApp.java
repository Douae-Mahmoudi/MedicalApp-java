package module;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application de connexion.
 * Cette classe étend javafx.application.Application et sert à initialiser
 * et afficher la fenêtre principale de login en chargeant le fichier FXML correspondant.
 */
public class LoginApp extends Application {

    /**
     * Méthode démarrant l'application JavaFX.
     *
     * @param primaryStage Le conteneur principal (fenêtre) de l'application.
     * @throws Exception Si le chargement du fichier FXML échoue.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chargement du fichier FXML définissant la vue de la page de connexion
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medicalapp/login.fxml"));

        // Configuration du titre de la fenêtre principale
        primaryStage.setTitle("Connexion");

        // Définition de la scène principale avec la vue chargée et taille fixe 300x150 pixels
        primaryStage.setScene(new Scene(root, 300, 150));

        // Affichage de la fenêtre principale à l'utilisateur
        primaryStage.show();
    }

    /**
     * Point d'entrée principal de l'application.
     * Appelle la méthode launch() pour initialiser la plateforme JavaFX
     * et démarrer l'application.
     *
     * @param args Arguments en ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
