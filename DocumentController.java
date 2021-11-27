import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentController implements DocumentListener {

    private final Viewer viewer;

    public DocumentController(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        viewer.setChangedTextArea(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        viewer.setChangedTextArea(true);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        viewer.setChangedTextArea(true);
    }
}