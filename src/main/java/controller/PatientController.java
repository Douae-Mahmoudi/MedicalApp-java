package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import module.Patient;
import javafx.scene.layout.*;
import module.PatientDAO;
import java.time.LocalDate;
import java.util.Optional;

public class PatientController {

    // Références aux composants de l'interface FXML
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, String> nomColumn;
    @FXML private TableColumn<Patient, String> prenomColumn;
    @FXML private TableColumn<Patient, LocalDate> dateNaissColumn;
    @FXML private TableColumn<Patient, String> cnColumn;
    @FXML private TableColumn<Patient, String> doseMedColumn;
    @FXML private TableColumn<Patient, String> medicsColumn;
    @FXML private TableColumn<Patient, String> medecinColumn;
    @FXML private TableColumn<Patient, Void> actionColumn;
    @FXML private TextField searchField;
    @FXML private Button addButton;
    @FXML private ImageView logoImage;

    // Liste des patients et version filtrée pour la recherche
    private ObservableList<Patient> patientList;
    private FilteredList<Patient> filteredData;

    @FXML
    public void initialize() {
        // Lier les colonnes du tableau aux attributs de l'objet Patient
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaissColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        cnColumn.setCellValueFactory(new PropertyValueFactory<>("numCarteNationale"));
        doseMedColumn.setCellValueFactory(new PropertyValueFactory<>("doseMedicament"));
        medicsColumn.setCellValueFactory(new PropertyValueFactory<>("medicaments"));
        medecinColumn.setCellValueFactory(new PropertyValueFactory<>("medecinsSuivi"));

        loadPatients();        // Charger les patients depuis la base
        addButtonToTable();    // Ajouter les boutons Modifier et Supprimer dans chaque ligne
        addButton.setOnAction(e -> openAddPatientDialog());  // Action bouton "Ajouter"
    }

    // Méthode pour charger les patients depuis la BDD et remplir la TableView
    private void loadPatients() {
        PatientDAO dao = new PatientDAO();  // Accès à la BDD
        patientList = FXCollections.observableArrayList(dao.getAllPatients()); // Charger les patients

        // Appliquer un filtre de recherche
        filteredData = new FilteredList<>(patientList, p -> true);
        SortedList<Patient> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(patientTable.comparatorProperty());
        patientTable.setItems(sortedData);
    }

    // Configuration du champ de recherche
    private void setupSearchFilter() {
        filteredData = new FilteredList<>(patientList, p -> true);

        // Écouteur sur le champ de recherche
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(p -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String ft = newVal.toLowerCase();
                return p.getNom().toLowerCase().contains(ft)
                        || p.getPrenom().toLowerCase().contains(ft)
                        || p.getNumCarteNationale().toLowerCase().contains(ft);
            });
        });

        SortedList<Patient> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(patientTable.comparatorProperty());
        patientTable.setItems(sortedData);
    }

    // Ajouter les boutons Modifier et Supprimer dans la colonne d'action
    private void addButtonToTable() {
        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEdit = new Button("Modifier");
            private final Button btnDelete = new Button("Supprimer");
            private final VBox buttonBox = new VBox(5, btnEdit, btnDelete);

            {
                // Style des boutons
                btnEdit.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                // Action bouton Modifier
                btnEdit.setOnAction(ev -> {
                    Patient p = getTableView().getItems().get(getIndex());
                    openEditPatientDialog(p);
                });

                // Action bouton Supprimer
                btnDelete.setOnAction(ev -> {
                    Patient p = getTableView().getItems().get(getIndex());
                    new PatientDAO().deletePatientById(p.getId());
                    patientList.remove(p);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttonBox);
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    // Ouvrir une boîte de dialogue pour ajouter un patient
    private void openAddPatientDialog() {
        Dialog<Patient> dialog = createPatientDialog(null);
        Optional<Patient> res = dialog.showAndWait();
        res.ifPresent(updated -> {
            new PatientDAO().insertPatient(updated);  // Ajouter en base
            patientList.add(updated);                // Ajouter à la liste
            patientTable.refresh();                  // Rafraîchir la table
        });
    }

    // Ouvrir une boîte de dialogue pour modifier un patient existant
    private void openEditPatientDialog(Patient patient) {
        Dialog<Patient> dialog = createPatientDialog(patient);
        Optional<Patient> res = dialog.showAndWait();
        res.ifPresent(updated -> {
            // Mise à jour des champs
            patient.setNom(updated.getNom());
            patient.setPrenom(updated.getPrenom());
            patient.setDateNaissance(updated.getDateNaissance());
            patient.setNumCarteNationale(updated.getNumCarteNationale());
            patient.setDoseMedicament(updated.getDoseMedicament());
            patient.setMedicaments(updated.getMedicaments());
            patient.setMedecinsSuivi(updated.getMedecinsSuivi());

            new PatientDAO().updatePatient(patient);  // Mise à jour en base
            patientTable.refresh();                  // Rafraîchir la table
        });
    }

    // Créer une boîte de dialogue de saisie/modification de patient
    private Dialog<Patient> createPatientDialog(Patient existingPatient) {
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle(existingPatient == null ? "Ajouter un Patient" : "Modifier un Patient");
        ButtonType actionType = new ButtonType(existingPatient == null ? "Ajouter" : "Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

        // Création du formulaire avec GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Champs du formulaire
        TextField nomF    = new TextField();
        TextField prenomF = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField cnF     = new TextField();
        TextField doseF   = new TextField();
        TextField medsF   = new TextField();
        TextField medecF  = new TextField();

        // Pré-remplir les champs si modification
        if (existingPatient != null) {
            nomF.setText(existingPatient.getNom());
            prenomF.setText(existingPatient.getPrenom());
            datePicker.setValue(existingPatient.getDateNaissance());
            cnF.setText(existingPatient.getNumCarteNationale());
            doseF.setText(existingPatient.getDoseMedicament());
            medsF.setText(existingPatient.getMedicaments());
            medecF.setText(existingPatient.getMedecinsSuivi());
        }

        // Disposition dans la grille
        grid.addRow(0, new Label("Nom:"), nomF);
        grid.addRow(1, new Label("Prénom:"), prenomF);
        grid.addRow(2, new Label("Date de naissance:"), datePicker);
        grid.addRow(3, new Label("N° carte nat. :"), cnF);
        grid.addRow(4, new Label("Dose méd. :"), doseF);
        grid.addRow(5, new Label("Médicaments:"), medsF);
        grid.addRow(6, new Label("Médecins :"), medecF);

        dialog.getDialogPane().setContent(grid);

        // Validation du bouton OK : actif seulement si nom non vide
        Node actionBtn = dialog.getDialogPane().lookupButton(actionType);
        actionBtn.setDisable(true);
        nomF.textProperty().addListener((o, ov, nv) -> actionBtn.setDisable(nv.trim().isEmpty()));

        // Cas spécial : si c'est une édition avec nom déjà rempli, activer le bouton
        if (existingPatient != null && !nomF.getText().trim().isEmpty()) {
            actionBtn.setDisable(false);
        }

        // Action quand on clique sur OK
        dialog.setResultConverter(bt -> {
            if (bt == actionType) {
                return new Patient(
                        existingPatient != null ? existingPatient.getId() : 0,
                        nomF.getText(),
                        prenomF.getText(),
                        datePicker.getValue(),
                        cnF.getText(),
                        doseF.getText(),
                        medsF.getText(),
                        medecF.getText()
                );
            }
            return null;
        });

        return dialog;
    }
}
