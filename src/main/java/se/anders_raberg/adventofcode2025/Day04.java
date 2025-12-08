package se.anders_raberg.adventofcode2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

public class Day04 {
    private static final Logger LOGGER = Logger.getLogger(Day04.class.getName());
    private static final Map<Pos, String> GRID = new HashMap<>();

    public record Pos(int x, int y) {
        public Set<Pos> neighbors() {
            return Set.of( //
                    new Pos(x - 1, y - 1), //
                    new Pos(x, y - 1), //
                    new Pos(x + 1, y - 1), //
                    new Pos(x - 1, y), //
                    new Pos(x + 1, y), //
                    new Pos(x - 1, y + 1), //
                    new Pos(x, y + 1), //
                    new Pos(x + 1, y + 1));
        }
    }

    private Day04() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input4.txt"));
        for (int y = 0; y < lines.size(); y++) {
            String[] row = lines.get(y).trim().split("");
            for (int x = 0; x < row.length; x++) {
                GRID.put(new Pos(x, y), row[x]);
            }
        }

        LOGGER.info(() -> String.format("Part 1: %d", getRemovedRolls().size()));

        int count = 0;
        List<Pos> removedRolls;
        do {
            removedRolls = getRemovedRolls();
            count = count + removedRolls.size();
            removedRolls.forEach(p -> GRID.put(p, "."));

        } while (!removedRolls.isEmpty());

        LOGGER.info(String.format("Part 2: %d", count));

    }

    private static List<Pos> getRemovedRolls() {
        List<Pos> list = GRID.entrySet().stream().filter(e -> e.getValue().equals("@")).map(Entry::getKey).toList();
        return list.stream() //
                .filter(p -> p.neighbors().stream() //
                        .filter(n -> GRID.getOrDefault(n, "").equals("@")) //
                        .count() < 4) //
                .toList();
    }

}
