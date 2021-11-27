public class SelectAllText implements ActionNotepad {

    private final Viewer viewer;

    public SelectAllText(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.selectAll();
    }
}