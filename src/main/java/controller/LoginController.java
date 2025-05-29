package controller;

// Importation des classes nécessaires
import com.example.medicalapp.Main; // Pour accéder à la méthode showMainApp()
import javafx.fxml.FXML; // Pour lier les éléments FXML au code
import javafx.scene.control.Alert; // Pour afficher des messages d'alerte
import javafx.scene.control.PasswordField; // Champ de mot de passe dans l'interface
import javafx.stage.Stage; // Pour manipuler les fenêtres (stages) JavaFX

public class LoginController {

    // Champ de mot de passe défini dans le fichier LoginView.fxml
    @FXML
    private PasswordField passwordField;

    private final String CODE_CORRECT = "1234";

    // Méthode appelée lorsqu'on clique sur le bouton de connexion
    @FXML
    private void handleLogin() {
        // Récupération du mot de passe saisi par l'utilisateur
        String enteredPassword = passwordField.getText();

        // Vérification si le mot de passe saisi est correct
        if (CODE_CORRECT.equals(enteredPassword)) {

            // Si le mot de passe est correct :
            // Récupère la fenêtre (stage) actuelle via un composant de la scène
            Stage currentStage = (Stage) passwordField.getScene().getWindow();

            // Appelle la méthode pour afficher l'interface principale de l'application
            Main.showMainApp(currentStage);

        } else {
            // Si le mot de passe est incorrect :
            // Création et affichage d'une alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null); // Pas de titre secondaire
            alert.setContentText("Mot de passe incorrect !");
            alert.showAndWait(); // Affichage bloquant de l'alerte
        }
    }
}
