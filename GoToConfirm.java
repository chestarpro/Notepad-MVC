public class GoToConfirm implements ActionNotepad {

    private final Viewer viewer;

    public GoToConfirm(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.okGoToDialog();
    }
}
