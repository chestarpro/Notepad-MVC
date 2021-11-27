import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.Highlighter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Viewer {

    private final JFrame frame;
    private final MyFileUtil fileUtil;
    private final JMenuBar menuBar;

    private final MyTextArea textArea;
    private final UndoManager manager;
    private JTextField textFieldGoTo;
    private JPanel statusPanel;
    private JLabel statusLabel;
    private JDialog goToDialog;
    private JToolBar toolBar;
    private JCheckBox statusBarBox;

    private JMenuItem editItemFind;
    private JMenuItem editItemDelete;
    private JMenuItem editItemCopy;
    private JMenuItem editItemCut;
    private JMenuItem editItemFindMore;
    private JMenuItem editItemUndo;
    private JMenuItem editItemRedo;
    private JMenuItem viewItemZoomIn;
    private JMenuItem viewItemZoomOut;
    private JMenuItem viewItemZoomDefault;

    private String initFontFamily;

    private int initFontSize;
    private boolean changedTextArea;
    private boolean flag;
    private Font fontZoom;
    private Font fontText;

    public Viewer() {
        frame = new JFrame("Untitled - Notepad-MVC");
        flag = false;
        textArea = new MyTextArea();
        manager = new UndoManager();
        // FontSize, FontFamily
        initDefaultFont();
        //TextArea
        initTextArea();
        fileUtil = new MyFileUtil(this);
        ActionController actionController = new ActionController(this, fileUtil);

        //Menu bar
        menuBar = new JMenuBar();

        //Menu file
        initMenuFile(actionController);

        //Menu edit
        initMenuEdit(actionController);

        //Menu format
        initMenuFormat(actionController);

        //Menu view
        initStatusPanel();
        initMenuView(actionController);

        //Menu help
        initMenuHelp(actionController);

        //Toolbar
        initToolBar(actionController);

        //Frame
        initFrame();
        frame.setVisible(true);
        fontZoom = textArea.getFont();
        fontText = textArea.getFont();
    }

    /*---Frame----Begin---*/
    private void initFrame() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        int width = (int) ((double) gd.getDisplayMode().getWidth() * 0.63);
        int height = (int) ((double) gd.getDisplayMode().getHeight() * 0.70);
        width = width - (width % 10);
        height = height - (height % 10);

        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("./images/main-icon.png"));
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(initMenuPanel(), BorderLayout.NORTH);
        frame.add(initScrollPane(), BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);

        addCurrentExit();
    }

    private void addCurrentExit() {
        frame.addWindowListener(new ExitController(frame, fileUtil));
    }

    public JFrame getFrame() {
        return frame;
    }
    /*---Frame----End---*/

    /*---TextArea----Begin---*/
    private void initTextArea() {

        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(initFontFamily, Font.PLAIN, initFontSize));

        textArea.setBackground(new Color(43, 43, 43));
        textArea.setSelectionColor(new Color(200, 132, 255));

        textArea.addCaretListener(new CaretController(this));
        textArea.getDocument().addUndoableEditListener(manager);
        addCheckChangedFromTextArea();
    }


    public void updateTextArea(String text) {
        textArea.setText(text);
    }

    public void setFont(Font font) {
        textArea.setFont(font);
        fontText = font;
    }

    public Font getFont() {
        return fontText;
    }

    public String getTextFromTextArea() {
        return textArea.getText();
    }

    public List<String> getListTextFromTextArea() {
        return textArea.getListText();
    }

    public boolean isChangedTextArea() {
        return changedTextArea;
    }

    public void setChangedTextArea(boolean changedTextArea) {
        this.changedTextArea = changedTextArea;
    }

    private void addCheckChangedFromTextArea() {
        textArea.getDocument().addDocumentListener(new DocumentController(this));
    }

    private JPanel initMenuPanel() {
        JPanel menuPanel = new JPanel();

        menuPanel.setLayout(new BorderLayout());

        menuPanel.add(menuBar, BorderLayout.NORTH);
        menuPanel.add(toolBar, BorderLayout.SOUTH);

        return menuPanel;
    }

    private JScrollPane initScrollPane() {
        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setPreferredSize(new Dimension(500, 600));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }

    public String textAreaGetSelectedText() {
        return textArea.getSelectedText();
    }

    //Font
    private void initDefaultFont() {
        initFontFamily = "Arial";
        initFontSize = 22;
    }
    /*---TextArea----End---*/

    /*---MenuFile----Begin---*/
    private void initMenuFile(ActionController actionController) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        JMenuItem fileItemCreate = createMenuItem("Create", createIcon("./images/create.png"),
                CommandEnum.CREATE, actionController);
        fileItemCreate.setAccelerator(createKeyStroke(KeyEvent.VK_N));

        JMenuItem fileItemOpen = createMenuItem("Open...", createIcon("./images/open.png"),
                CommandEnum.OPEN, actionController);
        fileItemOpen.setAccelerator(createKeyStroke(KeyEvent.VK_O));

        JMenuItem fileItemSave = createMenuItem("Save", createIcon("./images/save.png"),
                CommandEnum.SAVE, actionController);
        fileItemSave.setAccelerator(createKeyStroke(KeyEvent.VK_S));

        JMenuItem fileItemSaveAs = createMenuItem("Save As...    ", createIcon("./images/save-as.png"),
                CommandEnum.SAVE_AS, actionController);
        KeyStroke ctrlShiftS = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK |
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        fileItemSaveAs.setAccelerator(ctrlShiftS);

        JMenuItem fileItemPrint = createMenuItem("Print...", createIcon("./images/print.png"),
                CommandEnum.PRINT, actionController);
        fileItemPrint.setAccelerator(createKeyStroke(KeyEvent.VK_P));

        JMenuItem fileItemExit = createMenuItem("Exit", createIcon("./images/exit.png"), CommandEnum.EXIT, actionController);

        fileMenu.add(fileItemCreate);
        fileMenu.add(fileItemOpen);
        fileMenu.add(fileItemSave);
        fileMenu.add(fileItemSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(fileItemPrint);
        fileMenu.addSeparator();
        fileMenu.add(fileItemExit);
        menuBar.add(fileMenu);
    }

    //Default file name
    public void setUntitledFileNameToFrame() {
        frame.setTitle("Untitled - Notepad MVC");
    }

    //File name
    public void setFileNameToFrame(String fileName) {
        frame.setTitle(fileName + " - Notepad-MVC");
    }

    //Open
    public int getDialogOpen(JFileChooser fileChooser) {
        return fileChooser.showOpenDialog(frame);
    }

    public void getMessageErrorOpenFile() {
        JOptionPane.showMessageDialog(frame, "The file was not found.\n" +
                        "Check if the file name is correct and try again.", "Notepad-MVC",
                JOptionPane.ERROR_MESSAGE, createIcon("./images/warning.png"));
    }

    //Save and Save As
    public int getConfirmMessageToSave() {
        return JOptionPane.showConfirmDialog(frame, "Do you want to save the changes in the \n"
                        + fileUtil.getFileName() + "?", "Notepad-MVC", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, createIcon("./images/question.png"));
    }

    public int getConfirmMessageToReplace() {
        return JOptionPane.showConfirmDialog(frame, "Such file already exists \n" +
                        "Do you want replace file?", "Notepad-MVC", JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE, createIcon("./images/warning.png"));
    }

    public int getDialogSave(JFileChooser fileChooser) {
        return fileChooser.showSaveDialog(frame);
    }

    //Print
    public void showDialogFinishPrintDocument() {
        JOptionPane.showMessageDialog(frame, "The document has been printed.", "Notepad-MVC",
                JOptionPane.INFORMATION_MESSAGE, createIcon("./images/check.png"));
    }
    /*---MenuFile----End---*/

    /*---MenuEdit----Begin---*/
    private void initMenuEdit(ActionController actionController) {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');

        editItemUndo = createMenuItem("Undo", createIcon("./images/undo.png"),
                CommandEnum.UNDO, actionController);
        editItemUndo.setAccelerator(createKeyStroke(KeyEvent.VK_Z));
        editItemUndo.setEnabled(false);

        editItemRedo = createMenuItem("Redo", createIcon("./images/redo.png"),
                CommandEnum.REDO, actionController);
        editItemRedo.setAccelerator(createKeyStroke(KeyEvent.VK_Y));
        editItemRedo.setEnabled(false);

        editItemCopy = createMenuItem("Copy", createIcon("./images/copy.png"),
                CommandEnum.COPY, actionController);
        editItemCopy.setAccelerator(createKeyStroke(KeyEvent.VK_C));
        editItemCopy.setEnabled(false);

        editItemCut = createMenuItem("Cut", createIcon("./images/cut.png"),
                CommandEnum.CUT, actionController);
        editItemCut.setAccelerator(createKeyStroke(KeyEvent.VK_X));
        editItemCut.setEnabled(false);

        JMenuItem editItemPaste = createMenuItem("Paste", createIcon("./images/paste.png"),
                CommandEnum.PASTE, actionController);
        editItemPaste.setAccelerator(createKeyStroke(KeyEvent.VK_V));

        editItemDelete = createMenuItem("Delete", createIcon("./images/delete.png"),
                CommandEnum.CLEAR, actionController);
        editItemDelete.setAccelerator(createKeyStroke(KeyEvent.VK_L));
        editItemDelete.setEnabled(false);

        //Select All
        JMenuItem fileItemSelectAll = createMenuItem("Select all", createIcon("./images/select-all.png"),
                CommandEnum.SELECT_ALL, actionController);
        fileItemSelectAll.setAccelerator(createKeyStroke(KeyEvent.VK_A));

        editItemFind = createMenuItem("Find", createIcon("./images/search.png"),
                CommandEnum.FIND, actionController);
        editItemFind.setAccelerator(createKeyStroke(KeyEvent.VK_F));
        editItemFind.setEnabled(false);

        editItemFindMore = new JMenuItem("Find more...   ");
        editItemFindMore.setActionCommand(CommandEnum.FIND.toString());
        editItemFindMore.addActionListener(actionController);
        editItemFindMore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        editItemFindMore.setEnabled(false);

        JMenuItem editItemGoTo = createMenuItem("   Go", createIcon("./images/go-to.png"),
                CommandEnum.GO_TO, actionController);
        editItemGoTo.setAccelerator(createKeyStroke(KeyEvent.VK_G));

        JMenuItem editItemDate = createMenuItem("Time/Date", createIcon("./images/time-date.png"),
                CommandEnum.DATE, actionController);
        editItemDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

        editMenu.add(editItemUndo);
        editMenu.add(editItemRedo);
        editMenu.addSeparator();
        editMenu.add(editItemCopy);
        editMenu.add(editItemCut);
        editMenu.add(editItemPaste);
        editMenu.add(editItemDelete);
        editMenu.addSeparator();
        editMenu.add(editItemFind);
        editMenu.add(editItemFindMore);
        editMenu.add(editItemGoTo);
        editMenu.addSeparator();
        editMenu.add(fileItemSelectAll);
        editMenu.add(editItemDate);

        //Menu format
        JMenu formatMenu = new JMenu("Format");
        formatMenu.setMnemonic('T');
        formatMenu.add(createMenuItem("Font...", createIcon("./images/font.png"),
                CommandEnum.FONT, actionController));

        //Font Dialog
        JDialog fontDialog = new JDialog(frame, "Font", true);

        String[] fontSizeList = {"8", "9", "10", "12", "14", "16", "18", "20", "22", "22", "30", "40"};
        String[] fontFamilyList = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();

        fontDialog.setLocation((int) frame.getLocation().getX() + 20,
                (int) frame.getLocation().getY() + 100);
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
        JLabel textLabel = new JLabel("aaAAbbBBccCC");
        textLabel.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, textArea.getFont().getSize()));
        samplePanel.add(textLabel);

        //Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setPreferredSize(new Dimension(440, 200));

        //Font family wrapper
        JPanel fontFamilyPanel = new JPanel();
        fontFamilyPanel.setLayout(new BorderLayout(10, 10));

        //Font family comboBox
        JLabel fontFamilyLabel = new JLabel("Font Family");
        JComboBox fontFamilyComboBox = new JComboBox<>(fontFamilyList);
        fontFamilyComboBox.setSelectedItem(textArea.getFont().getFamily());
        fontFamilyComboBox.setActionCommand(CommandEnum.CHANGE_FONT_FAMILY.toString());
        fontFamilyComboBox.addActionListener(actionController);
        fontFamilyPanel.add(fontFamilyLabel, BorderLayout.NORTH);
        fontFamilyPanel.add(fontFamilyComboBox, BorderLayout.CENTER);
        optionsPanel.add(fontFamilyPanel);

        //Font size wrapper
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setLayout(new BorderLayout(10, 10));

        //Font size comboBox
        JLabel fontSizeLabel = new JLabel("Font Size");
        JComboBox fontSizeComboBox = new JComboBox<>(fontSizeList);
        fontSizeComboBox.setSelectedItem("" + textArea.getFont().getSize());
        fontSizeComboBox.setActionCommand(CommandEnum.CHANGE_FONT_SIZE.toString());
        fontSizeComboBox.addActionListener(actionController);
        fontSizePanel.add(fontSizeLabel, BorderLayout.NORTH);
        fontSizePanel.add(fontSizeComboBox, BorderLayout.CENTER);
        optionsPanel.add(fontSizePanel);

        //Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        buttonsPanel.add(createButton("OK", CommandEnum.OK, actionController));
        buttonsPanel.add(createButton("Cancel", CommandEnum.CANCEL, actionController));

        panel.add(optionsPanel, BorderLayout.NORTH);
        panel.add(samplePanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.PAGE_END);

        fontDialog.add(panel, BorderLayout.CENTER);
        fontDialog.setResizable(false);

        menuBar.add(editMenu);
    }

    //Undo
    public void undoDocumentText() {
        if (manager.canUndo()) {
            manager.undo();
        }
    }

    public boolean checkCanUndo() {
        return manager.canUndo();
    }

    public void setEditItemUndo(boolean access) {
        editItemUndo.setEnabled(access);
    }

    //Redo
    public void redoDocumentText() {
        if (manager.canRedo()) {
            manager.redo();
        }
    }

    public boolean checkCanRedo() {
        return manager.canRedo();
    }

    public void setEditItemRedo(boolean access) {
        editItemRedo.setEnabled(access);
    }

    //Cut
    public void cutTextArea() {
        textArea.cut();
    }

    public void setEditItemCutEnabled(boolean access) {
        editItemCut.setEnabled(access);
    }

    //Copy
    public void copyTextArea() {
        textArea.copy();
    }

    public void setEditItemCopyEnabled(boolean access) {
        editItemCopy.setEnabled(access);
    }

    //Paste
    public void pasteTextArea() {
        textArea.paste();
    }

    //Delete
    public void deleteTextArea() {
        textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
        editItemDelete.setEnabled(false);
    }

    public void setEditItemDeleteEnabled(boolean access) {
        editItemDelete.setEnabled(access);
    }

    //Find
    public void setEditItemFindEnabled(boolean access) {
        editItemFind.setEnabled(access);
    }

    public void clearHighlight() {
        textArea.getHighlighter().removeAllHighlights();
    }

    public Highlighter getHighlighter() {
        return textArea.getHighlighter();
    }

    public void setFlag(boolean value) {
        flag = value;
    }

    public boolean getFlag() {
        return flag;
    }

    public void getMessageCantFind(String word) {
        JOptionPane.showMessageDialog(frame, "Cannot find \"" + word + "\"", "Notepad-MVC",
                JOptionPane.INFORMATION_MESSAGE, createIcon("./images/info.png"));
    }

    //Find more
    public void setEditItemFindMoreEnabled(boolean access) {
        editItemFindMore.setEnabled(access);
    }

    public JMenuItem getEditItemFindMore() {
        return editItemFindMore;
    }

    //GoTo
    private void initGoToDialog(ActionController actionController) {
        goToDialog = new JDialog(frame, "Go To Line", true);
        goToDialog.setSize(350, 120);
        goToDialog.setLocation((int) frame.getLocation().getX() + 10, (int) frame.getLocation().getY() + 95);
        goToDialog.setResizable(false);

        JPanel gotoPanel = new JPanel();

        JPanel gotoPanelLine = new JPanel();
        JLabel lineNumber = new JLabel("Line Number: ");
        textFieldGoTo = new JTextField("1", 20);
        textFieldGoTo.selectAll();

        gotoPanelLine.add(lineNumber);
        gotoPanelLine.add(textFieldGoTo);
        gotoPanel.add(gotoPanelLine);

        JPanel goToButtonsPanel = new JPanel();
        goToButtonsPanel.add(createButton("Go To", CommandEnum.GO_TO_OK, actionController));
        goToButtonsPanel.add(createButton("Cancel", CommandEnum.GO_TO_CANCEL, actionController));
        gotoPanel.add(goToButtonsPanel);
        goToDialog.add(gotoPanel);
    }

    public void okGoToDialog() {
        try {
            String textOfTextField = textFieldGoTo.getText();
            if (textOfTextField == null) return;
            int lineNumber = Integer.parseInt(textOfTextField);
            textArea.setCaretPosition(textArea.getLineStartOffset(lineNumber - 1));
            goToDialog.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "No such line", "Notepad-MVC",
                    JOptionPane.INFORMATION_MESSAGE, createIcon("./images/info.png"));
            String lastLineNumber = "" + textArea.getLineCount();
            textFieldGoTo.setText(lastLineNumber);
        }
    }

    public void hideGoToDialog() {
        goToDialog.setVisible(false);
    }

    public void showGoToDialog(ActionController actionController) {
        if (goToDialog == null) {
            initGoToDialog(actionController);
        }
        goToDialog.setVisible(true);
    }

    //Select all
    public void selectAll() {
        textArea.selectAll();
    }

    //Date time
    public void pasteCurrentDate() {
        textArea.insert(LocalTime.now().withNano(0).withSecond(0) +
                " " + LocalDate.now(), textArea.getCaretPosition());
    }
    /*---MenuEdit----End---*/

    /*---MenuFormat----Begin---*/
    private void initMenuFormat(ActionController actionController) {
        JMenu formatMenu = new JMenu("Format");
        formatMenu.setMnemonic('T');
        formatMenu.add(createMenuItem("Font...", createIcon("./images/font.png"),
                CommandEnum.FONT, actionController));

        menuBar.add(formatMenu);
    }

    public String getFontFamily() {
        return initFontFamily;
    }

    public int getFontSize() {
        return initFontSize;
    }
    /*---MenuFormat----End---*/

    /*---MenuView----Begin---*/
    private void initMenuView(ActionController actionController) {
        JMenu viewMenu = new JMenu("View");
        JMenu viewZoom = new JMenu("Zoom");
        viewZoom.setIcon(createIcon("./images/zoom.png"));

        viewItemZoomIn = createMenuItem("Zoom In", createIcon("./images/zoom-in.png"),
                CommandEnum.ZOOM_IN, actionController);
        viewItemZoomIn.setAccelerator(createKeyStroke(KeyEvent.VK_ADD));
        viewItemZoomIn.setEnabled(true);

        viewItemZoomOut = createMenuItem("Zoom Out", createIcon("./images/zoom-out.png"),
                CommandEnum.ZOOM_OUT, actionController);
        viewItemZoomOut.setAccelerator(createKeyStroke(KeyEvent.VK_SUBTRACT));
        viewItemZoomOut.setEnabled(true);

        viewItemZoomDefault = createMenuItem("Restore Default Zoom", createIcon("./images/zoom-default.png"),
                CommandEnum.ZOOM_DEFAULT, actionController);
        viewItemZoomDefault.setAccelerator(createKeyStroke(KeyEvent.VK_NUMPAD0));
        viewItemZoomDefault.setEnabled(false);

        statusBarBox = new JCheckBox("StatusBar");
        statusBarBox.setOpaque(false);
        statusBarBox.setFocusable(false);
        statusBarBox.setFont(statusBarBox.getFont().deriveFont(Font.PLAIN));
        statusBarBox.setPreferredSize(new Dimension(100, 20));
        statusBarBox.setIcon(createIcon("./images/status-bar-false.png"));
        statusBarBox.setSelectedIcon(createIcon("./images/status-bar-true.png"));
        statusBarBox.setSelected(false);

        statusBarBox.addActionListener(actionController);
        viewMenu.setMnemonic('V');
        viewMenu.add(viewZoom);
        viewMenu.addSeparator();
        viewZoom.add(viewItemZoomIn);
        viewZoom.add(viewItemZoomOut);
        viewZoom.addSeparator();
        viewZoom.add(viewItemZoomDefault);
        viewMenu.add(statusBarBox);

        menuBar.add(viewMenu);
    }

    // Zoom in and zoom out
    public void zoomIn() {
        if (canZoomIn()) {
            fontZoom = new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle(), textArea.getFont().getSize() + 2);
            textArea.setFont(fontZoom);
        }

    }

    public boolean canZoomIn() {
        if (fontZoom.getSize() > 48) {
            setViewItemZoomIn(false);
            return false;
        } else {
            setViewItemZoomIn(true);
            return true;
        }
    }

    public void setViewItemZoomIn(boolean active) {
        viewItemZoomIn.setEnabled(active);
    }

    public void zoomOut() {
        if (canZoomOut()) {
            int size = textArea.getFont().getSize();
            size = Math.max(size - 2, 8);
            fontZoom = new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle(), size);
            textArea.setFont(fontZoom);
        }
    }

    public boolean canZoomOut() {
        if (fontZoom.getSize() <= 8) {
            setViewItemZoomOut(false);
            return false;
        } else {
            setViewItemZoomOut(true);
            return true;
        }
    }

    public void setViewItemZoomOut(boolean active) {
        viewItemZoomOut.setEnabled(active);
    }

    public void zoomDefault() {
        if (canZoomDefault()) {
            textArea.setFont(new java.awt.Font(textArea.getFont().getFontName(), textArea.getFont().getStyle(),
                    initFontSize));

        }
    }

    public boolean canZoomDefault() {
        if (textArea.getFont().getSize() > 22 || textArea.getFont().getSize() < 22) {
            setViewItemZoomDefault(true);
            return true;
        } else {
            return false;
        }
    }

    public void setViewItemZoomDefault(boolean active) {
        viewItemZoomDefault.setEnabled(active);
    }

    //Status Bar
    public JCheckBox getStatusBarBox() {
        return statusBarBox;
    }

    //Status Panel
    private void initStatusPanel() {
        statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel();
        statusPanel.add(statusLabel);
        statusPanel.setVisible(false);
    }

    public void setStatusPanelToVisible(boolean visible) {
        statusPanel.setVisible(visible);
    }

    public void setLabelByTextAreaLines(int line, int column) {
        statusLabel.setText("  Line " + line + ", Column " + column);
    }

    /*---MenuView----End---*/

    /*---MenuHelp----Begin---*/
    private void initMenuHelp(ActionController actionController) {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        helpMenu.add(createMenuItem("About the program", createIcon("./images/info.png"),
                CommandEnum.ABOUT, actionController));

        menuBar.add(helpMenu);
    }

    public void getMessageAbout() {
        JOptionPane.showMessageDialog(frame,
                new MessageWithLink("<div>Notepad MVC Version Team №4.<div>" +
                        "<a href=\"\">See the development process</a>"), "Notepad: information",
                JOptionPane.INFORMATION_MESSAGE, createIcon("./images/main-icon.png"));
    }
    /*---MenuHelp----End---*/

    private void initToolBar(ActionController actionController) {
        toolBar = new JToolBar();
        toolBar.setOpaque(true);
        toolBar.setBackground(Color.DARK_GRAY);

        toolBar.add(createButton(createIcon("./images/create-large.png"), CommandEnum.CREATE,
                "Create file", actionController));
        toolBar.add(createButton(createIcon("./images/save-large.png"), CommandEnum.SAVE,
                "Save file", actionController));
        toolBar.add(createButton(createIcon("./images/open-large.png"), CommandEnum.OPEN,
                "Open file", actionController));
        toolBar.add(createButton(createIcon("./images/copy-large.png"), CommandEnum.COPY,
                "Copy", actionController));
        toolBar.add(createButton(createIcon("./images/cut-large.png"), CommandEnum.CUT,
                "Cut", actionController));
        toolBar.add(createButton(createIcon("./images/paste-large.png"), CommandEnum.PASTE,
                "Paste", actionController));
        toolBar.add(createButton(createIcon("./images/font-large.png"), CommandEnum.FONT,
                "Font format", actionController));
        toolBar.add(createButton(createIcon("./images/print-large.png"), CommandEnum.PRINT,
                "Print", actionController));
    }

    //---------------------------------------------------------------


    /*---Create MenuItem and Button----Begin---*/
    private JButton createButton(String name, CommandEnum command, ActionController actionController) {
        JButton jButton = new JButton(name);
        jButton.setActionCommand(command.toString());
        jButton.addActionListener(actionController);
        return jButton;
    }

    private JButton createButton(Icon icon, CommandEnum command, String tooltipText, ActionController actionController) {
        JButton jButton = new JButton(icon);
        jButton.setActionCommand(command.toString());
        jButton.addActionListener(actionController);
        jButton.setToolTipText(tooltipText);
        return jButton;
    }

    private JMenuItem createMenuItem(String name, Icon icon, CommandEnum command, ActionController actionController) {
        JMenuItem menuItem = new JMenuItem(name, icon);
        menuItem.setActionCommand(command.toString());
        menuItem.addActionListener(actionController);

        return menuItem;
    }

    private ImageIcon createIcon(String iconName) {
        return new ImageIcon(iconName);
    }

    private KeyStroke createKeyStroke(int event) {
        return KeyStroke.getKeyStroke(event,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    }
    /*---Create MenuItem and Button----End---*/
}