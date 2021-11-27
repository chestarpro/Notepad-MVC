import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ExitController implements WindowListener {
    private final JFrame frame;
    private final MyFileUtil fileUtil;

    public ExitController(JFrame frame, MyFileUtil fileUtil) {
        this.frame = frame;
        this.fileUtil = fileUtil;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int res = fileUtil.getRequestForChangedSaveFile();
        if (res == 2 || res == -1) {
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        } else if (res == 0) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}