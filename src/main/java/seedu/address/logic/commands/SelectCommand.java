package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SelectPredicate;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects the persons based on the indexes "
            + "Parameters: INDEX [MORE_INDEXES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2 7";

    private SelectPredicate selectPredicate;

    private List<Person> persons = new ArrayList<>();

    private final List<Index> indexes;

    public SelectCommand(List<Index> indexes) {
        this.indexes = indexes;
    }

    //    WIP
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Index index: this.indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }


            persons.add(lastShownList.get(index.getZeroBased()));
        }

        selectPredicate = new SelectPredicate(persons);
        model.updateFilteredPersonList(selectPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    //    WIP
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SelectCommand)) {
            return false;
        }

        SelectCommand otherSelectCommand = (SelectCommand) other;
        //        TO BE CHANGED!!
        return true;
    }
}
