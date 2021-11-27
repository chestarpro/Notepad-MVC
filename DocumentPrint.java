import java.util.List;

public class DocumentPrint implements ActionNotepad {

    private final Viewer viewer;
    private List<String> textLinesList;

    public DocumentPrint(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doAction() {
        try {
            String textPageNumber = "Page ";
            textLinesList = viewer.getListTextFromTextArea();

            PrintFile printFile = new PrintFile(textLinesList, viewer.getFont(), textPageNumber);
            printFile.showDialog();
            if (printFile.isPrinted()) {
                viewer.showDialogFinishPrintDocument();
            }
        } catch (TextNotFoundException | FontNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            textLinesList.clear();
            textLinesList = null;
        }
    }
}
