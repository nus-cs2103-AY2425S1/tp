package seedu.ddd.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * An UI component that displays information of a {@code Vendor}.
 */
public class VendorCard extends DisplayedCard {

    private static final String FXML = "VendorCard.fxml";

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
     * Creates a {@code Vendor} with the given {@code vendor} and index to display.
     */
    public VendorCard(Vendor vendor, int displayedIndex) {
        super(FXML);
        this.vendor = vendor;

        name.setText(String.format("%s. %s", displayedIndex, vendor.getName().fullName));
        id.setText(String.format("Vendor: #%d", vendor.getId().contactId));

        phone.setText(String.format("📞  Phone Number: %s", vendor.getPhone()));
        email.setText(String.format("📫  Email: %s", vendor.getEmail()));
        address.setText(String.format("🏠  Address: %s", vendor.getAddress()));
        service.setText(String.format("🔨  Service: %s", vendor.getService()));
        vendor.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
