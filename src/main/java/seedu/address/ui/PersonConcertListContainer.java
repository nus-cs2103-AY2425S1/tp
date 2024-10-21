package seedu.address.ui;

import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel to contain a {@code PersonListPanel} and (@code ConcertListPanel}.
 */
public class PersonConcertListContainer extends UiPart<Region> {
    private static final String FXML = "PersonConcertListContainer.fxml";
//    TODO
//    private final Logger logger = LogsCenter.getLogger(PersonConcertListContainer.class);

    @FXML
    private HBox personConcertListContainer;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane concertListPanelPlaceholder;

    /**
     * Creates a {@code PersonConcertListContainer} with the given {@code PersonListPanel} and (@code ConcertListPanel}.
     */
    public PersonConcertListContainer(PersonListPanel personListPanel, ConcertListPanel concertListPanel) {
        super(FXML);

        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        concertListPanelPlaceholder.getChildren().add(concertListPanel.getRoot());
    }
}
