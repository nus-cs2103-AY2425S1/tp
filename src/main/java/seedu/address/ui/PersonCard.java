package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    private Label email;
    @FXML
    private Label schedule;
    @FXML
    private Label note;
    @FXML
    private Label reminder;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        // Handle multiple schedules
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

        Set<Schedule> schedules = person.getSchedules();
        if (schedules.isEmpty()) {
            schedule.setText("No scheduled appointments");
        } else {
            String formattedSchedules = schedules.stream()
                    .map(schedule -> {
                        LocalDateTime dateTime = LocalDateTime.parse(schedule.getDateTime(), inputFormatter);
                        String formattedDate = dateTime.format(outputFormatter);
                        String noteText = schedule.getNotes();
                        return String.format("%s [ %s ]\n", formattedDate, noteText);
                    })
                    .collect(Collectors.joining(""));
            schedule.setText(formattedSchedules);
        }

        if (person.getReminder() != null && !person.getReminder().toString().isEmpty()) {
            reminder.setText(String.format("Reminder: %s before",
                    person.getReminder().getReminderTime()));
        } else {
            reminder.setText("");
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
