import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindDialogController implements ActionListener {
    private final Viewer viewer;
    private final FindDialog findDialog;
    private final Highlighter highlighter;
    private int lastIndex;
    private String findText;
    private final Color color;

    public FindDialogController(Viewer viewer, FindDialog findDialog) {
        this.viewer = viewer;
        this.findDialog = findDialog;
        highlighter = viewer.getHighlighter();
        lastIndex = 0;
        color = new Color(200, 132, 255);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        doAction(command);
    }

    public void doAction(String command) {
        findText = findDialog.getTextFromTextField();
        if (command.equals(CommandEnum.FIND_NEXT.toString())) {
            search();
        } else if (command.equals(CommandEnum.FIND_MORE.toString())) {
            if (findText != null && !findText.isEmpty()) {
                search();
            } else {
                findDialog.getDialogWindowSearch();
            }
        } else if (command.equals(CommandEnum.FIND_CANCEL.toString())) {
            findDialog.dispose();
        }
    }


    private void search() {
        highlighter.removeAllHighlights();
        String text = viewer.getTextFromTextArea();
        viewer.setFlag(true);

        if (text.length() < lastIndex) {
            lastIndex = 0;
        }

        if (viewer.getTextFromTextArea().contains(findDialog.getTextFromTextField())) {
            int index = text.indexOf(findText, lastIndex);
            int end = index + findText.length();

            try {
                highlighter.addHighlight(index, end, new DefaultHighlighter.DefaultHighlightPainter(color));
                if ((end < text.length() - 1) && (text.substring(end).contains(findText))) {
                    lastIndex = end;
                } else {
                    lastIndex = 0;
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else {
            viewer.getMessageCantFind(findDialog.getTextFromTextField());
        }
    }
}