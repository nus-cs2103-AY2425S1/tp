package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.CommandInfo;

/**
 * A UI component that displays information of {@code CommandInfo}.
 */
public class HelpListCard extends UiPart<Region> {

    private static final String FXML = "HelpListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label commandUsageLabel;


    /**
     * Creates a {@code HelpListCard} with the given {@code CommandInfo}.
     */
    public HelpListCard(CommandInfo commandInfo) {
        super(FXML);
        commandUsageLabel.setText(commandInfo.getUsage());
    }
}
