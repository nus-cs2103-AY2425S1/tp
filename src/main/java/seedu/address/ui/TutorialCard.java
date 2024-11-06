package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.participation.Participation;



/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialCard.fxml";
    private List<Participation> participationList;
    private final Logger logger = LogsCenter.getLogger(TutorialCard.class);

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
        this.participationList = participationList;
        updateStudentCount();

        logger.info("Successfully created TutorialCard for " + tutorial);

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
}

