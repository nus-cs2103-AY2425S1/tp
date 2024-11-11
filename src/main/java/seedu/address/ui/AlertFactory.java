package seedu.address.ui;

import javafx.scene.control.Alert;

/**
 * A factory class to create Alert dialogs.
 */
public class AlertFactory {

    /**
     * Creates an Alert of the specified type.
     *
     * @param alertType The type of alert to create.
     * @return A new Alert instance.
     */
    public Alert createAlert(Alert.AlertType alertType) {
        return new Alert(alertType);
    }
}
