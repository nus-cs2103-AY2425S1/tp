package seedu.edulog.logic.commands;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.model.Model.PREDICATE_SHOW_PAID_STUDENTS;
import static seedu.edulog.model.Model.PREDICATE_SHOW_UNPAID_STUDENTS;

/**
 * Calculate total revenue earned
 */
public class PaidRevenueCommand extends RevenueCommand {

    private final String OPTION = "paid";

    public PaidRevenueCommand() { }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        int result = model.getPaid();
        model.updateFilteredStudentList(PREDICATE_SHOW_PAID_STUDENTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, OPTION, result));
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof PaidRevenueCommand);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
