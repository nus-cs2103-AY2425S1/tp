package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

public class DetailView extends UiPart<Region> {

    private static final String FXML = "PersonDetailView.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Person person;

    public DetailView(Person p) {
        super(FXML);
        this.person = p;
    }
}
