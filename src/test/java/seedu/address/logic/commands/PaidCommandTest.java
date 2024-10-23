package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.EXPIRING_INSURANCE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRING_POLICY_NAME;
import static seedu.address.logic.commands.CommandTestUtil.NON_EXISTENT_POLICY_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_INVESTMENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;

public class PaidCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexAndPolicy_success() {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy policyToUpdate = new Policy(VALID_POLICY_NAME_INVESTMENT, VALID_DATE_1, VALID_DATE_2,
                VALID_INSURANCE_PAYMENT);
        personToUpdate.assignPolicy(policyToUpdate);

        PaidCommand paidCommand = new PaidCommand(INDEX_FIRST_PERSON, VALID_POLICY_NAME_INVESTMENT);

        String expectedMessage = String.format(PaidCommand.MESSAGE_SUCCESS, VALID_POLICY_NAME_INVESTMENT,
                personToUpdate.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUpdate, personToUpdate);

        assertCommandSuccess(paidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPolicy_throwsCommandException() {
        PaidCommand paidCommand = new PaidCommand(INDEX_FIRST_PERSON, NON_EXISTENT_POLICY_NAME);

        assertCommandFailure(paidCommand, model, String.format(PaidCommand.MESSAGE_INVALID_POLICY,
                NON_EXISTENT_POLICY_NAME, model.getFilteredPersonList()
                        .get(INDEX_FIRST_PERSON.getZeroBased()).getName()));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PaidCommand paidCommand = new PaidCommand(outOfBoundIndex, VALID_POLICY_NAME_INVESTMENT);

        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_policyExpiringSoon_throwsCommandException() {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Policy policyToUpdate = new Policy(EXPIRING_POLICY_NAME, VALID_DATE_1, VALID_DATE_2,
                EXPIRING_INSURANCE_PAYMENT);
        personToUpdate.assignPolicy(policyToUpdate);

        PaidCommand paidCommand = new PaidCommand(INDEX_SECOND_PERSON, EXPIRING_POLICY_NAME);

        assertCommandFailure(paidCommand, model, String.format(PaidCommand.MESSAGE_INVALID_PAYDATE,
                EXPIRING_POLICY_NAME, personToUpdate.getName()));
    }

}
