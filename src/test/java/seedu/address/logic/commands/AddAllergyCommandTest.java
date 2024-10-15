package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class AddAllergyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullNricAndNullAllergies_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAllergyCommand(null, null));
    }

    @Test
    public void execute_noNricFound() throws Exception {
        // ensures CommandException is thrown when provided with Nric that is not in addressbook
        Nric nric = new Nric("T1111111F");
        Set<Tag> tags = ALICE.getTags();
        AddAllergyCommand command = new AddAllergyCommand(nric, tags);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_addAllergy_success() throws Exception {
        // ensures CommandException is thrown when provided with Nric that is not in addressbook
        Nric nric = ALICE.getNric();
        String allergy = "Peanut allergy";
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(allergy));

        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag(allergy));
        expectedTags.addAll(ALICE.getTags());

        AddAllergyCommand command = new AddAllergyCommand(nric, tags);
        command.execute(model);
        Person alice = model.fetchPersonIfPresent(new NricMatchesPredicate(ALICE.getNric()))
                .orElse(null);
        assertEquals(expectedTags, alice.getTags());
    }

    @Test
    public void equals() {
        Nric aliceNric = ALICE.getNric();
        Set<Tag> aliceTags = ALICE.getTags();
        Nric bobNric = BOB.getNric();
        Set<Tag> bobTags = BOB.getTags();
        AddAllergyCommand addAliceAllergyCommand = new AddAllergyCommand(aliceNric, aliceTags);
        AddAllergyCommand addBobCommand = new AddAllergyCommand(bobNric, bobTags);
        // same object -> returns true
        assertTrue(addAliceAllergyCommand.equals(addAliceAllergyCommand));

        // same values -> returns true
        AddAllergyCommand addAliceAllergyCommandCopy = new AddAllergyCommand(aliceNric, aliceTags);
        assertTrue(addAliceAllergyCommand.equals(addAliceAllergyCommandCopy));

        // different types -> returns false
        assertFalse(addAliceAllergyCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceAllergyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceAllergyCommand.equals(addBobCommand));
    }
}
