package seedu.address.ui;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;

/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialCard.fxml";

    @FXML
    private Label tutorial;
    @FXML
    private Label studentsEnrolled;

    /**
     * Creates a {@code TutorialCard} of the given tutorial
     */
    public TutorialCard(String tutorial, List<Participation> participationList) {
        super(FXML);

        this.tutorial.setText(tutorial);

    }
}

