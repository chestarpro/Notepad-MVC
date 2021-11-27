public class ZoomDefault implements ActionNotepad {

    private final Viewer viewer;

    public ZoomDefault(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.zoomDefault();
    }
}