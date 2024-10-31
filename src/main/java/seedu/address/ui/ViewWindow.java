package seedu.address.ui;


import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person} in a larger window.
 */
public class ViewWindow extends UiPart<Region> {
    private static final String FXML = "ViewWindow.fxml";

    public final Person person;

    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label assignment;
    @FXML
    private FlowPane tags;
    @FXML
    private Label telegram;
    @FXML
    private Label github;

    /**
     * Display the {@code PersonCode} with the given {@code Person}.
     * @param person
     */
    public ViewWindow(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        telegram.setText(person.getTelegram().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (person.getAssignment() != null) {
            assignment.setText(person.getAssignment().toString());
        } else {
            assignment.setText("No assignment available"); // Optional: for better user feedback
        }

        if (person.getGithub() != null) {
            github.setText(person.getGithub().toString());
        } else {
            github.setText("GitHub username unspecified");
        }
    }
}
