package seedu.address.logic.parser;

import java.util.List;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PUBLIC_ADDRESS = new Prefix("pa/");
    public static final Prefix PREFIX_PUBLIC_ADDRESS_SUBSTRING = new Prefix("pas/");

    public static final Prefix PREFIX_PUBLIC_ADDRESS_LABEL = new Prefix("l/");
    public static final Prefix PREFIX_PUBLIC_ADDRESS_NETWORK = new Prefix("c/");

    public static List<Prefix> getAllPrefixes() {
        return List.of(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_PUBLIC_ADDRESS,
                PREFIX_PUBLIC_ADDRESS_SUBSTRING, PREFIX_PUBLIC_ADDRESS_LABEL, PREFIX_PUBLIC_ADDRESS_NETWORK);
    }

}
