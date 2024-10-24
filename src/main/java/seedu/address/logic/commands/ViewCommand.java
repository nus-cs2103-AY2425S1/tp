package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

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
            + "matches the specified telegram handle (case-insensitive) and displays a new page which "
            + "contains all the contact's information.\n"
            + "Parameters: t/TELEGRAM_HANDLE \n"
            + "Example: " + COMMAND_WORD + " t/TELEGRAM_HANDLE";

    private final String tele;

    public ViewCommand(String tele) {
        this.tele = tele.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> p = lastShownList.stream().filter(person ->
                person.getTelegram().value.toLowerCase().equals(this.tele)).toList();
        if (p.isEmpty()) {
            throw new CommandException(MESSAGE_NO_SUCH_TELEGRAM + this.tele);
        }
        Person person = p.get(0);
        return new CommandResult(MESSAGE_SUCCESS, true, person);
    }

}
