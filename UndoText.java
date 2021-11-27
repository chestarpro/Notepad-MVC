public class UndoText implements ActionNotepad{

    private final Viewer viewer;

    public UndoText(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.undoDocumentText();
    }
}
