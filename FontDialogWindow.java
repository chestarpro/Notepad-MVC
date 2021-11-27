import javax.swing.*;
import java.awt.*;

public class FontDialogWindow implements ActionNotepad {
    private final Viewer viewer;
    private JDialog fontDialog;
    private String[] fontFamily;
    private String[] fontSize;
    private JComboBox<String> fontFamilyComboBox;
    private JComboBox<String> fontSizeComboBox;
    private JLabel textLabel;
    private final FontDialogController controller;

    public FontDialogWindow(Viewer viewer) {
        controller = new FontDialogController(viewer, this);
        this.viewer = viewer;
        fontDialog = null;
        fontFamily = null;
        fontSize = null;
        fontFamilyComboBox = null;
        fontSizeComboBox = null;
        textLabel = null;
    }

    @Override
    public void doAction() {
        if (fontDialog == null) {
            initFontDialog();
        }
        fontDialog.setVisible(true);
    }

    public JDialog getFontDialog() {
        return fontDialog;
    }

    public String[] getFontFamily() {
        return fontFamily;
    }

    public String[] getFontSize() {
        return fontSize;
    }

    public void dispose() {
        fontDialog.dispose();
    }

    public String getSelectedItemFromFontFamily() {
        return (String) fontFamilyComboBox.getSelectedItem();
    }

    public void setFontFamilyForTextLabel() {
        textLabel.setFont(
                new Font(getSelectedItemFromFontFamily(), Font.PLAIN,
                        textLabel.getFont().getSize())
        );
    }

    public String getSelectedItemFromFontSize() {
        return (String) fontSizeComboBox.getSelectedItem();
    }

    public void setFontSizeForTextLabel() {
        textLabel.setFont(
                new Font(textLabel.getFont().getFamily(), Font.PLAIN, Integer.parseInt(getSelectedItemFromFontSize()))
        );
    }

    private void initFontDialog() {
        fontDialog = new JDialog(viewer.getFrame(), "Font", true);
        fontFamily = new String[]{
                viewer.getFontFamily()
        };
        fontSize = new String[]{"" + viewer.getFontSize()};
        String[] fontSizeList = {"8", "9", "10", "12", "14", "16", "18", "20", "22", "26", "30", "40"};
        String[] fontFamilyList = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();

        fontDialog.setLocation(400,200);
        fontDialog.setSize(460, 420);
        fontDialog.setLayout(new BorderLayout());

        //Main panel
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        //Sample panel
        JPanel samplePanel = new JPanel();
        samplePanel.setLayout(new GridBagLayout());
        samplePanel.setBorder(BorderFactory.createTitledBorder("Sample"));
        samplePanel.setBackground(Color.WHITE);
        textLabel = new JLabel("aaAAbbBBccCC");
        textLabel.setFont(new Font(viewer.getFontFamily(), Font.PLAIN, viewer.getFontSize()));
        samplePanel.add(textLabel);

        //Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setPreferredSize(new Dimension(440, 200));

        //Font family wrapper
        JPanel fontFamilyPanel = new JPanel();
        fontFamilyPanel.setLayout(new BorderLayout(10, 10));

        //Font family comboBox
        JLabel fontFamilyLabel = new JLabel("Font Family");
        fontFamilyComboBox = new JComboBox<>(fontFamilyList);
        fontFamilyComboBox.setSelectedItem(viewer.getFontFamily());
        fontFamilyComboBox.setActionCommand(CommandEnum.CHANGE_FONT_FAMILY.toString());
        fontFamilyComboBox.addActionListener(controller);
        fontFamilyPanel.add(fontFamilyLabel, BorderLayout.NORTH);
        fontFamilyPanel.add(fontFamilyComboBox, BorderLayout.CENTER);
        optionsPanel.add(fontFamilyPanel);

        //Font size wrapper
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setLayout(new BorderLayout(10, 10));

        //Font size comboBox
        JLabel fontSizeLabel = new JLabel("Font Size");
        fontSizeComboBox = new JComboBox<>(fontSizeList);
        fontSizeComboBox.setSelectedItem("" + viewer.getFontSize());
        fontSizeComboBox.setActionCommand(CommandEnum.CHANGE_FONT_SIZE.toString());
        fontSizeComboBox.addActionListener(controller);
        fontSizePanel.add(fontSizeLabel, BorderLayout.NORTH);
        fontSizePanel.add(fontSizeComboBox, BorderLayout.CENTER);
        optionsPanel.add(fontSizePanel);

        //Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton fontConfirmButton = new JButton("OK");
        fontConfirmButton.addActionListener(controller);
        fontConfirmButton.setActionCommand(CommandEnum.FONT_OK.toString());

        JButton fontCancelButton = new JButton("Cancel");
        fontCancelButton.addActionListener(controller);
        fontCancelButton.setActionCommand(CommandEnum.FONT_CANCEL.toString());

        buttonsPanel.add(fontConfirmButton);
        buttonsPanel.add(fontCancelButton);

        panel.add(optionsPanel, BorderLayout.NORTH);
        panel.add(samplePanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.PAGE_END);

        fontDialog.add(panel, BorderLayout.CENTER);
        fontDialog.setResizable(false);
    }
}