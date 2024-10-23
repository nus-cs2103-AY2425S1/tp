package seedu.address.commons.core;

public enum SortOrder {
    ASC("asc", "ascending"),
    DESC("desc", "descending"),
    OG("og", "original");

    public static final String MESSAGE_CONSTRAINTS =
            "Sort order parameter can only be asc or desc or og.";

    public final String keyword;
    private final String stringRep;

    private SortOrder(String keyword, String stringRep) {
        this.keyword = keyword;
        this.stringRep = stringRep;
    }

    @Override
    public String toString() {
        return this.stringRep;
    }
}
