package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.util.DeliveryAction;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean isShowHelp;

    /** The person to inspect (only if isInspect else null) **/
    private final Person person;

    /** The application should exit. */
    private final boolean isExit;

    /** Inspection page should be showed **/
    private final boolean isInspect;

    /** Main page should be showed **/
    private final boolean isList;

    private DeliveryAction deliveryAction = DeliveryAction.NONE;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, DeliveryAction deliveryAction) {
        this.feedbackToUser = feedbackToUser;
        this.person = null;
        this.isShowHelp = false;
        this.isExit = false;
        this.isInspect = false;
        this.isList = false;
        this.deliveryAction = deliveryAction;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Person person, boolean isShowHelp, boolean isExit, boolean isInspect,
                         boolean isList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = isShowHelp;
        this.person = person;
        this.isExit = isExit;
        this.isInspect = isInspect;
        this.isList = isList;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Person person, boolean isShowHelp, boolean isExit, boolean isInspect) {
        this(feedbackToUser, person, isShowHelp, isExit, isInspect, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, null, showHelp, exit, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isShowHelp() {
        return isShowHelp;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isInspect() {
        return isInspect;
    }

    public boolean isList() {
        return isList;
    }

    public boolean isDeliveryAdded() {
        return this.deliveryAction == DeliveryAction.ADD;
    }

    public boolean isDeliveryDeleted() {
        return this.deliveryAction == DeliveryAction.DELETE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && isShowHelp == otherCommandResult.isShowHelp
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowHelp, isExit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", isShowHelp)
                .add("person", person)
                .add("exit", isExit)
                .add("inspect", isInspect)
                .add("list", isList)
                .toString();
    }

}
