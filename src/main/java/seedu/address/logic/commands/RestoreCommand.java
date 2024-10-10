package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

public class RestoreCommand extends Command{

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_RESTORE_PERSON_SUCCESS = "Restored Person: %1$s";

    public static final String MESSAGE_NOTHING_TO_RESTORE = "Person has to be deleted previously to be restored";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.checkRestorable()) {
            throw new CommandException(MESSAGE_NOTHING_TO_RESTORE);
        }

        if (model.hasPerson(model.getLastDeletedPerson())) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(model.getLastDeletedPerson());
        model.makeNotRestorable();
        return new CommandResult(String.format(MESSAGE_RESTORE_PERSON_SUCCESS, Messages.format(model.getLastDeletedPerson())));

    }

}
