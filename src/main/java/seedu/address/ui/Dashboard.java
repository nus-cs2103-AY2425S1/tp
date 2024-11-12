package seedu.address.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * An UI component that displays key information related to students and tutorials.
 */
public class Dashboard extends UiPart<Region> {

    private static final String FXML = "Dashboard.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final ObservableList<Person> personList;
    private final ObservableList<Participation> participationList;
    private final ObservableList<Tutorial> tutorialList;
    private final Map<Tutorial, TutorialCard> tutorialCards = new HashMap<>();

    @FXML
    private HBox dashboard;
    @FXML
    private Label total;
    @FXML
    private Label feesOverdue;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox tutorials;


    /**
     * Creates a {@code Dashboard} with the given {@code Payment} to display.
     */
    public Dashboard(ObservableList<Person> personList, ObservableList<Participation> participationList,
                     ObservableList<Tutorial> tutorialList) {
        super(FXML);
        this.personList = personList;
        this.participationList = participationList;
        this.tutorialList = tutorialList;

        initializeTutorialCards();
        updateStudentSummary();
        addParticipationListListener();
        addTutorialListListener();

        logger.info("Successfully created Dashboard");
    }


    private void initializeTutorialCards() {
        if (tutorialList.isEmpty()) {
            setEmptyTutorialsPlaceholder();
        }
        for (Tutorial tutorial : tutorialList) {
            createAndAddTutorialCard(tutorial);
        }
    }

    private List<Participation> getParticipationListForTutorial(Tutorial tutorial) {
        return participationList.stream()
                .filter(p -> p.getTutorial().equals(tutorial))
                .collect(Collectors.toList());
    }

    private void createAndAddTutorialCard(Tutorial tutorial) {
        if (tutorialList.size() == 1) {
            tutorials.getChildren().clear();
        }

        List<Participation> initialParticipationList = getParticipationListForTutorial(tutorial);
        TutorialCard card = new TutorialCard(tutorial.getSubject(), initialParticipationList);
        tutorials.getChildren().add(card.getRoot());
        tutorialCards.put(tutorial, card);
    }

    private void addParticipationListListener() {
        // for tutorial cards
        participationList.addListener((ListChangeListener<Participation>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    updateTutorialCards();
                }
            }
        });

        // for total and fees overdue
        personList.addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    updateStudentSummary();
                }
            }
        });
    }

    private void addTutorialListListener() {
        tutorialList.addListener((ListChangeListener<Tutorial>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Tutorial addedTutorial : change.getAddedSubList()) {
                        createAndAddTutorialCard(addedTutorial);
                    }
                }

                if (change.wasRemoved()) {
                    for (Tutorial removedTutorial : change.getRemoved()) {
                        TutorialCard card = tutorialCards.remove(removedTutorial);
                        if (card != null) {
                            tutorials.getChildren().remove(card.getRoot());
                        }
                    }
                    if (tutorialList.isEmpty()) {
                        setEmptyTutorialsPlaceholder();
                    }
                }
            }
        });
    }


    private void updateTutorialCards() {
        for (Tutorial tutorial : tutorialList) {
            List<Participation> updatedParticipationList = getParticipationListForTutorial(tutorial);
            TutorialCard card = tutorialCards.get(tutorial);
            if (card != null) {
                card.updateParticipationList(updatedParticipationList);
            }
        }
    }

    private void updateStudentSummary() {
        // Update total number of students
        total.setText(String.valueOf(personList.size()));

        // Calculate and update number of students with overdue fees
        int studentsWithFeesOverdue = personList.filtered(person ->
                Integer.parseInt(person.getPayment().overdueAmount) > 0).size();
        feesOverdue.setText(String.valueOf(studentsWithFeesOverdue));
    }

    /**
     * Sets the tutorials container to show a message if there are no tutorials offered.
     */
    private void setEmptyTutorialsPlaceholder() {
        Label label = new Label("No classes offered yet");
        label.setStyle("-fx-background-color: #F3F8FB; -fx-background-radius: 5; -fx-padding: 10 15;"
                + "-fx-text-fill: #262626");

        HBox emptyState = new HBox(label);
        emptyState.setAlignment(Pos.CENTER);

        tutorials.getChildren().clear();
        tutorials.getChildren().add(emptyState);
    }

}

