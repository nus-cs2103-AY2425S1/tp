package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private Label information;
    @FXML
    private Label ingredients; // Added for Supplier

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

        // Check if the person is a Customer and display their information field
        if (person instanceof Customer) {
            Customer customer = (Customer) person;
            information.setText(customer.getInformation().value);
            information.setVisible(true); // Make the label visible for customers
            // Hide Supplier-specific fields
            ingredients.setText("");
            ingredients.setVisible(false);
        } else if (person instanceof Supplier) {
            Supplier supplier = (Supplier) person;
            String joinedString = String.join(",", supplier.getIngredientsSupplied().getIngredientNames());
            ingredients.setText(joinedString);
            ingredients.setVisible(true); // Make the label visible for suppliers
            // Hide Customer-specific fields
            information.setText("");
            information.setVisible(false);
        } else {
            // Hide or clear both Customer and Supplier-specific fields for generic Person
            information.setText("");
            information.setVisible(false);
            ingredients.setText("");
            ingredients.setVisible(false);
        }

        // Sort and display the tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);

                    // Apply the CSS class for "customer" and "supplier" tags
                    if (tag.tagName.equals("customer")) {
                        tagLabel.getStyleClass().add("tag-customer"); // Ensure that the "tag-customer" style is defined in your CSS
                    } else if (tag.tagName.equals("supplier")) {
                        tagLabel.getStyleClass().add("tag-supplier");
                    }
                    tags.getChildren().add(tagLabel);
                });
    }
}


