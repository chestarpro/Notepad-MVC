public class CreateFile implements ActionNotepad {

    private final MyFileUtil fileUtil;

    public CreateFile(MyFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public void doAction() {
        fileUtil.createFile();
    }
}