package se.anders_raberg.adventofcode2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.logging.Logger;
import java.util.stream.LongStream;

public class Day06 {
    private static final Logger LOGGER = Logger.getLogger(Day06.class.getName());

    private Day06() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input6.txt"));

        List<BinaryOperator<Long>> operators = Arrays.stream(lines.getLast().trim().split("\s+"))
                .map(Day06::parseOperator) //
                .toList();

        // Part 1
        //
        List<List<Long>> list1 = lines.subList(0, lines.size() - 1).stream()
                .map(l -> Arrays.stream(l.trim().split("\s+")).map(Long::parseLong).toList()).toList();

        List<List<Long>> transpose1 = transpose(list1);

        long sum1 = LongStream.range(0, transpose1.size())
                .map(i -> transpose1.get((int) i).stream().reduce(operators.get((int) i)).orElseThrow()).sum();

        LOGGER.info(() -> String.format("Part 1: %d", sum1));

        // Part 2
        //
        List<List<String>> list2 = lines.subList(0, lines.size() - 1).stream()
                .map(l -> Arrays.stream(l.split("")).toList()).toList();

        List<List<String>> transpose2 = split(transpose(list2).stream().map(s -> String.join("", s)).toList());

        long sum2 = LongStream.range(0, transpose2.size())
                .map(i -> parseAndReduce(transpose2.get((int) i), operators.get((int) i))).sum();

        LOGGER.info(() -> String.format("Part 2: %d", sum2));
    }

    private static long parseAndReduce(List<String> input, BinaryOperator<Long> op) {
        return input.stream().map(String::trim).map(Long::parseLong).reduce(op).orElseThrow();
    }

    private static List<List<String>> split(List<String> input) {
        List<List<String>> result = new ArrayList<>();
        List<String> current = new ArrayList<>();

        for (String s : input) {
            if (s.isBlank()) {
                if (!current.isEmpty()) {
                    result.add(current);
                    current = new ArrayList<>();
                }
            } else {
                current.add(s);
            }
        }

        if (!current.isEmpty()) {
            result.add(current);
        }

        return result;
    }

    private static BinaryOperator<Long> parseOperator(String op) {
        return switch (op.trim()) {
            case "+" -> Math::addExact;
            case "*" -> Math::multiplyExact;
            default -> throw new IllegalArgumentException("Unexpected value: " + op);
        };
    }

    private static <T> List<List<T>> transpose(List<List<T>> matrix) {
        if (matrix.isEmpty()) {
            return List.of();
        }

        int rows = matrix.size();
        int cols = matrix.getFirst().size();

        List<List<T>> result = new ArrayList<>(cols);

        for (int c = 0; c < cols; c++) {
            List<T> newRow = new ArrayList<>(rows);
            for (int r = 0; r < rows; r++) {
                newRow.add(matrix.get(r).get(c));
            }
            result.add(newRow);
        }

        return result;
    }

}