package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_AMOUNT_DUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_PAYMENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_INVESTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_LIFE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;



/**
 * Contains unit tests for {@code AssignPolicyCommand}.
 */
public class AssignPolicyCommandTest {

    private Model model;



    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Policy validPolicy = new Policy(VALID_POLICY_NAME_INVESTMENT,
                VALID_DATE_1, VALID_DATE_2, VALID_INSURANCE_PAYMENT);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(INDEX_FIRST_PERSON, validPolicy);

        String expectedMessage = String.format(AssignPolicyCommand.MESSAGE_SUCCESS, Messages.format(personToEdit));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getOneBased()).assignPolicy(validPolicy);

        assertCommandSuccess(assignPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePolicyUnfilteredList_failure() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy duplicatePolicy = new Policy(VALID_POLICY_NAME_LIFE, VALID_DATE_1,
                VALID_DATE_2, VALID_INSURANCE_PAYMENT);
        personToEdit.assignPolicy(duplicatePolicy);

        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(INDEX_FIRST_PERSON, duplicatePolicy);

        assertCommandFailure(assignPolicyCommand, model, AssignPolicyCommand.MESSAGE_DUPLICATE_POLICY);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Policy validPolicy = new Policy(VALID_POLICY_NAME_LIFE, VALID_DATE_1, VALID_DATE_2, VALID_INSURANCE_PAYMENT);

        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(outOfBoundIndex, validPolicy);

        assertCommandFailure(assignPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        Policy validPolicy = new Policy(VALID_POLICY_NAME_LIFE,
                VALID_DATE_1, VALID_DATE_2, VALID_INSURANCE_PAYMENT);

        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(outOfBoundIndex, validPolicy);

        assertCommandFailure(assignPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void checkEquals() {
        Policy policy = new Policy(VALID_POLICY_NAME_LIFE, VALID_DATE_1, VALID_DATE_2,
                VALID_INSURANCE_PAYMENT_DATE + " " + VALID_INSURANCE_AMOUNT_DUE);
        Index targetIndex = Index.fromZeroBased(0);
        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(targetIndex, policy);
        assertTrue(assignPolicyCommand.equals(assignPolicyCommand));
    }

}
