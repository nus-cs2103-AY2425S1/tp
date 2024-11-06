package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.util.function.Consumer;

public class AddLogPopup {

    public static void display(Consumer<String> onSave, Runnable onCancel) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Log Entry");

        TextArea logEntryArea = new TextArea();
        logEntryArea.setPromptText("Enter your log entry here...");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String logEntry = logEntryArea.getText();
            if (!logEntry.isEmpty()) {
                onSave.accept(logEntry);  // Pass logEntry to onSave callback
                window.close();
            } else {
                // Optionally handle empty input with an alert
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            onCancel.run();  // Run onCancel callback
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(logEntryArea, saveButton, cancelButton);

        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.showAndWait();
    }
}
