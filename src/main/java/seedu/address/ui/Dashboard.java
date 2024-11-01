package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * An UI component that displays key information related to students and tutorials.
 */
public class Dashboard extends UiPart<Region> {

    private static final String FXML = "Dashboard.fxml";

    private final ObservableList<Person> personList;
    private final ObservableList<Participation> participationList;
    private final ObservableList<Tutorial> tutorialList;

    @FXML
    private Label total;
    @FXML
    private Label feesOverdue;
    @FXML
    private FlowPane tutorials;

    /**
     * Creates a {@code Dashboard} with the given {@code Payment} t0 display.
     */
    public Dashboard(ObservableList<Person> personList, ObservableList<Participation> participationList,
                     ObservableList<Tutorial> tutorialList) {
        super(FXML);
        this.personList = personList;
        this.participationList = participationList;
        this.tutorialList = tutorialList;

        setStudentSummary();
        setTutorials();
    }

    private void setStudentSummary() {
        total.setText(personList.size() + "");

        int studentsWithFeesOverdue = personList.filtered(person ->
                Integer.parseInt(person.getPayment().overdueAmount) > 0).size();
        feesOverdue.setText(studentsWithFeesOverdue + "");
    }

    private void setTutorials() {
        tutorialList.sorted(Comparator.comparing(Tutorial::getSubject))
                .forEach(tutorial -> tutorials.getChildren().add(new TutorialCard(tutorial.getSubject(),
                        getParticipationListOfTutorial(tutorial)).getRoot()));
    }

    private List<Participation> getParticipationListOfTutorial(Tutorial tutorial) {
        return participationList.filtered(participation -> participation.getTutorial().equals(tutorial));
    }

}

