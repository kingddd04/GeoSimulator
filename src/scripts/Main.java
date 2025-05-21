package scripts;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainMenuGUI bootMenuGui = new MainMenuGUI();
            bootMenuGui.setVisible(true);
        });
    }
}