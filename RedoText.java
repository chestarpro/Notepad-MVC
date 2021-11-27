public class RedoText implements ActionNotepad{

    private final Viewer viewer;

    public RedoText(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.redoDocumentText();
    }
}
