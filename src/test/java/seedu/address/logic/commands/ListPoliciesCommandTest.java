package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TypicalPersons;

public class ListPoliciesCommandTest {

    // one index greater than the last index in the typical address book
    private static final Index INDEX_OUT_OF_BOUNDS = Index.fromOneBased(TypicalPersons
            .getTypicalAddressBook().getPersonList().size() + 1);

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexWithPolicies_success() throws Exception {
        // Benson Meier is the second person in the list with one policy
        ListPoliciesCommand command = new ListPoliciesCommand(INDEX_SECOND_PERSON);
        Person personWithPolicies = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        String expectedMessage = personWithPolicies.getPolicies().stream()
                .map(Policy::toString)
                .collect(Collectors.joining("\n"));
        expectedMessage = String.format(ListPoliciesCommand.MESSAGE_LIST_POLICIES_SUCCESS,
                personWithPolicies.getName(), expectedMessage);

        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_validIndexWithNoPolicies_success() throws Exception {
        // Alice Pauline is the first person in the list with no policies
        ListPoliciesCommand command = new ListPoliciesCommand(INDEX_FIRST_PERSON);
        Person personWithoutPolicies = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(ListPoliciesCommand.MESSAGE_NO_POLICIES,
                personWithoutPolicies.getName());

        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ListPoliciesCommand command = new ListPoliciesCommand(INDEX_OUT_OF_BOUNDS);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        ListPoliciesCommand command1 = new ListPoliciesCommand(INDEX_FIRST_PERSON);
        ListPoliciesCommand command2 = new ListPoliciesCommand(INDEX_SECOND_PERSON);
        ListPoliciesCommand command3 = new ListPoliciesCommand(INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command3));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different index -> returns false
        assertFalse(command1.equals(command2));
    }
}
