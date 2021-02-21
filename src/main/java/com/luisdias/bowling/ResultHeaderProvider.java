package com.luisdias.bowling;

import java.util.List;

public interface ResultHeaderProvider {

    /**
     * Gets the lines representing the header of the results
     * @return A list of lines (strings)
     */
    List<String> getHeader();
}
