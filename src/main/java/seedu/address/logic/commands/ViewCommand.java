package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.ContactDisplay;
import seedu.address.ui.TestContactDisplay;

/**
 * Diplays the details of an existing person in the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the details of the contact identified "
            + "by the index number used in the displayed contact list. "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Displayed Person: %1$s";

    private final Index index;
    private final ContactDisplay contactDisplay;
    private final TestContactDisplay testContactDisplay;

    /**
     * @param index of the person in the filtered person list to edit
     * @param contactDisplay to display the person
     */
    public ViewCommand(Index index, ContactDisplay contactDisplay) {
        requireNonNull(index);
        this.index = index;
        this.contactDisplay = contactDisplay;
        this.testContactDisplay = new TestContactDisplay();
    }

    /**
     * @param index of the person in the filtered person list to edit
     */
    public ViewCommand(Index index, TestContactDisplay testContactDisplay) {
        requireNonNull(index);
        this.index = index;
        this.contactDisplay = null;
        this.testContactDisplay = testContactDisplay;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(index.getZeroBased());
        if (contactDisplay == null) {
            testContactDisplay.updateContactDetails(personToView);
        } else {
            contactDisplay.updateContactDetails(personToView);
        }

        return new CommandResult(String.format(MESSAGE_VIEW_PERSON_SUCCESS, Messages.format(personToView)),
        personToView);
    }
}
