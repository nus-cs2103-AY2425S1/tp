package seedu.ddd.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class VendorCard extends UiPart<Region> {

    private static final String FXML = "VendorCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Vendor vendor;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private Label service;

    @FXML
    private FlowPane tags;

    @FXML
    private Label id;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public VendorCard(Vendor vendor, int displayedIndex) {
        super(FXML);
        this.vendor = vendor;

        name.setText(String.format("%s. %s", displayedIndex, vendor.getName().fullName));
        id.setText(String.format("Vendor: #%d", vendor.getId().id));

        phone.setText(String.format("📞  Phone Number: %s", vendor.getPhone()));
        email.setText(String.format("📫  Email: %s", vendor.getEmail()));
        address.setText(String.format("🏠  Address: %s", vendor.getAddress()));
        service.setText(String.format("🔨  Service: %s", vendor.getService()));
        vendor.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
