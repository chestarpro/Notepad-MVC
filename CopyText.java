public class CopyText implements ActionNotepad {

    private final Viewer viewer;

    public CopyText(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.copyTextArea();
    }
}