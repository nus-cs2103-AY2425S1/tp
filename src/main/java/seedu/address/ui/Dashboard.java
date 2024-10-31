package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * An UI component that displays key information related to students and tutorials.
 */
public class Dashboard extends UiPart<Region> {

    private static final String FXML = "Dashboard.fxml";

    /**
     * Creates a {@code Dashboard} with the given {@code Payment} t0 display.
     */
    public Dashboard(ObservableList<Person> personList, ObservableList<Participation> participationList,
                     ObservableList<Tutorial> tutorialList) {
        super(FXML);
    }
}

