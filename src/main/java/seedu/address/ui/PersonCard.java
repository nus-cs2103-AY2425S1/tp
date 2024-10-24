package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label workExp;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label interests;

    @FXML
    private Label university;
    @FXML
    private Label major;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        workExp.setText(person.getWorkExp().value);

        // Add tags to FlowPane
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        university.setText(person.getUniversity().value);
        major.setText(person.getMajor().value);

        // Join interests with commas and set them to the interests label
        String interestText = person.getInterests().stream()
                .sorted(Comparator.comparing(interest -> interest.interestName))
                .map(interest -> interest.interestName)
                .collect(Collectors.joining(", "));
        interests.setText(interestText);
    }
}
