package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Detail view of a Person.
 */
public class DetailView extends UiPart<Region> {

    private static final String FXML = "PersonDetailView.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Person person;

    /**
     * Initializes a new instance of a DetailView for a person.
     *
     * @param p The Person to display.
     */
    public DetailView(Person p) {
        super(FXML);
        this.person = p;
    }
}
