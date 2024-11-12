package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.ui.DisplayType;

/**
 * Adds an employee to the address book.
 */
public class AddProjectCommand extends Command {

    public static final String COMMAND_WORD = "addproject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the address book. "
            + "Parameters: "
            + PREFIX_PROJECT_ID + "PROJECT ID "
            + PREFIX_PROJECT_NAME + "PROJECT NAME "
            + PREFIX_SKILL + "PROJECT SKILL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT_ID + "123 "
            + PREFIX_PROJECT_NAME + "Project Alpha "
            + PREFIX_SKILL + "Cybersecurity "
            + PREFIX_SKILL + "Java";

    public static final String MESSAGE_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the address book."
            + " The project id must be unique.";

    private final Project toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Employee}
     */
    public AddProjectCommand(Project project) {
        requireNonNull(project);
        toAdd = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasProject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.addProject(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), DisplayType.PROJECT_LIST);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddProjectCommand)) {
            return false;
        }

        AddProjectCommand otherAddProjectCommand = (AddProjectCommand) other;
        return toAdd.equals(otherAddProjectCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
