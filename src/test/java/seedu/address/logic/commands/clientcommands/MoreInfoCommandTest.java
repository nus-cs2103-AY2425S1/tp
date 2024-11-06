package seedu.address.logic.commands.clientcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonWithName;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalNames;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class MoreInfoCommandTest {
    private static final Name DO_NOT_EXIST_NAME = new Name("DO NOT EXIST NAME");
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());
    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(DO_NOT_EXIST_NAME);

        assertCommandFailure(moreInfoCommand, model, Messages.MESSAGE_INVALID_PERSON_INPUT);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 2);
        showPersonWithName(model, typicalNames.get(randomIndex));

        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(typicalNames
                .get(randomIndex + 1));

        assertCommandFailure(moreInfoCommand, model, Messages.MESSAGE_INVALID_PERSON_INPUT);
    }
    @Test
    public void execute_subName_throwsCommandException() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 1);
        Person personToMoreInfo = model.getPersonByName(typicalNames.get(randomIndex));
        String personToMoreInfoNameString = personToMoreInfo.getName().toString();
        Name subNamePersonToMoreInfo =
                new Name(personToMoreInfoNameString
                        .substring(0, personToMoreInfoNameString.length() - 1));
        MoreInfoCommand moreInfoCommand =
                new MoreInfoCommand(subNamePersonToMoreInfo);

        assertCommandFailure(moreInfoCommand, model,
                String.format(Messages.MESSAGE_SUGGESTION, personToMoreInfo.getName()));
    }

    @Test
    public void equals() {
        MoreInfoCommand moreInfoFirstCommand = new MoreInfoCommand(ALICE.getName());
        MoreInfoCommand moreInfoSecondCommand = new MoreInfoCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(moreInfoFirstCommand.equals(moreInfoFirstCommand));

        // same values -> returns true
        MoreInfoCommand moreInfoFirstCommandCopy = new MoreInfoCommand(ALICE.getName());
        assertTrue(moreInfoFirstCommand.equals(moreInfoFirstCommandCopy));

        // different types -> returns false
        assertFalse(moreInfoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(moreInfoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(moreInfoFirstCommand.equals(moreInfoSecondCommand));
    }

    @Test
    public void toStringMethod() {
        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(ALICE.getName());
        String expected = MoreInfoCommand.class.getCanonicalName() + "{targetName=" + ALICE.getName() + "}";
        assertEquals(expected, moreInfoCommand.toString());
    }
}
