package seedu.address.model.person;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAllergyCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AllergyTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidAllergyName_throwsIllegalArgumentException() {
        String invalidAllergyName = "";
        assertThrows(IllegalArgumentException.class, () -> new Allergy(invalidAllergyName));
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        Allergy allergy = new Allergy("Pollen");
        Nric nric = ALICE.getNric();
        Set<Allergy> allergies = new HashSet<>();
        allergies.add(allergy);
        AddAllergyCommand addAllergyCommand = new AddAllergyCommand(nric, allergies);
        String allergyMessage = "[" + allergy + "]";
        String expectedMessage = String.format(AddAllergyCommand.MESSAGE_ADD_ALLERGY_SUCCESS, allergyMessage, nric);
        assertCommandSuccess(addAllergyCommand, model, expectedMessage, model);
    }
}
