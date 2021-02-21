package com.luisdias.bowling.it;

import java.io.File;
import java.nio.file.Paths;

public class TestFileHelper {

    private static final String RESOURCES_PATH = "src/it/resources";

    public static String getFilePathFromResources(String fileName) {
        File file = new File(RESOURCES_PATH);
        String absolutePath = file.getAbsolutePath();
        return Paths.get(absolutePath, fileName).toString();
    }

}
