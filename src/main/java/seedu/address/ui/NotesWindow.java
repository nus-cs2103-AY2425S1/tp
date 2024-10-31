package seedu.address.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * A pop-up window that allows users to view and edit notes for a specific person.
 * The window closes and saves the input when the Enter key is pressed.
 */
public class NotesWindow {

    private String updatedNotes;

    /**
     * Displays a pop-up window for editing notes associated with a given person.
     * The window closes and saves the input when the Enter key is pressed.
     *
     * @param person The person whose notes are to be edited.
     * @return The updated notes entered by the user.
     */
    public String showNotesWindow(Person person) {
        Stage stage = new Stage();
        stage.setTitle("Notes for " + person.getName());
        stage.initModality(Modality.APPLICATION_MODAL);

        Label instructionLabel = new Label("Edit notes below and press Enter to save and close:");
        instructionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TextArea notesTextArea = new TextArea(person.getNotes().toString());
        notesTextArea.setWrapText(true);
        notesTextArea.setStyle("-fx-font-size: 13px; -fx-background-color: #f5f5f5;");

        VBox layout = new VBox(10, instructionLabel, notesTextArea);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.setStyle("-fx-background-color: #ffffff;");

        notesTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && event.isShiftDown()) {
                int caretPosition = notesTextArea.getCaretPosition();
                notesTextArea.insertText(caretPosition, "\n");
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                updatedNotes = notesTextArea.getText();
                stage.close();
            }
        });

        Scene scene = new Scene(layout, 400, 250);
        stage.setScene(scene);

        notesTextArea.requestFocus();
        notesTextArea.positionCaret(notesTextArea.getText().length());

        stage.showAndWait();

        return updatedNotes != null ? updatedNotes : person.getNotes().toString();
    }
}
