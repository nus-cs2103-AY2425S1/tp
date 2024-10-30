package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLAIM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.EditClaimCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.policy.PolicyType;
import seedu.address.testutil.EditClaimDescriptorBuilder;

public class EditClaimCommandTest {

    private Model model;
    private final PolicyType validPolicyType = PolicyType.HEALTH;
    private final Index validClaimIndex = Index.fromOneBased(1);
    private final EditClaimCommandParser parser = new EditClaimCommandParser();

    private final EditClaimDescriptor descriptor = new EditClaimDescriptorBuilder()
            .withStatus(ClaimStatus.PENDING)
            .withDescription("Surgery claim")
            .build();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditClaimCommand(null, validPolicyType, validClaimIndex, descriptor));
    }

    @Test
    public void constructor_nullPolicyType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditClaimCommand(INDEX_FIRST_PERSON, null, validClaimIndex, descriptor));
    }

    @Test
    public void constructor_nullClaimIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditClaimCommand(INDEX_FIRST_PERSON, validPolicyType, null, descriptor));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        // Test with an out-of-bounds index for the person list
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditClaimCommand editClaimCommand = new EditClaimCommand(outOfBoundsIndex, validPolicyType,
                validClaimIndex, descriptor);
        assertCommandFailure(editClaimCommand, model, EditClaimCommand.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void parse_validArgs_returnsEditClaimCommand() throws Exception {
        // Example user input for editing a claim
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "1 "
                + PREFIX_CLAIM_STATUS + "approved " + PREFIX_CLAIM_DESC + "Updated claim details.";

        // Expected command to be returned by the parser
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        editClaimDescriptor.setStatus(ClaimStatus.APPROVED);
        editClaimDescriptor.setDescription("Updated claim details.");

        EditClaimCommand expectedCommand = new EditClaimCommand(
                INDEX_FIRST_PERSON, PolicyType.HEALTH, INDEX_FIRST_CLAIM, editClaimDescriptor);

        // Parse the user input and get the actual command
        EditClaimCommand actualCommand = parser.parse(userInput);

        // Assert that the actual command matches the expected command
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void equals() {
        EditClaimCommand editClaimCommand1 = new EditClaimCommand(INDEX_FIRST_PERSON, validPolicyType,
                validClaimIndex, descriptor);
        EditClaimCommand editClaimCommand2 = new EditClaimCommand(INDEX_FIRST_PERSON, validPolicyType,
                validClaimIndex, descriptor);

        // same object -> returns true
        assert(editClaimCommand1.equals(editClaimCommand1));

        // same values -> returns true
        assert(editClaimCommand1.equals(editClaimCommand2));

        // different index -> returns false
        EditClaimCommand editClaimDifferentIndex = new EditClaimCommand(Index.fromOneBased(2),
                validPolicyType, validClaimIndex, descriptor);
        assert(!editClaimCommand1.equals(editClaimDifferentIndex));

        // different policy type -> returns false
        EditClaimCommand editClaimDifferentPolicy = new EditClaimCommand(INDEX_FIRST_PERSON, PolicyType.LIFE,
                validClaimIndex, descriptor);
        assert(!editClaimCommand1.equals(editClaimDifferentPolicy));

        // different claim index -> returns false
        EditClaimCommand editClaimDifferentClaimIndex = new EditClaimCommand(INDEX_FIRST_PERSON, validPolicyType,
                Index.fromOneBased(2), descriptor);
        assert(!editClaimCommand1.equals(editClaimDifferentClaimIndex));
    }
}
