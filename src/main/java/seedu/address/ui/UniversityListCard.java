package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class UniversityListCard extends UiPart<Region> {

    private static final String FXML = "UniversityListCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Label interests;
    @FXML
    private Label university;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public UniversityListCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        university.setText(person.getUniversity().value);

    }
}
