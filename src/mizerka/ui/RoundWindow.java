package mizerka.ui;

import mizerka.GameContext;
import mizerka.Player;
import mizerka.Round;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class RoundWindow extends WindowBase
{
    private HashMap<Player, JLabel> scoreLabels;

    @Override
    public void initialize() {
        setSize(600, 400);
        super.initialize();

        scoreLabels = new HashMap<>();
        addLabels();
        addPlayers();
        addEndButton();

        initialized = true;
    }

    private void addLabels() {
        JLabel roundLabel = new JLabel("Runda: " + (Round.getIndexOfCurrent() + 1));
        roundLabel.setBounds(20, 20, 260, 20);
        panel.add(roundLabel);

        JLabel gameModeLabel = new JLabel("Tryb gry: " + Round.getCurrentGameMode());
        gameModeLabel.setBounds(20, 40, 260, 20);
        panel.add(gameModeLabel);
    }

    private void addPlayers() {
        List<Player> players = GameContext.getInstance().getPlayers();
        int x = 20;

        for (Player player : players) {
            addPlayerNameLabel(x, 80, 100, 20, player);
            addPlayerButtons(x, 110, 50, 50, player);
            addScoreLabel(x, 160, 100, 20, player);
            x += 210;
        }

        updateScoreLabels();
    }

    private void addEndButton() {
        JButton endRoundButton = new JButton("Koniec rundy");
        endRoundButton.setBounds(230, 250, 120, 30);
        endRoundButton.addActionListener(e -> onEndRound());
        panel.add(endRoundButton);
    }

    private void onEndRound()
    {
        if (Round.end()) {
            new EndRoundWindow().show();
            hide();
        }
    }

    private void addPlayerNameLabel(int x, int y, int width, int height, Player player) {
        JLabel label = new JLabel(player.getName());
        label.setBounds(x, y, width, height);
        panel.add(label);
    }

    private void addPlayerButtons(int x, int y, int width, int height, Player player) {
        Font font = new Font("Arial", Font.BOLD, 20);

        JButton addButton = new JButton("+");
        addButton.setBounds(x, y, width, height);
        addButton.setFont(font);
        addButton.setFocusable(false);
        addButton.addActionListener(e -> onAddScore(player));
        panel.add(addButton);

        JButton subtractButton = new JButton("-");
        subtractButton.setBounds(x + width, y, width, height);
        subtractButton.setFont(font);
        subtractButton.setFocusable(false);
        subtractButton.addActionListener(e -> onSubtractScore(player));
        panel.add(subtractButton);
    }

    private void addScoreLabel(int x, int y, int width, int height, Player player) {
        JLabel label = new JLabel();
        label.setBounds(x, y, width, height);
        panel.add(label);
        scoreLabels.put(player, label);
    }

    private void onSubtractScore(Player player)
    {
        Round.getCurrent().addScore(player, -1);
        updateScoreLabels();
    }

    private void onAddScore(Player player)
    {
        Round.getCurrent().addScore(player, 1);
        updateScoreLabels();
    }

    public void updateScoreLabels() {
        for (Player player : GameContext.getInstance().getPlayers()) {
            scoreLabels.get(player).setText(Round.getCurrent().getFormattedScore(player));
        }
    }
}
