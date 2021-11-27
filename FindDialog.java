import javax.swing.*;
import java.awt.*;

public class FindDialog implements ActionNotepad {
    private final Viewer viewer;
    private JDialog findDialog;
    private JTextField textField;
    private final FindDialogController controller;

    public FindDialog(Viewer viewer) {
        this.viewer = viewer;
        controller = new FindDialogController(viewer, this);
        findDialog = null;
        textField = null;
    }

    @Override
    public void doAction() {
        if (findDialog == null || textField == null) {
            initFindDialog();
        }
        if (viewer.getTextFromTextArea().isEmpty()) {
            return;
        }
        findDialog.setVisible(true);
    }

    public void dispose() {
        findDialog.dispose();
    }

    public String getTextFromTextField() {
        return textField.getText();
    }

    public void getDialogWindowSearch() {
        findDialog.setVisible(true);
    }

    public void initFindDialog() {
        findDialog = new JDialog(viewer.getFrame(), "Find", true);
        findDialog.setSize(360, 180);
        findDialog.setLocation(420, 200);
        findDialog.setResizable(false);
        JPanel findDialogPanel = new JPanel();
        findDialogPanel.setLayout(new BorderLayout());
        findDialogPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));

        JPanel findPanel1 = new JPanel();
        JLabel findTextLabel = new JLabel("Find what:");
        textField = new JTextField(20);
        findPanel1.add(findTextLabel);
        findPanel1.add(textField);
        JPanel findPanel2 = new JPanel();

        JButton findConfirmButton = new JButton("Find next");
        findConfirmButton.setActionCommand(CommandEnum.FIND_NEXT.toString());
        findConfirmButton.addActionListener(controller);

        viewer.getEditItemFindMore().setActionCommand(CommandEnum.FIND_MORE.toString());
        viewer.getEditItemFindMore().addActionListener(controller);

        JButton findCancelButton = new JButton("Cancel");
        findCancelButton.setActionCommand(CommandEnum.FIND_CANCEL.toString());
        findCancelButton.addActionListener(controller);

        findPanel2.add(findConfirmButton);
        findPanel2.add(findCancelButton);

        findDialogPanel.add(findPanel1, BorderLayout.NORTH);
        findDialogPanel.add(findPanel2, BorderLayout.SOUTH);

        findDialog.add(findDialogPanel);
    }
}