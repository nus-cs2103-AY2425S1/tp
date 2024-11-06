package seedu.address.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddLogPopup extends UiPart<Stage> {

    private static final String FXML = "AddLogPopup.fxml";
    /**
     * Constructs a UiPart using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     *
     * @param fxmlFileName
     */
    public AddLogPopup(String fxmlFileName) {
        super(FXML);
    }

    public static String display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Log Entry");

        TextArea logEntryArea = new TextArea();
        logEntryArea.setPromptText("Enter your log entry here...");

        VBox.setVgrow(logEntryArea, Priority.ALWAYS);

        Button saveButton = new Button("Save");
        saveButton.setDisable(true);

        logEntryArea.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty());
        });

        final String[] logEntry = {null}; // Capture log entry result

        saveButton.setOnAction(e -> {
            logEntry[0] = logEntryArea.getText();
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER_RIGHT);
        buttonLayout.getChildren().addAll(saveButton, cancelButton);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(logEntryArea, buttonLayout);

        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.showAndWait();

        return logEntry[0];
    }
}
