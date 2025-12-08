package se.anders_raberg.adventofcode2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Day03 {
    private static final Logger LOGGER = Logger.getLogger(Day03.class.getName());

    private Day03() {
    }

    public static void run() throws IOException {
        List<String> banks = Files.readAllLines(Paths.get("inputs/input3.txt"));

        LOGGER.info(() -> "Part 1: " + calculateJoltage(banks, 2));
        LOGGER.info(() -> "Part 2: " + calculateJoltage(banks, 12));
    }

    private static long calculateJoltage(List<String> banks, int digits) {
        return banks.stream() //
                .map(Day03::stringToLongList) //
                .map(bank -> findMaxList(bank, digits - 1)) //
                .mapToLong(Day03::listToLong) //
                .sum();
    }

    private static List<Long> findMaxList(List<Long> bank, int maxDigitsToSearch) {
        List<Long> res = new ArrayList<>();
        List<Long> listToSearch = bank.subList(0, bank.size() - maxDigitsToSearch);
        Long firstMax = Collections.max(listToSearch);
        int indexOfFirstMax = listToSearch.indexOf(firstMax);

        res.add(firstMax);
        if (maxDigitsToSearch > 0) {
            res.addAll(findMaxList(bank.subList(indexOfFirstMax + 1, bank.size()), maxDigitsToSearch - 1));
        }
        return res;
    }

    private static long listToLong(List<Long> p) {
        return Long.parseLong(p.stream().map(v -> Long.toString(v)).reduce(String::concat).orElseThrow());
    }

    private static List<Long> stringToLongList(String str) {
        return Arrays.stream(str.split("")).map(Long::parseLong).toList();
    }
}