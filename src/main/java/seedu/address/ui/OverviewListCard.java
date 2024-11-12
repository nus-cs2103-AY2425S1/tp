package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
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
    private Text phone;
    @FXML
    private Text email;
    @FXML
    private Text address;
    @FXML
    private Text desiredRole;
    @FXML
    private Text skills;
    @FXML
    private Text experience;
    @FXML
    private Text status;
    @FXML
    private Text note;
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
        desiredRole.setText(person.getDesiredRole().value); // Display role with label
        skills.setText(person.getSkills().value); // Display skills with label
        experience.setText(person.getExperience().value);
        status.setText(person.getStatus().value); // Display status with label
        note.setText(person.getNote().value); // Display note with label

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
