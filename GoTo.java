public class GoTo implements ActionNotepad {

    private final Viewer viewer;
    private final ActionController actionController;

    public GoTo(Viewer viewer, ActionController actionController) {
        this.viewer = viewer;
        this.actionController = actionController;
    }

    @Override
    public void doAction() {
        viewer.showGoToDialog(actionController);
    }
}
