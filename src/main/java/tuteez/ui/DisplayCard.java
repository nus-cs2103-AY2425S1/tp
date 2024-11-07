package tuteez.ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tuteez.commons.util.UiUtil;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;

/**
 * A UI component that displays all detailed information of a {@code Person}.
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayCard.fxml";

    public final Person person;
    @FXML
    private Label name;
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
    private VBox displayLessons;
    @FXML
    private VBox displayRemarks;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public DisplayCard(Optional<Person> lastViewedPerson) {
        super(FXML);
        this.person = lastViewedPerson.orElse(null);
        if (lastViewedPerson.isPresent()) {
            UiUtil.setNameText(name, person);
            UiUtil.setPhoneText(phone, person);
            UiUtil.setTelegramUsernameText(telegram, person);
            UiUtil.setAddressText(address, person);
            UiUtil.setEmailText(email, person);
            UiUtil.setTags(displayTags, person);
            setRemarks(person);
            setDisplayLessons(person);
        }
    }

    /**
     * Sets the lessons for the provided person by sorting their lessons and displaying them in a numbered format.
     *
     * This method retrieves a list of lessons from the specified {@code Person},
     * sorts the lessons by day and start time using {@code LessonComparator},
     * and then displays each lesson in a numbered format (e.g., "1. MONDAY 0900-1100")
     * within the {@code displayLessons} container.
     *
     * @param person The {@code Person} whose lessons will be displayed.
     */

    private void setDisplayLessons(Person person) {
        assert(person != null);
        // This code might need to change once the method for getting sorted lessons
        // is implemented in the person class.
        List<Lesson> sortedLessons = person.getLessons().stream()
                .sorted(new Lesson.LessonComparator())
                .toList();

        IntStream.range(0, sortedLessons.size())
                .mapToObj(i -> new Label((i + 1) + ". " + sortedLessons.get(i).getDayAndTime()))
                .forEach(label -> displayLessons.getChildren().add(label));
    }

    /**
     * Sets the remarks associated with the {@code Person} in the remarks display pane.
     * Each remark is displayed in a numbered format (e.g., "1. Remark text").
     *
     * @param person The {@code Person} whose remarks are to be displayed.
     */
    private void setRemarks(Person person) {
        assert(person != null);
        IntStream.range(0, person.getRemarkList().getRemarks().size())
                .forEach(i -> {
                    String remark = person.getRemarkList().getRemarks().get(i).toString();
                    String remarkText = (i + 1) + ".\u00A0" + remark;
                    displayRemarks.getChildren().add(new Label(remarkText));
                });
    }
}
