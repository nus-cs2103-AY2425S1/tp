package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGuests.getTypicalAddressBookWithGuests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Guest;
import seedu.address.testutil.GuestBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddGuestCommand}.
 */
public class AddGuestCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithGuests(), new UserPrefs());
    }

    @Test
    public void execute_newGuest_success() {
        Guest validGuest = new GuestBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validGuest);

        assertCommandSuccess(new AddGuestCommand(validGuest), model,
                String.format(AddGuestCommand.MESSAGE_SUCCESS, Messages.format(validGuest)),
                expectedModel);
    }

    @Test
    public void execute_duplicateGuest_throwsCommandException() {
        Guest guestInList = (Guest) model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddGuestCommand(guestInList), model,
                AddGuestCommand.MESSAGE_DUPLICATE_GUEST);
    }

}
