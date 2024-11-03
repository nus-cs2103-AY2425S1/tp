package seedu.address.ui;


import java.util.Comparator;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person} in a larger window.
 */
public class ViewWindow extends UiPart<Region> {
    private static final String FXML = "ViewWindow.fxml";

    public final Person person;
    int MAX_WEEKS = 12; // Set the total weeks for the display


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
    @FXML
    private FlowPane weekLabel;

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
        setAssignmentText(person.getAssignment());

        if (person.getGithub() != null) {
            github.setText(person.getGithub().toString());
        } else {
            github.setText("GitHub username unspecified");
        }

        if (!person.getWeeksPresent().isEmpty()) {
            weekLabel.getChildren().add(new Label("Weeks attended: "));
            
            for (int week = 1; week <= MAX_WEEKS; week++) {
                Label weekLabelNode = new Label(String.valueOf(week));

                if (person.getWeeksPresent().contains(week)) {
                    weekLabelNode.getStyleClass().add("week-number-marked");
                } else {
                    weekLabelNode.getStyleClass().add("week-number-unmarked");
                }
                weekLabel.getChildren().add(weekLabelNode);
            }
        } else {
            weekLabel.getChildren().add(new Label("No weeks attended"));
        }
    }


    private void setAssignmentText(Map<String, Assignment> assignmentMap) {
        if (assignmentMap.isEmpty()) {
            assignment.setText("No assignment available");
            return;
        }
        StringBuilder sb = new StringBuilder(assignmentMap.size());
        for (Assignment eachAssignment : assignmentMap.values()) {
            sb.append(eachAssignment.toString()).append(", ");
        }
        sb.setLength(sb.length() - 2); //remove the last ,
        assignment.setText(sb.toString());
    }
}
