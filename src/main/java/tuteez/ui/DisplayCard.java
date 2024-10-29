package tuteez.ui;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tuteez.model.person.Person;

/**
 * A UI component that displays all detailed information of a {@code Person}.
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayCard.fxml";

    public final Person person;

    @FXML
    private VBox cardPane;
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
    private FlowPane displayTags;
    @FXML
    private FlowPane displayLessons;
    @FXML
    private VBox displayRemarks;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public DisplayCard(Optional<Person> lastViewedPerson) {
        super(FXML);
        this.person = lastViewedPerson.orElse(null);
        if (lastViewedPerson.isPresent()) {
            name.setText(person.getName().fullName);
            phone.setText(person.getPhone().value);
            telegram.setText("@" + person.getTelegramUsername().telegramUsername);
            address.setText(person.getAddress().value);
            email.setText(person.getEmail().value);
            displayTags.getChildren().clear();
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> displayTags.getChildren().add(new Label(tag.tagName)));

            displayRemarks.getChildren().clear();
            IntStream.range(0, person.getRemarkList().getRemarks().size())
                    .forEach(i -> {
                        String remark = person.getRemarkList().getRemarks().get(i).toString();
                        displayRemarks.getChildren().add(new Label((i + 1) + ". " + remark)); // Add numbering
                    });

            displayLessons.getChildren().clear();
            person.getLessons().stream()
                    .forEach(lesson -> displayLessons.getChildren().add(new Label(lesson.getDayAndTime())));
        }
    }
}
