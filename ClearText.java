public class ClearText implements ActionNotepad {

    private final Viewer viewer;

    public ClearText(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.deleteTextArea();
    }
}