package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.AppParameters;
import seedu.address.commons.core.Browser;
import seedu.address.commons.core.LogsCenter;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Launches the github repository of the user.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe";
    public static final String MISSING_PERSON_EXCEPTION = "The person you specified does not exist"
            + " in the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "%1s";

    public static final String MESSAGE_SUCCESS = "Browser launched successfully";

    private static final Logger logger = LogsCenter.getLogger(AppParameters.class);
    private static final String GITHUB_URL = "https://github.com/%1s/?tab=repositories";

    private Name toLaunch;
    private Browser browser;

    /**
     * Creates an GitHubCommand to launch the specified {@code Name} person's github repository
     */
    public GitHubCommand(Name person, Browser browser) {
        requireNonNull(person);
        toLaunch = person;
        this.browser = browser;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasName(toLaunch)) {
            logger.log(Level.WARNING, "User does not exist in KonTActs");
            throw new CommandException(String.format(MISSING_PERSON_EXCEPTION, toLaunch.toString()));
        }

        Github githubAccount = model.getGitHubUsername(toLaunch);
        String uriLink = String.format(GITHUB_URL, githubAccount.username);
        this.browser.launchUri(uriLink);

        logger.log(Level.INFO, "Browser successfully launched!");
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
