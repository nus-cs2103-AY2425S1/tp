package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import spleetwaise.address.model.person.Person;

/**
 * A handle for a person card in the user interface, providing methods to access and retrieve the details of a person
 * displayed in the card.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    /**
     * Creates a PersonCardHandle to manage the specified person card node.
     *
     * @param cardNode The node representing the person card in the user interface.
     */
    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the ID displayed on the person card.
     *
     * @return The ID of the person.
     */
    public String getId() {
        return idLabel.getText();
    }

    /**
     * Retrieves the name displayed on the person card.
     *
     * @return The name of the person.
     */
    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Retrieves the address displayed on the person card.
     *
     * @return The address of the person.
     */
    public String getAddress() {
        return addressLabel.getText();
    }

    /**
     * Retrieves the phone number displayed on the person card.
     *
     * @return The phone number of the person.
     */
    public String getPhone() {
        return phoneLabel.getText();
    }

    /**
     * Retrieves the email address displayed on the person card.
     *
     * @return The email address of the person.
     */
    public String getEmail() {
        return emailLabel.getText();
    }

    /**
     * Retrieves the tags associated with the person from the card.
     *
     * @return A list of tags associated with the person.
     */
    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Compares the current person card with a specified person object to check for equality.
     *
     * @param person The person object to compare against.
     * @return True if the person card matches the specified person, false otherwise.
     */
    public boolean equals(Person person) {
        return getName().equals(person.getName().fullName)
                && getAddress().equals(person.getAddress().value)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value)
                && getTags().equals(person.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
