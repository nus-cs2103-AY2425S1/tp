package seedu.academyassist.logic.commands;

import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SORT_PARAM;

import java.util.Objects;

import seedu.academyassist.commons.util.CollectionUtil;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.sort.SortParam;

/**
 * Sorts students based on name or subject.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts students based on name/subject/studentId/yearGroup. \n"
            + "Parameters: \n"
            + PREFIX_SORT_PARAM + "FIELD\n"
            + "Example: \n"
            + COMMAND_WORD + " "
            + PREFIX_SORT_PARAM + "name";

    public static final String MESSAGE_SUCCESS = "Sorted by: %1$s in ascending order";

    private SortParam sortParam;

    /**
     * Creates a SortCommand
     * @param sortParam SortParam.NAME or SortParam.CLASS or SortParam.ID or SortParam.YEARGROUP
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
        } else if (sortParam.toString().equals("subject")) {
            model.sortAcademyAssistBySubject();
        } else if (sortParam.toString().equals("studentId")) {
            model.sortAcademyAssistById();
        } else if (sortParam.toString().equals("yearGroup")) {
            model.sortAcademyAssistByYearGroup();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, sortParam.toString()));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return sortParam.toString().equals(otherSortCommand.sortParam.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortParam.toString());
    }
}
