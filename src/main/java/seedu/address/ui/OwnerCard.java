package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.owner.LinkedPetList;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * An UI component that displays information of a {@code Owner}.
 */
public class OwnerCard extends UiPart<Region> {

    private static final String FXML = "OwnerListCard.fxml";

    public final Owner owner;

    @FXML
    private HBox cardPane;
    @FXML
    private Label icNumber;
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
    private Label linkedPets; //Label to display linked pets

    private ObservableList<Pet> petList;

    /**
     * Creates a {@code OwnerCard} with the given {@code Owner} and index to display.
     */
    public OwnerCard(Owner owner, int displayedIndex) {
        super(FXML);
        this.owner = owner;
        id.setText(displayedIndex + ". ");
        icNumber.setText("IC: " + owner.getIdentificationNumber().getRedacted());
        name.setText(owner.getName().fullName);
        phone.setText("Phone: " + owner.getPhone().value);
        address.setText("Address: " + owner.getAddress().value);
        email.setText("Email: " + owner.getEmail().value);
        // Set the linked pets using the toString() of each pet
        linkedPets.setText(formatLinkedPets(owner.getLinkedPets()));

        this.petList = owner.getLinkedPets().getList();

        // Add a listener to dynamically update the linked pets when they change
        petList.addListener((ListChangeListener<Pet>) change -> updateLinkedPetsDisplay());
    }

    /**
     * Updates the displayed linked pets when the list changes.
     */
    private void updateLinkedPetsDisplay() {
        linkedPets.setText(formatLinkedPets(owner.getLinkedPets()));
    }

    /**
     * Formats the list of linked pets as a single line separated by vertical bars.
     *
     * @param linkedPets the list of linked pets to format
     * @return a formatted string of linked pet names
     */
    private String formatLinkedPets(LinkedPetList linkedPets) {
        return linkedPets.getAsField();
    }
}
