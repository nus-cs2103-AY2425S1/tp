package hallpointer.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import hallpointer.address.model.Model;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.SessionContainsKeywordsPredicate;
import hallpointer.address.model.session.Session;

/**
 * Finds and lists all sessions in the address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindSessionCommand extends Command {

    public static final String COMMAND_WORD = "find_sessions";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all sessions whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " rehearsal match";

    public static final String MESSAGE_SUCCESS = "Matching sessions displayed: \n%1$s";
    public static final String MESSAGE_NO_MATCHES = "Error: No sessions found matching the keywords.";
    private final SessionContainsKeywordsPredicate predicate;

    public FindSessionCommand(SessionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMemberList(predicate);

        List<Member> filteredMembers = model.getFilteredMemberList();
        List<Session> matchingSessions = filteredMembers.stream()
                .flatMap(member -> member.getSessions().stream())
                .filter(session -> predicate.test(session))
                .toList();

        if (matchingSessions.isEmpty()) {
            return new CommandResult(MESSAGE_NO_MATCHES);
        }

        String resultMessage = matchingSessions.stream().distinct()
                .map(session -> session.getSessionName().toString())
                .collect(Collectors.joining("\n"));
        return new CommandResult(String.format(MESSAGE_SUCCESS, resultMessage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindSessionCommand // instanceof handles nulls
                && predicate.equals(((FindSessionCommand) other).predicate)); // state check
    }
}
