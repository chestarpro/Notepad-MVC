public class Exit implements ActionNotepad {

    private final MyFileUtil fileUtil;

    public Exit(MyFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public void doAction() {
        if (fileUtil.checkExitAnswer()) {
            return;
        }
        System.exit(0);
    }
}