package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import module.Visite;
import module.VisiteDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Contrôleur pour la gestion des visites (affichage, ajout, suppression, modification).
 */
public class VisiteController {

    // Composants FXML liés à la vue
    @FXML private TableView<Visite> visiteTable;
    @FXML private TableColumn<Visite, Integer> idColumn;
    @FXML private TableColumn<Visite, String> patientColumn;
    @FXML private TableColumn<Visite, LocalDate> dateColumn;
    @FXML private TableColumn<Visite, String> etatColumn;
    @FXML private TableColumn<Visite, Void> actionColumn;

    @FXML private TextField searchField;
    @FXML private Button addButton;

    // DAO pour les opérations de base de données
    private VisiteDAO visiteDAO = new VisiteDAO();

    // Liste observable pour afficher les visites
    private ObservableList<Visite> visiteList = FXCollections.observableArrayList();

    /**
     * Initialisation automatique lors du chargement du contrôleur.
     * Configure les colonnes, charge les données, et initialise les filtres et actions.
     */
    @FXML
    public void initialize() {
        // Lier les propriétés de la classe Visite aux colonnes du tableau
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("patientNom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etatRendu"));

        // Customisation de la colonne "état" avec un rectangle de couleur
        etatColumn.setCellFactory(col -> new TableCell<>() {
            private final Rectangle rect = new Rectangle(20, 20); // rectangle coloré

            @Override
            protected void updateItem(String etat, boolean empty) {
                super.updateItem(etat, empty);
                if (empty || etat == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    // Choisir la couleur en fonction de l'état
                    switch (etat.toLowerCase()) {
                        case "vu" -> rect.setFill(Color.LIMEGREEN);
                        case "absent" -> rect.setFill(Color.RED);
                        case "annulé", "annule" -> rect.setFill(Color.ORANGE);
                        default -> rect.setFill(Color.GRAY);
                    }
                    setGraphic(rect);
                    setText(etat); // Affiche aussi le texte de l’état
                }
            }
        });

        loadVisites(); // Charger les données depuis la base

        addActionButtons(); // Ajouter les boutons Modifier / Supprimer à chaque ligne

        // Ajouter un filtre pour la recherche dynamique
        FilteredList<Visite> filteredData = new FilteredList<>(visiteList, p -> true);
        visiteTable.setItems(filteredData);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(createPredicate(newVal));
        });

        // Gérer le clic sur le bouton Ajouter
        addButton.setOnAction(e -> showAddDialog());
    }

    /**
     * Charge toutes les visites depuis la base de données et les insère dans la liste observable.
     */
    private void loadVisites() {
        List<Visite> list = visiteDAO.getAllVisites();
        visiteList.setAll(list);
    }

    /**
     * Crée un prédicat de recherche à partir du texte saisi dans la barre de recherche.
     */
    private Predicate<Visite> createPredicate(String searchText) {
        return visite -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return visite.getPatientNom().toLowerCase().contains(searchText.toLowerCase())
                    || visite.getEtatRendu().toLowerCase().contains(searchText.toLowerCase())
                    || visite.getDateVisite().toString().contains(searchText);
        };
    }

    /**
     * Ajoute des boutons "Modifier" et "Supprimer" à chaque ligne du tableau.
     */
    private void addActionButtons() {
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button btnEdit = new Button("Modifier");
            private final Button btnDelete = new Button("Supprimer");
            private final VBox pane = new VBox(5, btnEdit, btnDelete); // espacement vertical

            {
                // Styles CSS pour les boutons
                btnEdit.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

                // Action bouton Modifier
                btnEdit.setOnAction(e -> {
                    Visite v = getTableView().getItems().get(getIndex());
                    showEditDialog(v);
                });

                // Action bouton Supprimer
                btnDelete.setOnAction(e -> {
                    Visite v = getTableView().getItems().get(getIndex());
                    boolean confirm = confirmDelete();
                    if (confirm) {
                        if (visiteDAO.deleteVisite(v.getId())) {
                            visiteList.remove(v);
                            showAlert(Alert.AlertType.INFORMATION, "Suppression", "Visite supprimée");
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression");
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    /**
     * Affiche une boîte de confirmation avant suppression.
     */
    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirmer la suppression ?", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Confirmation");
        return alert.showAndWait().filter(btn -> btn == ButtonType.OK).isPresent();
    }

    /**
     * Affiche une boîte de dialogue pour ajouter une nouvelle visite.
     */
    private void showAddDialog() {
        Dialog<Visite> dialog = new Dialog<>();
        dialog.setTitle("Ajouter Visite");

        // Champs de formulaire
        Label lblPatientId = new Label("ID Patient:");
        TextField tfPatientId = new TextField();

        Label lblDate = new Label("Date :");
        DatePicker dpDate = new DatePicker(LocalDate.now());

        Label lblEtat = new Label("État (Vu, Absent, Annulé):");
        ComboBox<String> cbEtat = new ComboBox<>();
        cbEtat.getItems().addAll("Vu", "Absent", "Annulé");
        cbEtat.setValue("Vu");

        // Organisation du formulaire dans un GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(lblPatientId, 0, 0);
        grid.add(tfPatientId, 1, 0);
        grid.add(lblDate, 0, 1);
        grid.add(dpDate, 1, 1);
        grid.add(lblEtat, 0, 2);
        grid.add(cbEtat, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Action sur clic "OK"
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int patientId = Integer.parseInt(tfPatientId.getText());
                    LocalDate date = dpDate.getValue();
                    if (date == null) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une date.");
                        return null;
                    }
                    String etat = cbEtat.getValue();
                    return new Visite(patientId, date, etat);
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Données invalides");
                }
            }
            return null;
        });

        // Traitement après validation
        dialog.showAndWait().ifPresent(visite -> {
            if (visiteDAO.insertVisite(visite)) {
                loadVisites();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Visite ajoutée");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter la visite");
            }
        });
    }

    /**
     * Affiche une boîte de dialogue pour modifier une visite existante.
     */
    private void showEditDialog(Visite visite) {
        Dialog<Visite> dialog = new Dialog<>();
        dialog.setTitle("Modifier Visite");

        // Champs pré-remplis
        Label lblPatientId = new Label("ID Patient:");
        TextField tfPatientId = new TextField(String.valueOf(visite.getPatientId()));

        Label lblDate = new Label("Date :");
        DatePicker dpDate = new DatePicker(visite.getDateVisite());

        Label lblEtat = new Label("État (Vu, Absent, Annulé):");
        ComboBox<String> cbEtat = new ComboBox<>();
        cbEtat.getItems().addAll("Vu", "Absent", "Annulé");
        cbEtat.setValue(visite.getEtatRendu());

        // Organisation dans un GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(lblPatientId, 0, 0);
        grid.add(tfPatientId, 1, 0);
        grid.add(lblDate, 0, 1);
        grid.add(dpDate, 1, 1);
        grid.add(lblEtat, 0, 2);
        grid.add(cbEtat, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Mise à jour de la visite si validation
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int patientId = Integer.parseInt(tfPatientId.getText());
                    LocalDate date = dpDate.getValue();
                    if (date == null) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une date.");
                        return null;
                    }
                    String etat = cbEtat.getValue();

                    visite.setPatientId(patientId);
                    visite.setDateVisite(date);
                    visite.setEtatRendu(etat);

                    return visite;
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Données invalides");
                }
            }
            return null;
        });

        // Enregistrement après modification
        dialog.showAndWait().ifPresent(updatedVisite -> {
            if (visiteDAO.updateVisite(updatedVisite)) {
                loadVisites();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Visite modifiée");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier la visite");
            }
        });
    }

    /**
     * Affiche une alerte à l'utilisateur.
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
