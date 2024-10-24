package tuteez.ui;

import java.util.Comparator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tuteez.model.person.Person;

/**
 * An UI component that displays all detailed information of a {@code Person}.
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayCard.fxml";

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
    private Label telegram;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane lessons;
    @FXML
    private VBox remarks;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public DisplayCard(Optional<Person> lastViewedPerson) {
        super(FXML);
        this.person = lastViewedPerson.orElse(null);
        if (lastViewedPerson.isPresent()) {
            name.setText(person.getName().fullName);
            phone.setText(person.getPhone().value);
            telegram.setText(person.getTelegramUsername().telegramUsername);
            address.setText(person.getAddress().value);
            email.setText(person.getEmail().value);

            tags.getChildren().clear();
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

            remarks.getChildren().clear();
            person.getRemarkList().getRemarks().stream()
                    .sorted(Comparator.comparing(remark -> remark.toString()))
                    .forEach(remark -> remarks.getChildren().add(new Label(remark.toString())));

            lessons.getChildren().clear();
            person.getLessons().stream()
                    .forEach(lesson -> lessons.getChildren().add(new Label(lesson.getDayAndTime())));
        }
    }
}
