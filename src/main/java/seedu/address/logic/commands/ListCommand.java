package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String LONG_COMMAND_WORD = ":list";
    public static final String SHORT_COMMAND_WORD = ":ls";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final List<String> INVALID_VARIANTS = Arrays.asList("list", "ls", "show", ":show");


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
