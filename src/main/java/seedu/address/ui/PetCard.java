package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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

    /**
     * Creates a {@code PetCode} with the given {@code Pet} and index to display.
     */
    public PetCard(Pet pet, int displayedIndex) {
        super(FXML);
        this.pet = pet;
        id.setText(displayedIndex + ". ");
        name.setText(pet.getName().name);
        species.setText(pet.getSpecies().value);
        breed.setText(pet.getBreed().value);
        age.setText(pet.getAge().value);
        sex.setText(pet.getSex().toString()); // toString() method will convert the single char sex value to a full word
    }
}
