package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.JOHN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditwCommand.EditWeddingDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Client;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.ModelStubAcceptingWeddingAdded;

class EditwCommandTest {

    @Test
    void execute_allFieldsSpecified_success() throws Exception {
        ModelStubAcceptingWeddingAdded modelStub = new ModelStubAcceptingWeddingAdded();
        Client validClient = new Client(JOHN);
        Wedding validWedding = new Wedding(new Name("WeddingJim"), validClient, new Date("2024-10-31"),
                new Venue("Venue1"));
        modelStub.addWedding(validWedding);

        EditwCommand.EditWeddingDescriptor descriptor = new EditwCommand.EditWeddingDescriptor();
        descriptor.setName(new Name("UpdatedWedding"));
        descriptor.setDate(new Date("2024-11-01"));
        descriptor.setVenue(new Venue("UpdatedVenue"));

        EditwCommand command = new EditwCommand(Index.fromOneBased(1), descriptor);
        CommandResult result = command.execute(modelStub);

        Wedding editedWedding = modelStub.getFilteredWeddingList().get(0);
        assertEquals("UpdatedWedding", editedWedding.getName().toString());
        assertEquals("2024-11-01", editedWedding.getDate().toString());
        assertEquals("UpdatedVenue", editedWedding.getVenue().toString());
        assertEquals(String.format(EditwCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding)),
                result.getFeedbackToUser());
    }

    @Test
    void execute_invalidIndex_throwsCommandException() {
        // Arrange
        ModelStubAcceptingWeddingAdded modelStub = new ModelStubAcceptingWeddingAdded();

        // Add a wedding to set up the initial data (only one wedding with index 1)
        Wedding initialWedding = new Wedding(new Name("Existing Wedding"), new Client(JOHN), new Date("2024-10-01"),
                new Venue("Initial Venue"));
        modelStub.addWedding(initialWedding);

        // Create an EditwCommand with an invalid index (e.g., index 10, which is out of bounds)
        EditwCommand.EditWeddingDescriptor descriptor = new EditwCommand.EditWeddingDescriptor();
        descriptor.setName(new Name("InvalidWedding"));
        EditwCommand command = new EditwCommand(Index.fromOneBased(10), descriptor);

        // Act & Assert
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    void execute_noFieldsSpecified_throwsCommandException() {
        ModelStubAcceptingWeddingAdded modelStub = new ModelStubAcceptingWeddingAdded();
        Wedding validWedding = new Wedding(new Name("WeddingJim"), new Date("2024-10-31"), new Venue("Venue1"));
        modelStub.addWedding(validWedding);

        EditwCommand.EditWeddingDescriptor descriptor = new EditwCommand.EditWeddingDescriptor();
        EditwCommand command = new EditwCommand(Index.fromOneBased(1), descriptor);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    void equals_sameValues_returnsTrue() {
        Index index = Index.fromOneBased(1);

        // Set up descriptors with the same values
        EditWeddingDescriptor descriptor1 = new EditwCommand.EditWeddingDescriptor();
        descriptor1.setName(new Name("Wedding"));
        descriptor1.setDate(new Date("2024-12-25"));
        descriptor1.setVenue(new Venue("Grand Ballroom"));

        EditWeddingDescriptor descriptor2 = new EditWeddingDescriptor();
        descriptor2.setName(new Name("Wedding"));
        descriptor2.setDate(new Date("2024-12-25"));
        descriptor2.setVenue(new Venue("Grand Ballroom"));

        // Create commands
        EditwCommand command1 = new EditwCommand(index, descriptor1);
        EditwCommand command2 = new EditwCommand(index, descriptor2);

        // Check equality for descriptors and commands
        assertEquals(descriptor1, descriptor2);
        assertEquals(command1, command2);
    }

    @Test
    void equals_differentValues_returnsFalse() {
        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);

        EditwCommand.EditWeddingDescriptor descriptor1 = new EditWeddingDescriptor();
        descriptor1.setName(new Name("WeddingWRONG"));
        descriptor1.setDate(new Date("2024-12-25"));
        descriptor1.setVenue(new Venue("Grand Ballroom"));

        EditWeddingDescriptor descriptor2 = new EditWeddingDescriptor();
        descriptor2.setName(new Name("WeddingRIGHT"));
        descriptor2.setDate(new Date("2024-12-26"));
        descriptor2.setVenue(new Venue("Beach Venue"));

        // Create commands with different indexes and descriptors
        EditwCommand command1 = new EditwCommand(index1, descriptor1);
        EditwCommand command2 = new EditwCommand(index2, descriptor2);

        // Check inequality for descriptors and commands
        assertNotEquals(descriptor1, descriptor2);
        assertNotEquals(command1, command2);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        Index index = Index.fromOneBased(1);

        EditWeddingDescriptor descriptor = new EditWeddingDescriptor();
        descriptor.setName(new Name("WeddingWow"));
        descriptor.setDate(new Date("2024-12-25"));
        descriptor.setVenue(new Venue("Grand Ballroom"));

        EditwCommand command = new EditwCommand(index, descriptor);

        // Check if command equals itself
        assertEquals(command, command);
    }

    @Test
    void equals_nullOrDifferentType_returnsFalse() {
        Index index = Index.fromOneBased(1);

        EditWeddingDescriptor descriptor = new EditWeddingDescriptor();
        descriptor.setName(new Name("WeddingBob"));
        descriptor.setDate(new Date("2024-12-25"));
        descriptor.setVenue(new Venue("Grand Ballroom"));

        EditwCommand command = new EditwCommand(index, descriptor);

        // Check inequality with null and different type
        assertNotEquals(null, command);
        assertNotEquals(command, new Object());
    }

    @Test
    void execute_partialFieldUpdate_success() throws Exception {
        ModelStubAcceptingWeddingAdded modelStub = new ModelStubAcceptingWeddingAdded();
        Client validClient = new Client(JOHN);
        Wedding validWedding = new Wedding(new Name("OriginalWedding"), validClient, new Date("2024-10-31"),
                new Venue("OriginalVenue"));
        modelStub.addWedding(validWedding);

        EditwCommand.EditWeddingDescriptor descriptor = new EditwCommand.EditWeddingDescriptor();
        descriptor.setVenue(new Venue("UpdatedVenue"));

        EditwCommand command = new EditwCommand(Index.fromOneBased(1), descriptor);
        CommandResult result = command.execute(modelStub);

        Wedding editedWedding = modelStub.getFilteredWeddingList().get(0);
        assertEquals("OriginalWedding", editedWedding.getName().toString());
        assertEquals("2024-10-31", editedWedding.getDate().toString());
        assertEquals("UpdatedVenue", editedWedding.getVenue().toString());
        assertEquals(String.format(EditwCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding)),
                result.getFeedbackToUser());
    }

    @Test
    void equals_selfAndOtherTypes_returnsExpectedResults() {
        EditWeddingDescriptor descriptor = new EditWeddingDescriptor();
        descriptor.setName(new Name("Wedding"));

        // Test self-comparison (should return true)
        assertEquals(descriptor, descriptor);

        // Test comparison with a different type (should return false)
        assertNotEquals(descriptor, new Object());

        // Test comparison with null (should return false)
        assertNotEquals(descriptor, null);
    }

    @Test
    void equals_differentDescriptorsWithSameValues_returnsTrue() {
        EditWeddingDescriptor descriptor1 = new EditWeddingDescriptor();
        descriptor1.setName(new Name("Wedding"));
        descriptor1.setDate(new Date("2024-12-25"));
        descriptor1.setVenue(new Venue("Grand Ballroom"));

        EditWeddingDescriptor descriptor2 = new EditWeddingDescriptor();
        descriptor2.setName(new Name("Wedding"));
        descriptor2.setDate(new Date("2024-12-25"));
        descriptor2.setVenue(new Venue("Grand Ballroom"));

        // Test equality for descriptors with the same values
        assertEquals(descriptor1, descriptor2);
    }

    @Test
    void equals_differentDescriptorsWithDifferentValues_returnsFalse() {
        EditWeddingDescriptor descriptor1 = new EditWeddingDescriptor();
        descriptor1.setName(new Name("WeddingJim"));

        EditWeddingDescriptor descriptor2 = new EditWeddingDescriptor();
        descriptor2.setName(new Name("WeddingNotJim"));

        // Test inequality for descriptors with different values
        assertNotEquals(descriptor1, descriptor2);
    }

    @Test
    void isAnyFieldEdited_noFieldsSet_returnsFalse() {
        EditWeddingDescriptor descriptor = new EditWeddingDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    void isAnyFieldEdited_someFieldsSet_returnsTrue() {
        EditWeddingDescriptor descriptor = new EditWeddingDescriptor();
        descriptor.setName(new Name("Wedding"));
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    void isAnyFieldEdited_allFieldsSet_returnsTrue() {
        EditWeddingDescriptor descriptor = new EditWeddingDescriptor();
        descriptor.setName(new Name("Wedding"));
        descriptor.setDate(new Date("2024-12-25"));
        descriptor.setVenue(new Venue("Grand Ballroom"));
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    void createEditedWedding_optionalFieldsNotEdited() {
        Wedding originalWedding = new Wedding(new Name("OriginalWedding"), new Client(JOHN), new Date("2024-10-01"),
                new Venue("OriginalVenue"));

        EditWeddingDescriptor descriptor = new EditWeddingDescriptor();
        descriptor.setName(new Name("UpdatedWedding"));

        Wedding editedWedding = EditwCommand.createEditedWedding(originalWedding, descriptor);

        // Ensure only the name is updated, and other fields remain unchanged
        assertEquals("UpdatedWedding", editedWedding.getName().toString());
        assertEquals("2024-10-01", editedWedding.getDate().toString());
        assertEquals("OriginalVenue", editedWedding.getVenue().toString());
    }
}
