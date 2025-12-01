package se.anders_raberg.adventofcode2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class Day01 {
    private static final int DIAL_START = 50;
    private static final int DIAL_SIZE = 100;
    private static final Logger LOGGER = Logger.getLogger(Day01.class.getName());

    private Day01() {
    }

    public static void run() throws IOException {
        List<Integer> rotations = Files.readAllLines(Paths.get("inputs/input1.txt")).stream().map(Day01::parseRotation)
                .toList();

        int pointer = DIAL_START;
        int passCounter = 0;
        int stopCounter = 0;

        for (int r : rotations) {
            for (int i = 0; i < Math.abs(r); i++) {
                pointer = (pointer + Integer.signum(r)) % DIAL_SIZE;
                if (pointer == 0) {
                    passCounter++;
                }
            }
            if (pointer == 0) {
                stopCounter++;
            }
        }

        LOGGER.info(String.format("Part 1: %d", stopCounter));
        LOGGER.info(String.format("Part 2: %d", passCounter));
    }

    private static int parseRotation(String rotation) {
        int sign = rotation.charAt(0) == 'L' ? -1 : 1;
        int value = Integer.parseInt(rotation.substring(1));
        return sign * value;
    }

}