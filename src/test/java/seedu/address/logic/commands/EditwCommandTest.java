package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.JOHN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
        Wedding validWedding = new Wedding(new Name("WeddingJim"), new Date("2024-10-31"), new Venue("Venue1"));
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
        assertEquals(String.format(EditwCommand.MESSAGE_EDIT_WEDDING_SUCCESS, editedWedding),
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
}

