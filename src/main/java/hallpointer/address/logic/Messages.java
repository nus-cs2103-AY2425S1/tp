package hallpointer.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hallpointer.address.logic.parser.Prefix;
import hallpointer.address.model.member.Member;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX = "The member index provided is invalid";
    public static final String MESSAGE_MEMBERS_LISTED_OVERVIEW = "%1$d members listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code member} for display to the user.
     */
    public static String format(Member member) {
        final StringBuilder builder = new StringBuilder();
        builder.append(member.getName())
                .append("; Phone: ")
                .append(member.getPhone())
                .append("; Email: ")
                .append(member.getEmail())
                .append("; Room: ")
                .append(member.getRoom())
                .append("; Tags: ");
        member.getTags().forEach(builder::append);
        return builder.toString();
    }

}
