package game.storage;

import game.battle.BattleLog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BattleStorage {
    public static void save(BattleLog log, Path file) throws IOException {
        Files.write(file, log.lines());
    }

    public static BattleLog load(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        BattleLog log = new BattleLog();
        for (String s : lines) log.add(s);
        return log;
    }
}
