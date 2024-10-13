package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.vendor.Vendor;

/**
 * Panel containing the list of vendors.
 */
public class VendorDetailsPanel extends UiPart<Region> {
    private static final String FXML = "VendorDetailsPanel.fxml";
    private Vendor vendor;
    private final Logger logger = LogsCenter.getLogger(VendorDetailsPanel.class);

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label description;

    /**
     * Creates a {@code VendorListPanel} with the given {@code ObservableList}.
     */
    public VendorDetailsPanel(ObservableObjectValue<Vendor> vendor) {
        super(FXML);

        vendor.addListener((observable, oldValue, newValue) -> {
            this.vendor = newValue;
            name.setText(this.vendor.getName().fullName);
            phone.setText(this.vendor.getPhone().value);
            description.setText(this.vendor.getDescription().value);
        });

    }

}
