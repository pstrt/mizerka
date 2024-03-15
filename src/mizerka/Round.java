package mizerka;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class Round
{
    private final Player currentPlayer;
    private final GameMode gameMode;
    private final HashMap<Player, Integer> scores;
    private final HashMap<Player, Integer> stacks;

    private Round(GameMode gameMode) {
        this.currentPlayer = GameContext.getInstance().getNextPlayer();
        this.gameMode = gameMode;
        this.scores = new HashMap<>();
        this.stacks = new HashMap<>();
        for (Player player : GameContext.getInstance().getPlayers()) {
            scores.put(player, 0);
        }
    }

    private void initializeStacks() {
        boolean condition = gameMode != GameMode.BEZ_LEW;

        List<Player> players = GameContext.getInstance().getPlayers();
        int currentPlayerIndex = players.indexOf(currentPlayer);

        for (int i = 0; i < players.size(); i++) {
            int stackValue;
            if (i == currentPlayerIndex) {
                stackValue = condition ? 7 : 3;
            } else if ((currentPlayerIndex + 1) % players.size() == i) {
                stackValue = 5;
            } else {
                stackValue = condition ? 3 : 7;
            }
            stacks.put(players.get(i), stackValue);
        }
    }

    public int getPoints(Player player) {
        return gameMode == GameMode.BEZ_LEW ? stacks.get(player) - scores.get(player) : scores.get(player) - stacks.get(player);
    }

    public int addScore(Player player, int score) {
        if (scores.get(player) + score >= 0) {
            scores.put(player, scores.get(player) + score);
        }
        return scores.get(player);
    }

    public String getFormattedScore(Player player) {
        return scores.get(player) + " / " + stacks.get(player);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public static boolean start(GameMode gameMode) {
        Round round = new Round(gameMode);

        if (!round.currentPlayer.validateGameMode(gameMode)) {
            JOptionPane.showMessageDialog(null, "Błąd: Gracz " + round.currentPlayer.getName() + " wybrał już ten tryb gry");
            return false;
        }
        round.initializeStacks();

        GameContext.getInstance().setRound(round);
        return true;
    }

    public static boolean end() {
        Round round = getCurrent();

        int pointsSum = 0;
        for (Player player : GameContext.getInstance().getPlayers()) {
            pointsSum += round.getPoints(player);
        }

        if (pointsSum != 0) {
            JOptionPane.showMessageDialog(null, "Suma zebranych kupek nie wynosi 0!");
            return false;
        }

        round.currentPlayer.onFinished(round);
        return true;
    }

    public static Round getCurrent() {
        return GameContext.getInstance().getCurrentRound();
    }

    public static int getIndexOfCurrent() {
        return GameContext.getInstance().getIndexOfCurrentRound();
    }

    public static GameMode getCurrentGameMode() {
        return getCurrent().gameMode;
    }
}
