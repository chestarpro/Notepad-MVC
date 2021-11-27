public class ZoomIn implements ActionNotepad {

    private final Viewer viewer;

    public ZoomIn(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.zoomIn();

    }
}