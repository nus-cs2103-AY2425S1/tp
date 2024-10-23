package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.Order;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class CustomerOrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Order order;

//    @FXML
//    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label date;
    @FXML
    private Label items;
    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public CustomerOrderCard(CustomerOrder order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
//        name.setText(person.getName().fullName);
        phone.setText(order.getPhoneNumber());
        date.setText(order.getOrderDate());
        items.setText(order.viewOrder());
    }
}
