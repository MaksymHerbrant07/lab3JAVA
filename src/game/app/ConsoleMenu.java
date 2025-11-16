package game.app;

import game.battle.BattleEngine;
import game.battle.BattleLog;
import game.battle.TeamBattleEngine;
import game.factory.DroidFactory;
import game.model.Droid;
import game.storage.BattleStorage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner in = new Scanner(System.in);
    private final List<Droid> roster = new ArrayList<>();
    private final DroidFactory factory = new DroidFactory();
    private BattleLog lastLog;

    public void run() {
        while (true) {
            System.out.println("""
                =============================
                1) Створити дроїда
                2) Показати список дроїдів
                3) Бій 1×1
                4) Бій команда×команда
                5) Записати бій у файл
                6) Відтворити бій з файлу
                0) Вихід
                =============================""");
            System.out.print("Ваш вибір: ");
            String cmd = in.nextLine().trim();
            switch (cmd) {
                case "1" -> createDroidFlow();
                case "2" -> showRoster();
                case "3" -> duelFlow();
                case "4" -> teamFlow();
                case "5" -> saveFlow();
                case "6" -> replayFlow();
                case "0" -> { System.out.println("Бувай!"); return; }
                default -> System.out.println("Невірна команда");
            }
        }
    }

    private void createDroidFlow() {
        System.out.print("Тип (strength/agility/universal): ");
        String type = in.nextLine().trim();
        System.out.print("Ім'я: ");
        String name = in.nextLine().trim();
        try {
            Droid d = factory.create(type, name);
            roster.add(d);
            System.out.println("Створено: " + d);
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private void showRoster() {
        if (roster.isEmpty()) {
            System.out.println("Список порожній");
            return;
        }
        for (int i = 0; i < roster.size(); i++) {
            System.out.printf("[%d] %s%n", i, roster.get(i));
        }
    }

    private void duelFlow() {
        if (roster.size() < 2) {
            System.out.println("Потрібно щонайменше 2 дроїди.");
            return;
        }
        showRoster();
        try {
            System.out.print("Індекс першого: ");
            int a = Integer.parseInt(in.nextLine().trim());
            System.out.print("Індекс другого: ");
            int b = Integer.parseInt(in.nextLine().trim());
            Droid d1 = roster.get(a);
            Droid d2 = roster.get(b);

            BattleEngine engine = new BattleEngine();
            lastLog = engine.duel(d1, d2);
            lastLog.lines().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Помилка вибору: " + e.getMessage());
        }
    }

    private void teamFlow() {
        if (roster.size() < 2) {
            System.out.println("Додайте більше дроїдів.");
            return;
        }
        // Простий розподіл: парні в A, непарні в B
        List<Droid> A = new ArrayList<>(), B = new ArrayList<>();
        for (int i = 0; i < roster.size(); i++) {
            if (i % 2 == 0) A.add(roster.get(i)); else B.add(roster.get(i));
        }
        if (A.isEmpty() || B.isEmpty()) {
            System.out.println("Не вдалось сформувати дві команди — додайте ще дроїдів.");
            return;
        }
        TeamBattleEngine engine = new TeamBattleEngine();
        lastLog = engine.fight(A, B);
        lastLog.lines().forEach(System.out::println);
    }

    private void saveFlow() {
        if (lastLog == null) {
            System.out.println("Немає логу бою для збереження.");
            return;
        }
        System.out.print("Вкажіть файл (наприклад, battle.txt): ");
        String p = in.nextLine().trim();
        try {
            BattleStorage.save(lastLog, Path.of(p));
            System.out.println("Збережено у " + Path.of(p).toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        }
    }

    private void replayFlow() {
        System.out.print("Вкажіть файл для відтворення: ");
        String p = in.nextLine().trim();
        try {
            BattleLog log = BattleStorage.load(Path.of(p));
            log.lines().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Помилка читання: " + e.getMessage());
        }
    }
}
