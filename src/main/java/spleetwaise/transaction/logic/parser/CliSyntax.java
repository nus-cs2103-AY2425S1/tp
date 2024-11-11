package spleetwaise.transaction.logic.parser;

import spleetwaise.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for transaction commands */
    public static final Prefix PREFIX_AMOUNT = new Prefix("amt/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/");
    public static final Prefix PREFIX_DATE = new Prefix("date/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("cat/");
    public static final Prefix PREFIX_STATUS = new Prefix("status/");
    public static final Prefix PREFIX_AMOUNT_SIGN = new Prefix("amtsign/");
}
