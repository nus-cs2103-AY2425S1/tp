package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class ViewPersonCard extends UiPart<Region> {
    private static final String FXML = "ViewPersonCard.fxml";
    private final Logger logger = LogsCenter.getLogger(ViewPersonCard.class);

    private final StringProperty info;

    @FXML
    private Label personInfo;

    /**
     * Creates a {@code ViewPersonCard} with the given {@code Person}.
     */
    public ViewPersonCard(Person person) {
        super(FXML);
        info = new SimpleStringProperty(person.generateContactInformation());
        personInfo.textProperty().bind(info);
    }
}

