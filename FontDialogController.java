import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontDialogController implements ActionListener {
    private final Viewer viewer;
    private final FontDialogWindow fontDialog;

    public FontDialogController(Viewer viewer, FontDialogWindow fontDialog) {
        this.viewer = viewer;
        this.fontDialog = fontDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        doAction(command);
    }

    public void doAction(String command) {
        if (command.equals(CommandEnum.FONT_OK.toString())) {
            viewer.setFont(
                    new Font(fontDialog.getFontFamily()[0], Font.PLAIN, Integer.parseInt(fontDialog.getFontSize()[0]))
            );
            fontDialog.getFontDialog().dispose();
        } else if (command.equals(CommandEnum.FONT_CANCEL.toString())) {
            fontDialog.dispose();
        } else if (command.equals(CommandEnum.CHANGE_FONT_FAMILY.toString())) {
            fontDialog.getFontFamily()[0] = fontDialog.getSelectedItemFromFontFamily();
            fontDialog.setFontFamilyForTextLabel();
        } else if (command.equals(CommandEnum.CHANGE_FONT_SIZE.toString())) {
            fontDialog.getFontSize()[0] = fontDialog.getSelectedItemFromFontSize();
            fontDialog.setFontSizeForTextLabel();
        }
    }
}
