public class DateAsText implements ActionNotepad {

    private final Viewer viewer;

    public DateAsText(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.pasteCurrentDate();
    }
}