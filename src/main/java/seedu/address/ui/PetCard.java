package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.LinkedOwnerList;
import seedu.address.model.pet.Pet;

/**
 * A UI component that displays information of a {@code Pet}.
 */
public class PetCard extends UiPart<Region> {

    private static final String FXML = "PetListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on PawPatrol level 4</a>
     */

    public final Pet pet;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label species;
    @FXML
    private Label breed;
    @FXML
    private Label age;
    @FXML
    private Label sex;
    @FXML
    private Label linkedOwner;  // Label to display linked pets

    private ObservableList<Owner> ownerList;

    /**
     * Creates a {@code PetCode} with the given {@code Pet} and index to display.
     */
    public PetCard(Pet pet, int displayedIndex) {
        super(FXML);
        this.pet = pet;
        id.setText(displayedIndex + ". ");
        name.setText(pet.getName().name + " uid: " + pet.getUniqueID());
        species.setText(pet.getSpecies().value);
        breed.setText(pet.getBreed().value);
        age.setText(pet.getAge().value);
        sex.setText(pet.getSex().toString()); // toString() method will convert the single char sex value to a full word
        // Set the linked pets using the toString() of each pet
        linkedOwner.setText(formatLinkedOwner(pet.getLinkedOwner()));

        this.ownerList = pet.getLinkedOwner().getList();

        // Add a listener to dynamically update the linked pets when they change
        ownerList.addListener((ListChangeListener<Owner>) change -> updateLinkedOwnerDisplay());
    }

    /**
     * Updates the displayed linked owner when the list changes.
     */
    private void updateLinkedOwnerDisplay() {
        linkedOwner.setText(formatLinkedOwner(pet.getLinkedOwner()));
    }

    /**
     * Formats the list of linked pets as a single line separated by vertical bars.
     *
     * @param linkedOwner the list of linked pets to format
     * @return a formatted string of linked pet names
     */
    private String formatLinkedOwner(LinkedOwnerList linkedOwner) {
        StringBuilder formattedOwner = new StringBuilder();

        formattedOwner.append("Owner: ");

        for (Owner owner : linkedOwner) {
            formattedOwner.append(owner.getName());
        }

        return formattedOwner.toString();
    }
}
