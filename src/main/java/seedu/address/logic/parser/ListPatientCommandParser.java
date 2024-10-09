package seedu.address.logic.parser;

import seedu.address.logic.commands.ListPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListPatientCommand object
 */
public class ListPatientCommandParser implements Parser<ListPatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListPatientCommand
     * and returns a ListPatientCommand object for execution.
     * Since list-patient does not take any arguments, this method simply returns a new ListPatientCommand object.
     *
     * @throws ParseException if the user input contains any arguments (arguments are not expected).
     */
    @Override
    public ListPatientCommand parse(String args) throws ParseException {
        return new ListPatientCommand();
    }
}
