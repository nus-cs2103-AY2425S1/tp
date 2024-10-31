package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 *  View a client details, such as name, phone, email, address, condition and schedule in a separate popup window,
 *  given that the client with the input name exists
 */
public class ViewClientCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View a client's information, including name "
            + "phone number, email, address, condition, appointments, reminder and payment history\n"
            + "Parameters: [NAME]\n"
            + "Example: " + COMMAND_WORD + " John Doe\n";
    private final Name clientName;

    public ViewClientCommand(Name clientName) {
        this.clientName = clientName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> clientList = model.getFilteredPersonList();
        if (clientList.stream().noneMatch(person -> person.getName().equals(this.clientName))) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME_DISPLAYED);
        }
        String showClientViewMessage = clientName + "'s Client tab displayed!";
        return new CommandResult(showClientViewMessage, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewClientCommand)) {
            return false;
        }

        ViewClientCommand otherViewClientCommand = (ViewClientCommand) other;
        return clientName.equals(otherViewClientCommand.clientName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toShowClient", clientName)
                .toString();
    }
}
