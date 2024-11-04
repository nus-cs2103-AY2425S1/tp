package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.participation.Participation;



/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialCard.fxml";

    @FXML
    private Label tutorial;
    @FXML
    private Label studentsEnrolled;
    private List<Participation> participationList;


    /**
     * Creates a {@code TutorialCard} of the given tutorial
     */
    public TutorialCard(String tutorial, List<Participation> participationList) {
        super(FXML);

        this.tutorial.setText(tutorial);
        this.participationList = participationList;
        updateStudentCount();
    }

    /**
     * Updates the old participation list to the updated one
     * @param newParticipationList updated participation list
     */
    public void updateParticipationList(List<Participation> newParticipationList) {
        this.participationList = newParticipationList;
        updateStudentCount();
    }
    private void updateStudentCount() {
        this.studentsEnrolled.setText(String.valueOf(participationList.size()));
    }

    public VBox getRoot() {
        return (VBox) super.getRoot();
    }
}

