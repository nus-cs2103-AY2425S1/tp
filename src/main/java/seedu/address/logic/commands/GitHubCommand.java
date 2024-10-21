package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;

/**
 * Launches a person's GitHub repository from the address book.
 */
public class GitHubCommand extends Command {
    public static final String COMMAND_WORD = "github";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Launches the github repository of the user. "
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";
    public static final String MISSING_PERSON_EXCEPTION = "The person you specified does not exist"
            + " in the address book."
            + "Parameters: "
            + PREFIX_NAME + "%1s";

    private static final String MESSAGE_SUCCESS = "Launching your browser...";

    private static final String NULL_GITHUB_USERNAME = "Github username specified for %1s is null.";

    private static final String GITHUB_URL = "https://github.com/%1s/?tab=repositories";

    private Name toLaunch;

    /**
     * Creates an GitHubCommand to launch the specified {@code Name} person's github repository
     */
    public GitHubCommand(Name person) {
        requireNonNull(person);
        toLaunch = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasName(toLaunch)) {
            throw new CommandException(String.format(MISSING_PERSON_EXCEPTION, toLaunch.toString()));
        }

        try {
            Github githubAccount = model.getGitHubUsername(toLaunch);
            String uriLink = String.format(GITHUB_URL, githubAccount.username);
            Desktop.getDesktop().browse(new URI(uriLink));
        } catch (IOException ioe) {
            throw new CommandException(String.format(NULL_GITHUB_USERNAME, toLaunch.toString()));
        } catch (URISyntaxException use) {
            throw new CommandException("");
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GitHubCommand)) {
            return false;
        }

        GitHubCommand otherGitHubCommand = (GitHubCommand) other;
        return toLaunch.equals(otherGitHubCommand.toLaunch);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toLaunch", toLaunch)
                .toString();
    }
}
