package keycontacts.logic.parser;

/**
 * Represents an argument token, which includes the prefix and value of the argument.
 */
public class ArgumentToken {

    private Prefix prefix;
    private String value;

    /**
     * Creates an ArgumentToken object with the specified prefix and value.
     * @param prefix
     * @param value
     */
    public ArgumentToken(Prefix prefix, String value) {
        this.prefix = prefix;
        this.value = value;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public String getValue() {
        return value;
    }
}
