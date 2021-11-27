public class PasteText implements ActionNotepad {

    private final Viewer viewer;

    public PasteText(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.pasteTextArea();
    }
}