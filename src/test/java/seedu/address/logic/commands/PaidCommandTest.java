package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAID_INSURANCE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_LIFE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
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
import seedu.address.testutil.PersonBuilder;

public class PaidCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexAndPolicy_success() throws seedu.address.logic.commands.exceptions.CommandException {
        Person person = new PersonBuilder().withName("John Doe").withPolicies("Policy 1").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        Index policyIndex = Index.fromOneBased(1);
        PaidCommand paidCommand = new PaidCommand(personIndex, policyIndex);

        Person personToUpdate = model.getFilteredPersonList().get(personIndex.getZeroBased());
        Policy policyToUpdate = personToUpdate.getPolicies().get(policyIndex.getZeroBased());
        Policy updatedPolicy = new Policy(VALID_POLICY_NAME_LIFE, VALID_DATE_1, VALID_DATE_2,
                VALID_PAID_INSURANCE_PAYMENT);

        Person updatedPerson = new PersonBuilder(personToUpdate).withPolicies(String.valueOf(updatedPolicy)).build();
        model.setPerson(personToUpdate, updatedPerson);

        String expectedMessage = String.format(PaidCommand.MESSAGE_SUCCESS, policyToUpdate.getPolicyName(),
                personToUpdate.getName());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUpdate, updatedPerson);

        assertCommandSuccess(paidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPolicy_throwsCommandException() {
        PaidCommand paidCommand = new PaidCommand(INDEX_FIRST_PERSON, Index.fromOneBased(23));

        assertCommandFailure(paidCommand, model, String.format(PaidCommand.MESSAGE_INVALID_POLICY,
                23, model.getFilteredPersonList()
                        .get(INDEX_FIRST_PERSON.getZeroBased()).getName()));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PaidCommand paidCommand = new PaidCommand(outOfBoundIndex, Index.fromOneBased(1));

        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
