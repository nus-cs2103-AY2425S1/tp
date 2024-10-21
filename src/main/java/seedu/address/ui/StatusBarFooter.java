package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label personListStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation, ObservableList<Person> personList) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        setPersonListStatus(personList.size());
        personList.addListener((ListChangeListener<? super Person>) unused -> setPersonListStatus(personList.size()));
    }

    private void setPersonListStatus(int totalCount) {
        String personListStatusPrefix = "Total Count: ";
        personListStatus.setText(personListStatusPrefix + totalCount);
    }
}
