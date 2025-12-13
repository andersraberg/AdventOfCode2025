package se.anders_raberg.adventofcode2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

public class Day05 {
    private static final Logger LOGGER = Logger.getLogger(Day05.class.getName());
    private static final Pattern PATTERN_RULE = Pattern.compile("(\\d+)-(\\d+)");

    private Day05() {
    }

    public static void run() throws IOException {
        String[] fileSections = new String(Files.readAllBytes(Paths.get("inputs/input5.txt"))).split("\n\n");
        RangeSet<Long> rangeSet = TreeRangeSet
                .create(Arrays.stream(fileSections[0].split("\n")).map(Day05::parseRange).toList());

        List<Long> ingredients = Arrays.stream(fileSections[1].split("\n")).map(Long::parseLong).toList();

        long count = ingredients.stream() //
                .filter(rangeSet::contains) //
                .count();

        LOGGER.info(() -> String.format("Part 1: %d", count));

        long sum = rangeSet.asRanges().stream().mapToLong(r -> r.upperEndpoint() - r.lowerEndpoint() + 1).sum();
        LOGGER.info(() -> String.format("Part 2: %d", sum));
    }

    private static Range<Long> parseRange(String str) {
        Matcher m = PATTERN_RULE.matcher(str);
        if (m.matches()) {
            return Range.closed(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)));
        }
        throw new IllegalArgumentException(str);
    }

}