package mizerka.ui;

import mizerka.GameContext;
import mizerka.Player;
import mizerka.Round;

import javax.swing.*;
import java.util.List;

public class EndRoundWindow extends WindowBase
{
    @Override
    protected void initialize() {
        setSize(300, 300);
        super.initialize();

        addLabels();
        addNextButton();

        initialized = true;
    }

    private void addLabels() {
        JLabel roundLabel = new JLabel(GameContext.getInstance().isEndGame() ? "Finalne punkty:" : "Punkty po rundzie " + (Round.getIndexOfCurrent() + 1) + ":");
        roundLabel.setBounds(10, 10, 260, 20);
        panel.add(roundLabel);

        List<Player> players = GameContext.getInstance().getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JLabel playerLabel = new JLabel(player.getName() + ": " + player.getFinalPoints());
            playerLabel.setBounds(10, 60 + i * 30, 260, 20);
            panel.add(playerLabel);
        }

        if (GameContext.getInstance().isEndGame()) {
            Player winner = GameContext.getInstance().getWinner();
            JLabel winnerLabel = new JLabel("Zwycięzca: " + winner.getName() + "!!!");
            winnerLabel.setBounds(10, 150, 260, 20);
            panel.add(winnerLabel);
        }
    }

    private void addNextButton() {
        JButton nextButton = new JButton(GameContext.getInstance().isEndGame() ? "Zakończ grę" : "Następna runda");
        nextButton.setBounds(10, 180, 260, 70);
        nextButton.addActionListener(e -> onNext());
        panel.add(nextButton);
    }

    private void onNext() {
        if (GameContext.getInstance().isEndGame()) {
            System.exit(0);
        } else {
            new GameModeSelector().show();
            hide();
        }
    }
}
