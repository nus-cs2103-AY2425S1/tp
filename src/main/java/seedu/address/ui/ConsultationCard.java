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

        // Set Background to Red if Consultation Date & Time have passed
        if (consultationDateTime.isBefore(currentDateTime)) {
            if (displayedIndex % 2 == 0) {
                cardPane.setStyle("-fx-background-color: #B03434;"); // Light Red
            } else {
                cardPane.setStyle("-fx-background-color: #7D1515;"); // Dark Red
            }
        }

        consultation.getStudents().stream()
                .sorted(Comparator.comparing(student -> student.getName().fullName))
                .forEach(student -> students.getChildren().add(new Label(student.getName().fullName)));
    }
}
