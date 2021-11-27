import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.put("nimbusBase", Color.BLACK);
        UIManager.put("nimbusBlueGrey", Color.DARK_GRAY);
        UIManager.put("control", Color.GRAY);
        UIManager.put("info", Color.DARK_GRAY);
        UIManager.put("TextArea.foreground", Color.WHITE);
        UIManager.put("MenuBar:Menu[Selected].backgroundPainter", Color.WHITE);

        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        new Viewer();

    }
}