package seedu.eventtory.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.eventtory.model.vendor.Vendor;

/**
 * A UI component that displays information of a {@code Vendor}.
 */
public class VendorCard extends UiPart<Region> {

    private static final String FXML = "VendorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Vendor vendor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code VendorCode} with the given {@code Vendor} and index to
     * display.
     */
    public VendorCard(Vendor vendor, int displayedIndex) {
        super(FXML);
        this.vendor = vendor;
        id.setText(displayedIndex + ". ");
        name.setText(vendor.getName().fullName);
        phone.setText(vendor.getPhone().value);
        description.setText(vendor.getDescription().value);
        vendor.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
