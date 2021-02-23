package com.luisdias.bowling.impl;

import com.luisdias.bowling.ResultHeaderProvider;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BowlingHeaderProvider implements ResultHeaderProvider {

    private static final int MAX_FRAMES = 10;

    @Override
    public List<String> getHeader() {
        String headerString = IntStream.range(1, MAX_FRAMES+1)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining("\t\t"));
        String frameSb = "Frame\t\t" + headerString + "\n";
        return Collections.singletonList(frameSb);
    }
}
