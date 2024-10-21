package seedu.address.ui;

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
        consultation.getStudents().stream()
                .sorted(Comparator.comparing(student -> student.getName().fullName))
                .forEach(student -> students.getChildren().add(new Label(student.getName().fullName)));
    }
}
