package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label contactListStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation, ObservableList<Contact> contactList) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        setContactListStatus(contactList.size());
        contactList.addListener(
                (ListChangeListener<? super Contact>) unused -> setContactListStatus(contactList.size()));
    }

    private void setContactListStatus(int totalCount) {
        String contactListStatusPrefix = "Total Count: ";
        contactListStatus.setText(contactListStatusPrefix + totalCount);
    }
}
