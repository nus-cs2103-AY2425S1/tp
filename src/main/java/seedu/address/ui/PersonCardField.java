package seedu.address.ui;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;

/**
 * Represents a labelled field in the PersonCard
 */
public class PersonCardField extends HBox {
    public static final String ICON_LITERAL_PHONE = "fas-phone-alt";
    public static final String ICON_LITERAL_ADDRESS = "fas-building";
    public static final String ICON_LITERAL_EMAIL = "fas-envelope";
    public static final String ICON_LITERAL_JOB = "fas-briefcase";
    public static final String ICON_LITERAL_INCOME = "fas-dollar-sign";

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
