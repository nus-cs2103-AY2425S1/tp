package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.wedding.Wedding;

/**
 * An UI component that displays information of a {@code Wedding}.
 */
public class WeddingCard extends UiPart<Region> {

    private static final String FXML = "WeddingCard.fxml";

    public final Wedding wedding;

    @FXML
    private Label weddingName;
    @FXML
    private Label weddingDate;
    @FXML
    private Label weddingAssignees;

    /**
     * A UI component that displays information of a {@code Wedding}.
     */
    public WeddingCard(Wedding wedding, int displayedIndex) {
        super(FXML);
        this.wedding = wedding;
        weddingName.setText(displayedIndex + ". " + wedding.getWeddingName().toString());
        weddingDate.setText("Date: " + wedding.getWeddingDate().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WeddingCard)) {
            return false;
        }

        WeddingCard card = (WeddingCard) other;
        return wedding.equals(card.wedding);
    }
}
