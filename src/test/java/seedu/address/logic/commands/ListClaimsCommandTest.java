package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.person.Person;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

/**
 * Contains test cases for the ListClaimsCommand class.
 */
public class ListClaimsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // reset model before each test to ensure isolation between tests
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    private ReadOnlyAddressBook getTypicalAddressBookWithClaims() {
        // create a new model to prevent side effects across tests
        Model tempModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person person = tempModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        PolicySet policies = new PolicySet();

        Policy healthPolicy = new HealthPolicy();
        healthPolicy.addClaim(new Claim(ClaimStatus.PENDING, "Hospitalization"));
        policies.add(healthPolicy);

        Person updatedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), policies);
        tempModel.setPerson(person, updatedPerson);

        return tempModel.getAddressBook();
    }

    private ReadOnlyAddressBook getTypicalAddressBookWithNoClaims() {
        // create a new model to prevent side effects across tests
        Model tempModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person person = tempModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PolicySet policies = new PolicySet();

        // add health policy but no claims
        Policy healthPolicy = new HealthPolicy();
        policies.add(healthPolicy);

        Person updatedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), policies);
        tempModel.setPerson(person, updatedPerson);

        return tempModel.getAddressBook();
    }

    @Test
    public void execute_validIndexAndPolicyTypeWithClaims_success() {

        Model modelWithClaims = new ModelManager(getTypicalAddressBookWithClaims(), new UserPrefs());

        ListClaimsCommand command = new ListClaimsCommand(INDEX_SECOND_PERSON, PolicyType.HEALTH);
        Person person = modelWithClaims.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        String expectedMessage = String.format(ListClaimsCommand.MESSAGE_LIST_CLAIMS_SUCCESS,
                PolicyType.HEALTH, person.getName(), "Claim Status: Pending | Claim Description: Hospitalization");

        assertCommandSuccess(command, modelWithClaims, expectedMessage, modelWithClaims);
    }

    @Test
    public void execute_validIndexAndPolicyTypeNoClaims_success() {
        // model with a person who has no claims for the specified policy type
        Model modelWithNoClaims = new ModelManager(getTypicalAddressBookWithNoClaims(), new UserPrefs());

        ListClaimsCommand command = new ListClaimsCommand(INDEX_FIRST_PERSON, PolicyType.HEALTH);
        Person person = modelWithNoClaims.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(ListClaimsCommand.MESSAGE_NO_CLAIMS, PolicyType.HEALTH,
                person.getName());

        assertCommandSuccess(command, modelWithNoClaims, expectedMessage, modelWithNoClaims);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ListClaimsCommand command = new ListClaimsCommand(outOfBoundIndex, PolicyType.HEALTH);

        assertCommandFailure(command, model, ListClaimsCommand.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void execute_policyTypeNotFound_throwsCommandException() {
        // no policy of the specified type
        ListClaimsCommand command = new ListClaimsCommand(INDEX_FIRST_PERSON, PolicyType.HEALTH);

        assertCommandFailure(command, model, String.format(ListClaimsCommand.MESSAGE_NO_POLICY_OF_TYPE,
                PolicyType.HEALTH, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName()));
    }

    @Test
    public void equals() {
        ListClaimsCommand command1 = new ListClaimsCommand(INDEX_FIRST_PERSON, PolicyType.HEALTH);
        ListClaimsCommand command2 = new ListClaimsCommand(INDEX_FIRST_PERSON, PolicyType.HEALTH);
        ListClaimsCommand command3 = new ListClaimsCommand(INDEX_SECOND_PERSON, PolicyType.HEALTH);
        ListClaimsCommand command4 = new ListClaimsCommand(INDEX_FIRST_PERSON, PolicyType.LIFE);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different index -> returns false
        assertFalse(command1.equals(command3));

        // different policy type -> returns false
        assertFalse(command1.equals(command4));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different type -> returns false
        assertFalse(command1.equals(new ClearCommand()));
    }

}
