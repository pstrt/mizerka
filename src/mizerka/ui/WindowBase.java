package mizerka.ui;

import javax.swing.*;

public class WindowBase
{
    private static final String TITLE = "Mizerka";

    protected int width;
    protected int height;

    protected boolean initialized = false;
    protected JFrame frame;
    protected JPanel panel;

    protected void initialize() {
        frame = new JFrame(TITLE);
        panel = new JPanel();

        frame.setSize(width, height);
        frame.add(panel);
        panel.setLayout(null);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    protected void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void show() {
        if (!initialized) {
            initialize();
        }

        frame.setVisible(true);
    }

    protected void hide() {
        frame.setVisible(false);
        frame.dispose();
    }
}
