package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyPawPatrol;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns PawPatrol.
     *
     * @see seedu.address.model.Model#getPawPatrol()
     */
    ReadOnlyPawPatrol getPawPatrol();

    /** Returns an unmodifiable view of the filtered list of owners */
    ObservableList<Owner> getFilteredOwnerList();

    /** Returns an unmodifiable view of the filtered list of pets */
    ObservableList<Pet> getFilteredPetList();

    /** Returns an unmodifiable view of the filtered list of links */
    ObservableList<Link> getFilteredLinkList();

    /**
     * Returns the user prefs' PawPatrol file path.
     */
    Path getPawPatrolFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
