public class OpenFile implements ActionNotepad {

    private final MyFileUtil fileUtil;

    public OpenFile(MyFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public void doAction() {
        fileUtil.openFile();
    }
}