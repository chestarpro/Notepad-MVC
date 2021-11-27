public class CutText implements ActionNotepad {

    private final Viewer viewer;

    public CutText(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.cutTextArea();
    }
}