public class ZoomOut implements ActionNotepad {

    private final Viewer viewer;

    public ZoomOut(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.zoomOut();
    }
}