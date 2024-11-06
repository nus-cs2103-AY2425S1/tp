package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOT_MONTHPAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonPredicateBuilder;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CLASSID, PREFIX_MONTHPAID,
                PREFIX_NOT_MONTHPAID, PREFIX_TAG, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_FEES);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CLASSID,
                PREFIX_MONTHPAID, PREFIX_NOT_MONTHPAID, PREFIX_TAG,
                PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_FEES);

        PersonPredicateBuilder personPredicateBuilder = new PersonPredicateBuilder();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            personPredicateBuilder = findNameKeywords(argMultimap, personPredicateBuilder);
        }


        if (argMultimap.getValue(PREFIX_CLASSID).isPresent()) {
            personPredicateBuilder = findClassIdKeywords(argMultimap, personPredicateBuilder);
        }

        if (argMultimap.getValue(PREFIX_MONTHPAID).isPresent()) {
            personPredicateBuilder = findMonthPaidKeywords(argMultimap, personPredicateBuilder);
        }

        if (argMultimap.getValue(PREFIX_NOT_MONTHPAID).isPresent()) {
            personPredicateBuilder = findNotMonthPaidKeywords(argMultimap, personPredicateBuilder);

        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            personPredicateBuilder = findTagsKeywords(argMultimap, personPredicateBuilder);

        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            personPredicateBuilder = findEmailKeywords(argMultimap, personPredicateBuilder);
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            personPredicateBuilder = findPhoneKeywords(argMultimap, personPredicateBuilder);
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            personPredicateBuilder = findAddressKeywords(argMultimap, personPredicateBuilder);
        }

        if (argMultimap.getValue(PREFIX_FEES).isPresent()) {
            personPredicateBuilder = findFeesKeywords(argMultimap, personPredicateBuilder);
        }

        if (personPredicateBuilder.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.NO_SEARCH_FIELDS_PROVIDED));
        }

        return new FindCommand(personPredicateBuilder);
    }

    private PersonPredicateBuilder findNameKeywords(ArgumentMultimap argMultimap,
                                                    PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
        if (nameKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withNameKeywords(Arrays.asList(nameKeywords));
    }

    private PersonPredicateBuilder findClassIdKeywords(ArgumentMultimap argMultimap,
                                                       PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] classIdKeywords = argMultimap.getValue(PREFIX_CLASSID).get().split("\\s+");
        if (classIdKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withClassIdKeywords(Arrays.asList(classIdKeywords));
    }

    private PersonPredicateBuilder findMonthPaidKeywords(ArgumentMultimap argMultimap,
                                                         PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] monthPaidKeywords = argMultimap.getValue(PREFIX_MONTHPAID).get().split("\\s+");
        if (monthPaidKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withMonthPaidKeywords(Arrays.asList(monthPaidKeywords));
    }

    private PersonPredicateBuilder findNotMonthPaidKeywords(ArgumentMultimap argMultimap,
                                                            PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] notMonthPaidKeywords = argMultimap.getValue(PREFIX_NOT_MONTHPAID).get().split("\\s+");
        if (notMonthPaidKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withNotMonthPaidKeywords(Arrays.asList(notMonthPaidKeywords));
    }

    private PersonPredicateBuilder findTagsKeywords(ArgumentMultimap argMultimap,
                                                    PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] tagKeywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
        if (tagKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withTagsKeywords(Arrays.asList(tagKeywords));
    }

    private PersonPredicateBuilder findEmailKeywords(ArgumentMultimap argMultimap,
                                                     PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
        if (emailKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withEmailKeywords(Arrays.asList(emailKeywords));
    }

    private PersonPredicateBuilder findPhoneKeywords(ArgumentMultimap argMultimap,
                                                     PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] phoneKeywords = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");
        if (phoneKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withPhoneKeywords(Arrays.asList(phoneKeywords));
    }

    private PersonPredicateBuilder findAddressKeywords(ArgumentMultimap argMultimap,
                                                       PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] addressKeywords = argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+");
        if (addressKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withAddressKeywords(Arrays.asList(addressKeywords));
    }

    private PersonPredicateBuilder findFeesKeywords(ArgumentMultimap argMultimap,
                                                    PersonPredicateBuilder personPredicateBuilder)
            throws ParseException {
        String[] feesKeywords = argMultimap.getValue(PREFIX_FEES).get().split("\\s+");
        if (feesKeywords[0].isEmpty()) {
            throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
        }
        return personPredicateBuilder.withFeesKeywords(Arrays.asList(feesKeywords));
    }



}
