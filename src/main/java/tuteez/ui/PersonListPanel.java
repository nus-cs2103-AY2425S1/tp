package tuteez.ui;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import tuteez.commons.core.LogsCenter;
import tuteez.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private static final int REFRESH_TIME = 60;
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private Timeline centralRefreshTimeline;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        startCentralRefreshTimeline();
    }

    /**
     * Starts the central timeline that refreshes all visible PersonCards every minute.
     */
    private void startCentralRefreshTimeline() {
        if (centralRefreshTimeline != null) {
            centralRefreshTimeline.stop();
        }
        // Calculate the initial delay to the next full minute
        long initialDelayInMillis = calculateInitialDelayToNextMinute();
        // Create a timeline with an initial delay and a 60-second interval
        centralRefreshTimeline = new Timeline(
                new KeyFrame(Duration.millis(initialDelayInMillis), event -> {
                    refreshAllVisibleCards();
                    // Start a repeating timeline after the initial refresh at the start of the next minute
                    centralRefreshTimeline = new Timeline(
                            new KeyFrame(Duration.seconds(REFRESH_TIME), e -> refreshAllVisibleCards())
                    );
                    centralRefreshTimeline.setCycleCount(Timeline.INDEFINITE);
                    centralRefreshTimeline.play();
                })
        );

        centralRefreshTimeline.play();

        logger.info("Started refresh timeline, initial delay until the next full minute: "
                + initialDelayInMillis + " ms");
    }

    /**
     * Calculates the delay in milliseconds until the start of the next full minute.
     */
    private long calculateInitialDelayToNextMinute() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMinute = now.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
        return ChronoUnit.MILLIS.between(now, nextMinute);
    }

    /**
     * Refreshes all visible PersonCards in the list view.
     */
    private void refreshAllVisibleCards() {
        // Iterate only over the visible cells of personListView
        personListView.lookupAll(".list-cell").stream()
                .filter(node -> node instanceof PersonListViewCell)
                .map(node -> (PersonListViewCell) node)
                .filter(cell -> cell.isVisible() && cell.getPersonCard() != null)
                .forEach(cell -> {
                    PersonCard personCard = cell.getPersonCard();
                    personCard.refreshNextLesson();
                    logger.fine(String.format("Refreshed timer for student at index %s", cell.getIndex()));
                });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {

        private PersonCard personCard;
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                personCard = null;
                setGraphic(null);
                setText(null);
            } else {
                personCard = new PersonCard(person, getIndex() + 1);
                setGraphic(personCard.getRoot());
            }
        }

        public PersonCard getPersonCard() {
            return this.personCard;
        }
    }

}
