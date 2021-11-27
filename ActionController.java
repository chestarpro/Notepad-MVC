import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ActionController implements ActionListener {
    private final Viewer viewer;
    private final MyFileUtil fileUtil;
    private Map<String, ActionNotepad> map;

    public ActionController(Viewer viewer, MyFileUtil fileUtil) {
        this.viewer = viewer;
        this.fileUtil = fileUtil;
        initializeMap();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        doAction(command);
    }

    private void doAction(String command) {
        if (map.containsKey(command)) {
            map.get(command).doAction();
        }
        //Status Bar
        viewer.setStatusPanelToVisible(viewer.getStatusBarBox().isSelected());

        viewer.setViewItemZoomIn(viewer.canZoomIn());
        viewer.setViewItemZoomOut(viewer.canZoomOut());
        viewer.setViewItemZoomDefault(viewer.canZoomDefault());
    }

    private void initializeMap() {
        if (map == null) {
            map = new HashMap<>();
        }

        initializeMap(CommandEnum.CREATE.toString(), new CreateFile(fileUtil));
        initializeMap(CommandEnum.OPEN.toString(), new OpenFile(fileUtil));
        initializeMap(CommandEnum.SAVE.toString(), new SaveFile(fileUtil));
        initializeMap(CommandEnum.SAVE_AS.toString(), new SaveAsFile(fileUtil));
        initializeMap(CommandEnum.PRINT.toString(), new DocumentPrint(viewer));
        initializeMap(CommandEnum.EXIT.toString(), new Exit(fileUtil));

        initializeMap(CommandEnum.UNDO.toString(), new UndoText(viewer));
        initializeMap(CommandEnum.REDO.toString(), new RedoText(viewer));
        initializeMap(CommandEnum.COPY.toString(), new CopyText(viewer));
        initializeMap(CommandEnum.PASTE.toString(), new PasteText(viewer));
        initializeMap(CommandEnum.CUT.toString(), new CutText(viewer));
        initializeMap(CommandEnum.CLEAR.toString(), new ClearText(viewer));
        initializeMap(CommandEnum.SELECT_ALL.toString(), new SelectAllText(viewer));
        initializeMap(CommandEnum.FIND.toString(), new FindDialog(viewer));
        initializeMap(CommandEnum.GO_TO.toString(), new GoTo(viewer, this));
        initializeMap(CommandEnum.GO_TO_OK.toString(), new GoToConfirm(viewer));
        initializeMap(CommandEnum.GO_TO_CANCEL.toString(), new GoToCancel(viewer));
        initializeMap(CommandEnum.DATE.toString(), new DateAsText(viewer));
        initializeMap(CommandEnum.FONT.toString(), new FontDialogWindow(viewer));
        initializeMap(CommandEnum.ABOUT.toString(), new AboutDialogWindow(viewer));
        initializeMap(CommandEnum.ZOOM_IN.toString(), new ZoomIn(viewer));
        initializeMap(CommandEnum.ZOOM_OUT.toString(), new ZoomOut(viewer));
        initializeMap(CommandEnum.ZOOM_DEFAULT.toString(), new ZoomDefault(viewer));
    }

    private void initializeMap(String command, ActionNotepad actionNotepad) {
        if (map != null) {
            map.put(command, actionNotepad);
        }
    }
}