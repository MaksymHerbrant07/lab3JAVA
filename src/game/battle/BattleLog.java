package game.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleLog {
    private final List<String> lines = new ArrayList<>();

    public void add(String s) { lines.add(s); }

    public List<String> lines() { return Collections.unmodifiableList(lines); }
}
