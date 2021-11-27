public class AboutDialogWindow implements ActionNotepad {

    private final Viewer viewer;

    public AboutDialogWindow(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        viewer.getMessageAbout();
    }
}