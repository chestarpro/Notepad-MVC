public class SaveFile implements ActionNotepad {

    private final MyFileUtil fileUtil;

    public SaveFile(MyFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public void doAction() {
        fileUtil.saveText();
    }
}
