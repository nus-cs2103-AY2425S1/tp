package seedu.address.ui;

import java.nio.file.Path;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;
    @FXML
    private Label contactCountLabel;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}
     */
    public StatusBarFooter(Path saveLocation, int contactCount) {
        super(FXML);
        //saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        setContactCount(contactCount);
    }

    /**
     * Writes the number of contacts in the contact list
     * @param count
     */
    private void setContactCount(int count) {
        contactCountLabel.setText("Total contacts: " + count);
    }

}
