package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    private FlowPane sellingProperties;
    @FXML
    private FlowPane buyingProperties;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        // Initialize the properties display
        updateSellingProperties();
        updateBuyingProperties();

        // Listen to changes in selling properties and buying properties
        person.getListOfSellingProperties().addListener((ListChangeListener<Property>)
                change -> updateSellingProperties());
        person.getListOfBuyingProperties().addListener((ListChangeListener<Property>)
                change -> updateBuyingProperties());
    }

    private void updateSellingProperties() {
        sellingProperties.getChildren().clear(); // Clear old data
        int[] sellingIndex = {1};
        person.getListOfSellingProperties().forEach(property -> {
            // Remove the "Tags" section from the property string
            String propertyWithoutTags = removeTagsFromString(property.toString());

            // Create a new HBox to hold the label and tags
            HBox propertyContainer = new HBox();
            Label label = new Label(sellingIndex[0] + ". " + propertyWithoutTags);
            label.getStyleClass().add("cell_small_label");
            propertyContainer.getChildren().add(label);

            // Create a FlowPane for property tags
            FlowPane propertyTags = new FlowPane();
            propertyTags.setHgap(2.5); // Horizontal gap between tags
            propertyTags.setVgap(5); // Vertical gap between tags
            property.getTags().forEach(tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.getStyleClass().add("tag-box"); // Apply tag-box style
                propertyTags.getChildren().add(tagLabel);
            });

            // Create an HBox to encapsulate both property text and its tags
            HBox outerContainer = new HBox();
            outerContainer.setSpacing(10); // Add spacing between label and tags
            outerContainer.setPadding(new Insets(10, 0, 10, 0)); // Add padding between HBox and text
            outerContainer.getChildren().addAll(propertyContainer, propertyTags);

            // Add the outer HBox to the sellingProperties FlowPane
            sellingProperties.getChildren().add(outerContainer);
            sellingIndex[0]++;
        });
    }

    private void updateBuyingProperties() {
        buyingProperties.getChildren().clear(); // Clear old data
        int[] buyingIndex = {1};
        person.getListOfBuyingProperties().forEach(property -> {
            // Remove the "Tags" section from the property string
            String propertyWithoutTags = removeTagsFromString(property.toString());

            // Create a new HBox to hold the label and tags
            HBox propertyContainer = new HBox();
            Label label = new Label(buyingIndex[0] + ". " + propertyWithoutTags);
            label.getStyleClass().add("cell_small_label");
            propertyContainer.getChildren().add(label);

            // Create a FlowPane for property tags
            FlowPane propertyTags = new FlowPane();
            propertyTags.setHgap(2.5); // Horizontal gap between tags
            propertyTags.setVgap(5); // Vertical gap between tags
            property.getTags().forEach(tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.getStyleClass().add("tag-box"); // Apply tag-box style
                propertyTags.getChildren().add(tagLabel);
            });

            // Create an HBox to encapsulate both property text and its tags
            HBox outerContainer = new HBox();
            outerContainer.setSpacing(10); // Add spacing between label and tags
            outerContainer.setPadding(new Insets(10, 0, 10, 0)); // Add padding between HBox and text
            outerContainer.getChildren().addAll(propertyContainer, propertyTags);

            // Add the outer HBox to the buyingProperties FlowPane
            buyingProperties.getChildren().add(outerContainer);
            buyingIndex[0]++;
        });
    }

    /**
     * Helper method to remove the 'Tags' section from the property string.
     */
    private String removeTagsFromString(String fullPropertyString) {
        // Detect the start of "Tags " and remove everything after it
        int indexOfTags = fullPropertyString.indexOf(" Tags: ");
        return (indexOfTags != -1) ? fullPropertyString.substring(0, indexOfTags).trim() : fullPropertyString;
    }
}
