package com.luisdias.bowling.impl;

import com.luisdias.bowling.ResultHeaderProvider;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.luisdias.bowling.helper.StringBuilderHelper.appendIfFalse;

public class BowlingHeaderProvider implements ResultHeaderProvider {

    private static final int MAX_FRAMES = 10;

    @Override
    public List<String> getHeader() {
        StringBuilder frameSb = new StringBuilder("Frame\t\t");
        IntStream.range(1, MAX_FRAMES+1)
            .forEachOrdered(i -> {
                frameSb.append(i);
                appendIfFalse(frameSb, "\t\t", i == MAX_FRAMES);
            });
        frameSb.append("\n");
        return Collections.singletonList(frameSb.toString());
    }
}
