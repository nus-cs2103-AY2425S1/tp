package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Log;
import seedu.address.model.person.Person;

public class LogCommand extends Command{
    public static final String COMMAND_WORD = "log";
    public static final String MESSAGE_SUCCESS = "Log added!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs information of a patient.\n"
            + "Parameters: INDEX (must be a positive integer), " +
            "TIMESTAMP (in the format DD-MM-YYYY HH:MM)," +
            "INFO (non-empty)\n" +
            "\n"
            + "Example: " + COMMAND_WORD + " 1 25-12-2024 14:30 Attended appointment";

    private final Index index;
    private final Log log;

    public LogCommand(Index targetIndex, Log log) {
        this.index = targetIndex;
        this.log = log;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToLog = lastShownList.get(index.getZeroBased());
        personToLog.addLogEntry(log);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogCommand // instanceof handles nulls
                && index.equals(((LogCommand) other).index)
                && log.equals(((LogCommand) other).log));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("log", log)
                .toString();
    }
}
