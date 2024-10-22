package keycontacts.logic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import keycontacts.logic.Messages;
import keycontacts.logic.parser.exceptions.ParseException;

public class ArgumentList {
    private final List<ArgumentToken> argumentList;

    public ArgumentList() {
        argumentList = new ArrayList<>();
    }

    public void addArgument(ArgumentToken argument) {
        argumentList.add(argument);
    }

    public Prefix getPrefix(int index) {
        return argumentList.get(index).getPrefix();
    }

    public String getValue(int index) {
        return argumentList.get(index).getValue();
    }

    public int size() {
        return argumentList.size();
    }

    public boolean anyPrefixesPresent(Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentList.stream()
                .anyMatch(argument -> argument.getPrefix().equals(prefix)));
    }

    public void verifyNoDuplicatePrefixesFor(Prefix... prefixes) throws ParseException {
        List<Prefix> duplicatedPrefixes = new ArrayList<>();
        for (Prefix prefix : prefixes) {
            long count = argumentList.stream()
                    .filter(argument -> argument.getPrefix().equals(prefix))
                    .count();
            if (count > 1) {
                duplicatedPrefixes.add(prefix);
            }
        }
        if (!duplicatedPrefixes.isEmpty()) {
            throw new ParseException(Messages.getErrorMessageForDuplicatePrefixes(duplicatedPrefixes.toArray(Prefix[]::new)));
        }
    }

}
