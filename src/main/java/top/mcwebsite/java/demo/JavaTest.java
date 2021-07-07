package top.mcwebsite.java.demo;

import java.io.File;
import java.util.Random;

public class JavaTest {
    public File test() {
        Random random = new Random(System.currentTimeMillis());
        if (random.nextBoolean()) {
            return new File(String.valueOf(random.nextInt()));
        } else {
            return null;
        }
    }
}
