package se.anders_raberg.adventofcode2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.apache.commons.math3.util.Pair;

public class Day02 {
    private static final Logger LOGGER = Logger.getLogger(Day02.class.getName());

    private Day02() {
    }

    public static void run() throws IOException {
        String input = new String(Files.readAllBytes(Paths.get("inputs/input2.txt"))).trim();
        List<String> asList = Arrays.asList(input.split(","));

        long sumPart1 = asList.stream() //
                .map(Day02::parseRange) //
                .mapToLong(r -> sumInvalidsInRange(r, Day02::isInvalidPart1)) //
                .sum();

        long sumPart2 = asList.stream() //
                .map(Day02::parseRange) //
                .mapToLong(r -> sumInvalidsInRange(r, Day02::isInvalidPart2)) //
                .sum();

        LOGGER.info(() -> String.format("Part 1: %d", sumPart1));
        LOGGER.info(() -> String.format("Part 2: %d", sumPart2));
    }

    private static Pair<Long, Long> parseRange(String rangeStr) {
        String[] split = rangeStr.split("-");
        return new Pair<>(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

    private static long sumInvalidsInRange(Pair<Long, Long> range, Predicate<String> invalidCheck) {
        return LongStream.rangeClosed(range.getFirst(), range.getSecond()) //
                .boxed() //
                .map(String::valueOf) //
                .filter(invalidCheck) //
                .mapToLong(Long::parseLong) //
                .sum();
    }

    private static boolean isInvalidPart1(String id) {
        int mid = id.length() / 2;
        return id.substring(0, mid).equals(id.substring(mid));
    }

    private static boolean isInvalidPart2(String id) {
        for (int s = id.length() - 1; s > 0; s--) {
            int size = s;
            if (IntStream.range(0, id.length()) //
                    .filter(i -> i % size == 0) //
                    .mapToObj(i -> id.substring(i, Math.min(i + size, id.length()))) //
                    .distinct() //
                    .count() == 1) {
                return true;
            }
        }

        return false;
    }
}