package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTRACT_END_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContractEndDate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code PromoteCommand}.
 */
public class PromoteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ContractEndDate validContractEndDate = ContractEndDate.of(VALID_CONTRACT_END_DATE_AMY);

    @Test
    public void execute_validArgumentsAndPerson_success() {
        Person lastPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person promotedPerson = personInList.withIsEmployee(true)
                        .withContractEndDate(VALID_CONTRACT_END_DATE_AMY).build();
        PromoteCommand promoteCommand = new PromoteCommand(INDEX_FIRST_PERSON, validContractEndDate);

        String expectedMessage = String.format(PromoteCommand.MESSAGE_SUCCESS, Messages.format(promotedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, promotedPerson);

        assertCommandSuccess(promoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notPotentialHire_failure() {
        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        PromoteCommand promoteCommand = new PromoteCommand(lastIndex, validContractEndDate);
        assertCommandFailure(promoteCommand, model, PromoteCommand.MESSAGE_NOT_A_POTENTIAL_HIRE);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PromoteCommand promoteCommand = new PromoteCommand(outOfBoundIndex, validContractEndDate);

        assertCommandFailure(promoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Promote in a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PromoteCommand promoteCommand = new PromoteCommand(outOfBoundIndex, validContractEndDate);

        assertCommandFailure(promoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final PromoteCommand standardCommand = new PromoteCommand(INDEX_FIRST_PERSON, validContractEndDate);
        final ContractEndDate otherValidDate = ContractEndDate.of("2024-12-12");

        // same index and contract end date -> returns true
        PromoteCommand commandWithSameIndex = new PromoteCommand(INDEX_FIRST_PERSON, validContractEndDate);
        assertTrue(standardCommand.equals(commandWithSameIndex));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new PromoteCommand(INDEX_SECOND_PERSON, validContractEndDate)));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different contract end date -> returns false
        assertFalse(standardCommand.equals(new PromoteCommand(INDEX_FIRST_PERSON, otherValidDate)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        PromoteCommand promoteCommand = new PromoteCommand(index, validContractEndDate);
        String expected = PromoteCommand.class.getCanonicalName() + "{index=" + index + ", contractEndDate="
                + VALID_CONTRACT_END_DATE_AMY + "}";
        assertEquals(expected, promoteCommand.toString());
    }
}
