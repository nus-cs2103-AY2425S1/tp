package keycontacts.logic.parser;

public class ArgumentToken {
    
    private Prefix prefix;
    private String value;

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
