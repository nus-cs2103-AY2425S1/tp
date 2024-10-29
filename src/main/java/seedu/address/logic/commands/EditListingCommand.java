package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalListings.getTypicalListings;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditListingCommand.EditListingDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.testutil.EditListingDescriptorBuilder;
import seedu.address.testutil.ListingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditListingCommand.
 */
public class EditListingCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());
    }

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        Listing editedListing = new ListingBuilder().withName("Updated Listing").build();
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing).build();
        EditListingCommand editCommand = new EditListingCommand(editedListing.getName(), descriptor);

        CommandResult result = editCommand.execute(model);

        assertEquals(String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS, editedListing), result.getFeedbackToUser());
        assertEquals(editedListing, model.getFilteredListingList().get(0));
    }

    @Test
    public void execute_someFieldsSpecified_success() throws Exception {
        Listing lastListing = model.getFilteredListingList().get(model.getFilteredListingList().size() - 1);
        Listing editedListing = new ListingBuilder(lastListing).withPrice("4500").build();

        EditListingDescriptor descriptor = new EditListingDescriptorBuilder().withPrice("4500").build();
        EditListingCommand editCommand = new EditListingCommand(lastListing.getName(), descriptor);

        CommandResult result = editCommand.execute(model);

        assertEquals(String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS, editedListing), result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateListing_failure() {
        Listing firstListing = model.getFilteredListingList().get(0);
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(firstListing).build();
        EditListingCommand editCommand = new EditListingCommand(firstListing.getName(), descriptor);

        assertCommandFailure(editCommand, EditListingCommand.MESSAGE_DUPLICATE_LISTING);
    }

    @Test
    public void execute_invalidListingName_failure() {
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder().withPrice("4000").build();
        EditListingCommand editCommand = new EditListingCommand(new Name("Nonexistent Listing"), descriptor);

        assertCommandFailure(editCommand, EditListingCommand.MESSAGE_INVALID_LISTING_NAME);
    }

    @Test
    public void execute_sellerNotFound_failure() {
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder().withSellerName(new Name("Nonexistent Seller")).build();
        EditListingCommand editCommand = new EditListingCommand(model.getFilteredListingList().get(0).getName(), descriptor);

        assertCommandFailure(editCommand, "Seller not found in the system.");
    }

    @Test
    public void equals() {
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder().withPrice("4000").build();
        EditListingCommand command = new EditListingCommand(new Name("Test Listing"), descriptor);

        // same values -> returns true
        EditListingDescriptor copyDescriptor = new EditListingDescriptor(descriptor);
        EditListingCommand commandWithSameValues = new EditListingCommand(new Name("Test Listing"), copyDescriptor);
        assertTrue(command.equals(commandWithSameValues));

        // different descriptor -> returns false
        EditListingCommand commandWithDifferentDescriptor = new EditListingCommand(new Name("Test Listing"),
                new EditListingDescriptorBuilder().withPrice("5000").build());
        assertFalse(command.equals(commandWithDifferentDescriptor));
    }

    /**
     * Helper method to handle command failure assertions.
     */
    private void assertCommandFailure(EditListingCommand command, String expectedMessage) {
        try {
            command.execute(model);
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}