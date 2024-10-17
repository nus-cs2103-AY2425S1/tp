package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Represents a labelled field in the PersonCard
 */
public class PersonCardField extends HBox {
    private Label field;
    private Label value;

    /**
     * Creates a template {@code PersonCardField} to display.
     */
    public PersonCardField() {
        super(5);
        field = new Label();
        field.setStyle("-fx-font-weight: bold;");
        value = new Label();
        HBox.setHgrow(value, Priority.ALWAYS);
        value.setWrapText(true);
        getChildren().addAll(field, value);
    }

    /**
     * Updates the {@code Label} {@code field} and {@code value} with the corresponding strings.
     * @param fieldText The String to be updated into field.
     * @param valueText The String to be updated in value.
     */
    public void setText(String fieldText, String valueText) {
        this.field.setText(fieldText);
        this.value.setText(valueText);
    }
}
