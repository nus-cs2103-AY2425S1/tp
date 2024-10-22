package seedu.address.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Represents a labelled field in the PersonCard
 */
public class PersonCardField extends HBox {
    private FontIcon icon;
    private Label value;

    /**
     * Creates a template {@code PersonCardField} to display.
     */
    public PersonCardField() {
        super(5);
        icon = new FontIcon();
        icon.setIconColor(Paint.valueOf("darkgray"));

        HBox iconWrapper = new HBox();
        iconWrapper.setPrefWidth(20);
        iconWrapper.setAlignment(Pos.CENTER);
        iconWrapper.getChildren().add(icon);

        value = new Label();
        HBox.setHgrow(value, Priority.ALWAYS);
        value.setWrapText(true);
        getChildren().addAll(iconWrapper, value);
    }

    /**
     * Updates the {@code PersonCardField} {@code Icon} and {@code Value} with the corresponding strings.
     *
     * @param iconLiteral The IconLiteral to be set for the icon.
     * @param valueText   The String to be updated in value.
     */
    public void setFields(String iconLiteral, String valueText) {
        this.icon.setIconLiteral(iconLiteral);
        this.value.setText(valueText);
    }
}
