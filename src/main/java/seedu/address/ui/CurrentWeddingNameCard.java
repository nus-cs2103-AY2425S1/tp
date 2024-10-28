package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CurrentWeddingNameCard extends UiPart<VBox> {
    private static final String FXML = "CurrentWeddingNameCard.fxml";

    @FXML
    private Label currentWeddingName;

    public CurrentWeddingNameCard() {
        super(FXML);
    }

    public void setCurrentWeddingName(String name) {
        if (name == null) {
            currentWeddingName.setText("Not viewing any wedding");
        }
        currentWeddingName.setText("Viewing: " + name);
    }
}