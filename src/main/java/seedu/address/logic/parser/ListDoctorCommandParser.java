package seedu.address.logic.parser;

import seedu.address.logic.commands.ListDoctorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListDoctorCommand object
 */
public class ListDoctorCommandParser implements Parser<ListDoctorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListDoctorCommand
     * and returns a ListDoctorCommand object for execution.
     * Since list-doctor does not take any arguments, this method simply returns a new ListDoctorCommand object.
     * 
     * @throws ParseException if the user input contains any arguments (arguments are not expected).
     */
    @Override
    public ListDoctorCommand parse(String args) throws ParseException {
        return new ListDoctorCommand();
    }
}
