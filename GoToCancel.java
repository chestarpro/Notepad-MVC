public class GoToCancel implements ActionNotepad {

    private final Viewer viewer;

    public GoToCancel(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.hideGoToDialog();
    }
}
