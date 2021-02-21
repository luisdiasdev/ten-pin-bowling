package com.luisdias.bowling;

import java.util.List;

public interface ResultOutputWriter {

    /**
     * Writes the list of result strings
     * to a given output.
     * @param result A list of ordered results
     * @return true if list is not empty and write was successful, false otherwise.
     */
    boolean write(List<String> result);
}
