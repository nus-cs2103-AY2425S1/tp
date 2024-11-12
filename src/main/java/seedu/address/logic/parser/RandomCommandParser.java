package seedu.address.logic.parser;

import seedu.address.logic.commands.RandomCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a RandomCommand object.
 * <p>
 * This class implements the {@link Parser} interface and is responsible for
 * interpreting the user input specific to random commands. It validates the
 * input format and ensures that it complies with the expected structure.
 * </p>
 */
public class RandomCommandParser implements Parser<RandomCommand> {
    private static final String WARNING_PARAMETERS_SUPPLIED = "Additional parameters supplied have been ignored";
    /**
     * Parses user input to create a RandomCommand object.
     * <p>
     * This class implements the {@link Parser} interface and is responsible for
     * interpreting the user input specific to random commands. It validates the
     * input format and ensures that it complies with the expected structure.
     * </p>
     *
     */
    public RandomCommand parse(String userInput) throws ParseException {
        String[] words = userInput.split(" ");
        if (words.length != 1) {
            return new RandomCommand(WARNING_PARAMETERS_SUPPLIED);
        } else {
            return new RandomCommand();
        }
    }

}
