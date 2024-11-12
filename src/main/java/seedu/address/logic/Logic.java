package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.property.Property;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the BuyerList.
     *
     * @see seedu.address.model.Model#getBuyerList()
     */
    ReadOnlyBuyerList getBuyerList();

    /** Returns an unmodifiable view of the unfiltered list of buyers */
    ObservableList<Buyer> getUnfilteredBuyerList();

    /** Returns an unmodifiable view of the filtered list of buyers */
    ObservableList<Buyer> getFilteredBuyerList();

    /**
     * Returns the user prefs' buyer list file path.
     */
    Path getBuyerListFilePath();

    /**
     * Returns the MeetUpList.
     *
     * @see seedu.address.model.Model#getMeetUpList()
     */
    ReadOnlyMeetUpList getMeetUpList();

    /** Returns an unmodifiable view of the unfiltered list of meet-ups */
    ObservableList<MeetUp> getUnfilteredMeetUpList();

    /** Returns an unmodifiable view of the filtered list of meet ups */
    ObservableList<MeetUp> getFilteredMeetUpList();

    /**
     * Returns the user prefs' meet up list file path.
     */
    Path getMeetUpListFilePath();

    /**
     * Returns the MeetUpList.
     *
     * @see seedu.address.model.Model#getMeetUpList()
     */
    ReadOnlyPropertyList getPropertyList();

    /** Returns an unmodifiable view of the unfiltered list of properties */
    ObservableList<Property> getUnfilteredPropertyList();

    /** Returns an unmodifiable view of the filtered list of meet ups */
    ObservableList<Property> getFilteredPropertyList();

    /**
     * Returns the user prefs' meet up list file path.
     */
    Path getPropertyListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
