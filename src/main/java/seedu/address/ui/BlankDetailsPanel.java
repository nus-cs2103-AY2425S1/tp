package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class BlankDetailsPanel extends UiPart<Region> {

    private static final String FXML = "BlankDetailsPanel.fxml";


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public BlankDetailsPanel() {
        super(FXML);
    }
}
