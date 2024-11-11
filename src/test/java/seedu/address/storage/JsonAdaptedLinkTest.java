package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOwners.BENSON;
import static seedu.address.testutil.TypicalPets.BISON;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

public class JsonAdaptedLinkTest {

    @Test
    public void toModelType_validLinkDetails_returnsLink() throws Exception {
        Link link = new Link(BENSON, BISON);
        JsonAdaptedLink jsonLink = new JsonAdaptedLink(link);

        ObservableList<Owner> owners = FXCollections.observableArrayList();
        owners.add(BENSON);
        ObservableList<Pet> pets = FXCollections.observableArrayList();
        pets.add(BISON);

        assertEquals(link, jsonLink.toModelType(owners, pets));
    }

    @Test
    public void toModelType_emptyOwners_throwsIllegalValueException() throws Exception {
        Link link = new Link(BENSON, BISON);
        JsonAdaptedLink jsonLink = new JsonAdaptedLink(link);

        ObservableList<Owner> owners = FXCollections.observableArrayList();
        ObservableList<Pet> pets = FXCollections.observableArrayList();
        pets.add(BISON);

        assertThrows(IllegalValueException.class, String.format(JsonAdaptedLink.INVALID_LINK_MESSAGE_FORMAT,
            BENSON.getUniqueID()), () -> jsonLink.toModelType(owners, pets));
    }

    @Test
    public void toModelType_emptyPets_throwsIllegalValueException() throws Exception {
        Link link = new Link(BENSON, BISON);
        JsonAdaptedLink jsonLink = new JsonAdaptedLink(link);

        ObservableList<Owner> owners = FXCollections.observableArrayList();
        owners.add(BENSON);
        ObservableList<Pet> pets = FXCollections.observableArrayList();

        assertThrows(IllegalValueException.class, String.format(JsonAdaptedLink.INVALID_LINK_MESSAGE_FORMAT,
            BISON.getUniqueID()), () -> jsonLink.toModelType(owners, pets));
    }
}
