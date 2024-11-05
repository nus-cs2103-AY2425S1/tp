package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;

/**
 * View all information of specified contact
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Viewing contact now...";

    public static final String MESSAGE_NO_SUCH_TELEGRAM =
            "There is no one in your address book with the telegram handle: @";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds one contact whose telegram handle "
            + "matches the specified telegram handle (case-insensitive)\n"
            + "and displays all the contact's information.\n"
            + "Parameters: t/TELEGRAM_HANDLE \n"
            + "Example: " + COMMAND_WORD + " t/TELEGRAM_HANDLE";

    private final String tele;

    public ViewCommand(String tele) {
        this.tele = tele.toLowerCase();
    }

    /**
     * Checks if telegram handle for this ViewCommand instance is at least 5
     * characters long and that it contains no illegal characters. Otherwise
     * throws relevant exception
     * @throws CommandException
     */
    public void isValidHandle() throws CommandException {
        if (!Telegram.isValidTelegramLength(this.tele)) {
            throw new CommandException(Telegram.LENGTH_CONSTRAINTS);
        } else if (!Telegram.isValidTelegram(this.tele)) {
            throw new CommandException(Telegram.MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        isValidHandle();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> p = lastShownList.stream().filter(person ->
                person.getTelegram().value.toLowerCase().equals(this.tele)).toList();
        if (p.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TELEGRAM + this.tele);
        }
        Person person = p.get(0);
        return new CommandResult(MESSAGE_SUCCESS, true, person);
    }

}
