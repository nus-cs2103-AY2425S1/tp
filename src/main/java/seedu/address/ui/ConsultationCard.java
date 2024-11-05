package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.consultation.Consultation;

/**
 * A UI component that displays information of a {@code Consultation}.
 */
public class ConsultationCard extends UiPart<Region> {
    private static final String FXML = "ConsultationListCard.fxml";

    public final Consultation consultation;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private FlowPane students;

    /**
     * Creates a {@code StudentCode} with the given {@code Student} and index to display.
     */
    public ConsultationCard(Consultation consultation, int displayedIndex) {
        super(FXML);
        this.consultation = consultation;
        id.setText(displayedIndex + ". ");
        date.setText(consultation.getDate().toString() + ", ");
        time.setText(consultation.getTime().toString());

        // Combine date and time for comparison with current date and time
        LocalDateTime consultationDateTime = LocalDateTime.of(
                consultation.getDate().getLocalDateValue(),
                consultation.getTime().getLocalTimeValue());
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Apply the appropriate CSS class based on the consultation time
        if (consultationDateTime.isBefore(currentDateTime)) {
            if (displayedIndex % 2 == 0) {
                cardPane.getStyleClass().add("consultation-card-past-even");
            } else {
                cardPane.getStyleClass().add("consultation-card-past-odd");
            }
            id.getStyleClass().add("consultation-card-strikethrough");
            date.getStyleClass().add("consultation-card-strikethrough");
            time.getStyleClass().add("consultation-card-strikethrough");
        }

        consultation.getStudents().stream()
                .sorted(Comparator.comparing(student -> student.getName().fullName))
                .forEach(student -> students.getChildren().add(new Label(student.getName().fullName)));
    }
}
