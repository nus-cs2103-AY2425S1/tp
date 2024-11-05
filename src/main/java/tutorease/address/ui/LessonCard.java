package tutorease.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import tutorease.address.model.lesson.Lesson;

/**
 * An UI component that displays information of a {@code Lesson}.
 */
public class LessonCard extends UiPart<Region> {

    private static final String FXML = "LessonListCard.fxml";

    @FXML
    private Label id;
    @FXML
    private HBox cardPane;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label student;
    @FXML
    private Label address;
    @FXML
    private Label fee;
    /**
     * Creates a {@code LessonCard} with the given {@code Lesson} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        startDateTime.setText(lesson.getStartDateTimeString());
        endDateTime.setText(lesson.getEndDateTimeString());
        student.setText(lesson.getStudentName());
        address.setText(lesson.getAddress());
        fee.setText(lesson.getAmountPerHour());
    }
}
