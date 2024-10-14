package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class ViewPersonCard extends UiPart<Region> {
    private static final String FXML = "ViewPersonCard.fxml";
    private final Logger logger = LogsCenter.getLogger(ViewPersonCard.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code ViewPersonCard} with the given {@code ObservableList}.
     */
    public ViewPersonCard(ObservableList<Person> personList) {
        super(FXML);
    }


}

