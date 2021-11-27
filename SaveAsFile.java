public class SaveAsFile implements ActionNotepad {

    private final MyFileUtil fileUtil;

    public SaveAsFile(MyFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public void doAction() {
        fileUtil.saveAsText();
    }
}
