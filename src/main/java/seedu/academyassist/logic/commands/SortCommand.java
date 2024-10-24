package seedu.academyassist.logic.commands;

import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SORT_PARAM;

import seedu.academyassist.commons.util.CollectionUtil;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.sort.SortParam;

/**
 * Sorts students based on name or class.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students based on name/class/studentId. "
            + "Parameters: "
            + PREFIX_SORT_PARAM + "'name' or 'class' or 'studentId'\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORT_PARAM + "name";

    public static final String MESSAGE_SUCCESS = "Sorted by: %1$s";

    private SortParam sortParam;

    /**
     * Creates a SortCommand
     * @param sortParam SortParam.NAME or SortParam.CLASS or SortParam.ID
     */
    public SortCommand(SortParam sortParam) {
        CollectionUtil.requireAllNonNull(sortParam);
        this.sortParam = sortParam;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //sort the contact book
        if (sortParam.toString().equals("name")) {
            model.sortAcademyAssistByName();
        } else if (sortParam.toString().equals("class")) {
            model.sortAcademyAssistByClass();
        } else if (sortParam.toString().equals("studentId")) {
            model.sortAcademyAssistById();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, sortParam.toString()));

    }
}
