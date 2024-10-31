package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_SERVICE_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.ddd.logic.commands.EditContactCommand.EditVendorDescriptor;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.logic.parser.CliFlags;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.predicate.NameContainsKeywordsPredicate;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.testutil.EditContactDescriptorBuilder;
import seedu.ddd.testutil.EditVendorDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Command flags
    public static final String CLIENT_FLAG = CliFlags.FLAG_CLIENT.getPrefix();
    public static final String VENDOR_FLAG = CliFlags.FLAG_VENDOR.getPrefix();

    public static final EditContactDescriptor VALID_EDIT_CONTACT_DESCRIPTOR;
    public static final EditVendorDescriptor VALID_EDIT_VENDOR_DESCRIPTOR;

    static {
        VALID_EDIT_CONTACT_DESCRIPTOR = new EditContactDescriptorBuilder()
                .withName(VALID_CLIENT_NAME)
                .withPhone(VALID_CLIENT_PHONE)
                .withEmail(VALID_CLIENT_EMAIL)
                .withAddress(VALID_CLIENT_ADDRESS)
                .withTags(VALID_TAG_1)
                .build();
        VALID_EDIT_VENDOR_DESCRIPTOR = new EditVendorDescriptorBuilder()
                .withName(VALID_VENDOR_NAME)
                .withPhone(VALID_VENDOR_PHONE)
                .withEmail(VALID_VENDOR_EMAIL)
                .withAddress(VALID_VENDOR_ADDRESS)
                .withService(VALID_VENDOR_SERVICE_1)
                .withTags(VALID_TAG_1, VALID_TAG_2)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(
        Command command,
        Model actualModel,
        CommandResult expectedCommandResult,
        Model expectedModel
    ) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(
        Command command,
        Model actualModel,
        String expectedMessage,
        Model expectedModel
    ) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Contact> expectedFilteredList = new ArrayList<>(actualModel.getFilteredContactList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredContactList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showContactAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredContactList().size());

        Contact contact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        final String[] splitName = contact.getName().fullName.split("\\s+");
        model.updateFilteredContactList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredContactList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().fullName.split("\\s+");
        model.updateFilteredContactList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredContactList().size());
    }

}
