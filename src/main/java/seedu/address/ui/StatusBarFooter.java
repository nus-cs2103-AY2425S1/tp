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
    public StatusBarFooter(Path saveLocation, ObservableList<Contact> contactListForUi,
                           ObservableList<Contact> allContactList) { //ObservableList<Contact> fullContacts
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        setContactListStatus(contactListForUi.size(), allContactList.size());
        contactListForUi.addListener(
                (ListChangeListener<? super Contact>) unused -> setContactListStatus(contactListForUi.size(),
                        allContactList.size()));

        // @@author cth06-Github
        allContactList.addListener(
                (ListChangeListener<? super Contact>) unused -> setContactListStatus(contactListForUi.size(),
                allContactList.size()));
        // @@author
    }

    // @@author cth06-Github*/
    private void setContactListStatus(int contactCountListed, int contactCountAll) {
        contactListStatus.setText(contactCountListed + " out of " + contactCountAll
                + " contacts listed");
    }
    // @@author
}
