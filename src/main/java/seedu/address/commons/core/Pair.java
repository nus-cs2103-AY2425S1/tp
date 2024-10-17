package seedu.address.commons.core;

import seedu.address.commons.core.index.Index;

/**
 * Represents a pair of an {@code Index} and a {@code String}.
 * Immutable class used to store two associated objects together.
 */
public class Pair {
    private final Index first;
    private final String second;

    /**
     * Constructs a {@code Pair} with the given {@code Index} and {@code String}.
     *
     * @param i The {@code Index} part of the pair.
     * @param s The {@code String} part of the pair.
     */
    public Pair(Index i, String s) {
        first = i;
        second = s;
    }

    /**
     * Returns the {@code Index} part of the pair.
     *
     * @return The {@code Index} part of the pair.
     */
    public Index first() {
        return this.first;
    }

    /**
     * Returns the {@code String} part of the pair.
     *
     * @return The {@code String} part of the pair.
     */
    public String second() {
        return this.second;
    }
}
