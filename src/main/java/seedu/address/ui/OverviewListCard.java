package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class OverviewListCard extends UiPart<Region> {

    private static final String FXML = "OverviewListCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label desiredRole;
    @FXML
    private Label skills;
    @FXML
    private Label experience;
    @FXML
    private Label status;
    @FXML
    private Label note;
    @FXML
    private FlowPane tags;


    /**
     * Creates a {@code OverviewListCard} with the given {@code Person} and index to display.
     */
    public OverviewListCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        address.setText(person.getAddress().value);
        desiredRole.setText(person.getDesiredRole().value); // Display desired role
        skills.setText(person.getSkills().value); // Display skills
        experience.setText(person.getExperience().value);
        status.setText(person.getStatus().value); // Display status
        note.setText(person.getNote().value); // Display note
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
