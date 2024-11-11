package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.link.Link;

/**
 * A UI component that displays information of a {@code Link}.
 */
public class LinkCard extends UiPart<Region> {

    private static final String FXML = "LinkCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Link link;

    @FXML
    private Label id;
    @FXML
    private HBox cardPane;
    @FXML
    private Label linkText;

    /**
     * Creates a {@code LinkCode} with the given {@code Link} and index to display.
     */
    public LinkCard(Link link, int displayedIndex) {
        super(FXML);
        this.link = link;
        //id.setText(displayedIndex + ". ");
        linkText.setText(link.description());
    }
}
