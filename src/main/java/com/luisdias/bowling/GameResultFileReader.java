package com.luisdias.bowling;

import java.io.File;
import java.util.function.Predicate;

public interface GameResultFileReader {

    boolean read(File file, Predicate<String> linePredicate);
}
