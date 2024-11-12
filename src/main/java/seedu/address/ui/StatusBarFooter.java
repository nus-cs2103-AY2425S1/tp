package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label addressBookSaveLocationStatus;

    @FXML
    private Label appointmentBookSaveLocationStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path addressBookLocation, Path appointmentBookLocation) {
        super(FXML);
        addressBookSaveLocationStatus.setText(
            String.format("Address Book: %s",
                Paths.get(".").resolve(addressBookLocation).toString())
        );
        appointmentBookSaveLocationStatus.setText(
            String.format("Appointment Book: %s",
                Paths.get(".").resolve(appointmentBookLocation).toString())
        );
    }

}
