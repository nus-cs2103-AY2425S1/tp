package seedu.ddd.logic.commands.add;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_VENDOR_ID;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DESCRIPTION;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_NAME;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE;
import static seedu.ddd.testutil.event.TypicalEvents.VALID_EVENT;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_A;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_B;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.Command;
import seedu.ddd.logic.commands.CommandTestUtil;
import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.testutil.contact.ClientBuilder;
import seedu.ddd.testutil.contact.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() {
        // ensure event contacts are in the model
        model.addContact(VALID_CLIENT);
        model.addContact(VALID_VENDOR);

        Command command = new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE,
            DEFAULT_EVENT_ID
        );

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEvent(VALID_EVENT);

        CommandTestUtil.assertCommandSuccess(command, model,
                String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.format(VALID_EVENT)),
                expectedModel);
        assert model.hasEvent(VALID_EVENT);
    }


    @Test
    public void execute_nullModel_throwsNullPointerException() {
        // ensure event contacts are in the model
        model.addContact(VALID_CLIENT);
        model.addContact(VALID_VENDOR);

        Command command = new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE,
            DEFAULT_EVENT_ID
        );

        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_invalidContacts_throwsCommandException() {
        assert !model.hasClientId(DEFAULT_CLIENT_ID);
        assert !model.hasVendorId(DEFAULT_VENDOR_ID);

        // this maps to VALID_CLIENT and VALID_VENDOR
        Command command = new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE,
            DEFAULT_EVENT_ID
        );

        String expectedMessage = String.format(AddEventCommand.MESSAGE_INVALID_CONTACT_IDS,
                Set.of(DEFAULT_CLIENT_ID), Set.of(DEFAULT_VENDOR_ID));
        assertCommandFailure(command, model, expectedMessage);

        // copy VALID_CLIENT without event associations
        Client client = new ClientBuilder(VALID_CLIENT).build();
        model.addContact(client);
        assert model.hasClientId(DEFAULT_CLIENT_ID);
        assert !model.hasVendorId(DEFAULT_VENDOR_ID);
        expectedMessage = String.format(AddEventCommand.MESSAGE_INVALID_VENDOR_IDS, Set.of(DEFAULT_VENDOR_ID));
        assertCommandFailure(command, model, expectedMessage);

        model.deleteContact(client);
        // copy VALID_VENDOR without event associations
        Vendor vendor = new VendorBuilder(VALID_VENDOR).build();
        model.addContact(vendor);
        assert !model.hasClientId(DEFAULT_CLIENT_ID);
        assert model.hasVendorId(DEFAULT_VENDOR_ID);
        expectedMessage = String.format(AddEventCommand.MESSAGE_INVALID_CLIENT_IDS, Set.of(DEFAULT_CLIENT_ID));
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        assert model.hasEvent(WEDDING_A);

        // exact copy of WEDDING_A
        Command command = new AddEventCommand(
            WEDDING_A.getName(),
            WEDDING_A.getDescription(),
            WEDDING_A.getDate(),
            new HashSet<>(WEDDING_A.getClientIds()),
            new HashSet<>(WEDDING_A.getVendorIds()),
            WEDDING_A.getEventId()
        );

        assertCommandFailure(command, model, AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        // only copy name
        command = new AddEventCommand(
            WEDDING_A.getName(),
            WEDDING_B.getDescription(),
            WEDDING_B.getDate(),
            new HashSet<>(WEDDING_B.getClientIds()),
            new HashSet<>(WEDDING_B.getVendorIds()),
            WEDDING_B.getEventId()
        );

        assertCommandFailure(command, model, AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void toStringMethod() {
        Command command = new AddEventCommand(
            DEFAULT_EVENT_NAME,
            DEFAULT_EVENT_DESCRIPTION,
            DEFAULT_EVENT_DATE,
            DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE,
            DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE,
            DEFAULT_EVENT_ID
        );
        String expectedEventString = AddEventCommand.class.getCanonicalName()
                + "{name=" + DEFAULT_EVENT_NAME
                + ", description=" + DEFAULT_EVENT_DESCRIPTION
                + ", date=" + DEFAULT_EVENT_DATE
                + ", clientIds=" + DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE
                + ", vendorIds=" + DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE
                + ", id=" + DEFAULT_EVENT_ID + "}";
        assertEquals(expectedEventString, command.toString());
    }

}
