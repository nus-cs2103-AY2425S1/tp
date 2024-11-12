package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A UI component that displays information of the current {@code Wedding} name.
 */
public class CurrentWeddingNameCard extends UiPart<VBox> {
    private static final String FXML = "CurrentWeddingNameCard.fxml";

    @FXML
    private Label currentWeddingName;

    public CurrentWeddingNameCard() {
        super(FXML);
    }

    public void bindCurrentWeddingName(ObservableValue<String> weddingName) {
        currentWeddingName.textProperty().bind(weddingName);
    }

}
