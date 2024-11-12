package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.Order;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class CustomerOrderCard extends UiPart<Region> {

    private static final String FXML = "CustomerOrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Order order;

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label date;
    @FXML
    private Label items;
    @FXML
    private Label remark;
    @FXML
    private FlowPane status;

    /**
     * Creates a {@code CustomerOrderCard} with the given {@code CustomerOrder} and index to display.
     */
    public CustomerOrderCard(CustomerOrder order, int displayedIndex) {
        super(FXML);
        assert order != null : "CustomerOrder should never be null";

        this.order = order;
        id.setText(displayedIndex + ". ");
        name.setText(order.getPerson().getName().fullName);
        phone.setText(order.getPerson().getPhone().value);
        date.setText(order.getOrderDate());
        items.setText(order.viewOrder());
        remark.setText(order.getRemark().toString());

        Label tagLabel = new Label(order.getStatus().toString());
        if (order.getStatus().toString().equals("Pending")) {
            tagLabel.getStyleClass().add("tag-pending");
        }
        status.getChildren().add(tagLabel);

    }
}
