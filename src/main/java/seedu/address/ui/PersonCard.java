package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Supplier;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;
    @FXML
    private Label details;
    @FXML
    private Label orders;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        remark.setText(person.getRemark().value);

        List<Order> orderList = person.getOrders();
        StringBuilder builder = new StringBuilder();
        for (Order order : orderList) {
            builder.append(order.toString()).append("\n");
        }
        orders.setText(builder.toString());

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);

                    if (tag.tagName.equals("Customer")) {
                        tagLabel.getStyleClass().add("tag-customer");
                    } else if (tag.tagName.equals("Supplier")) {
                        tagLabel.getStyleClass().add("tag-supplier");

                    }
                    tags.getChildren().add(tagLabel);
                });

        if (person instanceof Customer p) {
            details.setText(p.getInformation().toString());
        } else if (person instanceof Supplier p) {
            details.setText(p.getIngredientsSupplied().toString());
        } else {
            details.setText("");
        }

    }
}

