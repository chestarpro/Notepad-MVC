import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileUtil {

    private final Viewer viewer;
    private String filePath;

    public MyFileUtil(Viewer viewer) {
        this.viewer = viewer;
        filePath = null;
    }

    public int getCurrentAnswerOpenFile() {
        int answer = viewer.getConfirmMessageToSave();
        if (answer == 0) {
            if (!saveText()) {
                return 3;
            }
        }
        return answer;
    }

    public boolean saveText() {
        String textFromTextArea = viewer.getTextFromTextArea();
        File fileName = getFileNameForSave(viewer, filePath);
        if (fileName == null) {
            return false;
        }
        saveTextToFile(textFromTextArea, fileName);
        return true;
    }

    public void saveFile(String textFromTextArea, File fileName) throws IOException {
        PrintWriter printWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            printWriter = new PrintWriter(fileWriter);
            printWriter.print(textFromTextArea);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    public File getFileNameForSave(Viewer viewer, String filePath) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        if (filePath == null) {
            fileChooser.setSelectedFile(new File("New document file.txt"));
        } else {
            return new File(filePath);
        }
        int answer = viewer.getDialogSave(fileChooser);
        if (answer == 0) {
            if (fileChooser.getSelectedFile().exists()) {
                answer = viewer.getConfirmMessageToReplace();
                if (answer != 0) {
                    return null;
                }
            }
            return fileChooser.getSelectedFile();
        }
        return null;
    }


    public void saveTextToFile(String textFromTextArea, File fileName) {
        filePath = fileName.getPath();
        try {
            saveFile(textFromTextArea, fileName);
            viewer.setChangedTextArea(false);
            viewer.setFileNameToFrame(fileName.getName());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public void createFile() {
        if (checkExitAnswer()) {
            return;
        }
        filePath = null;
        viewer.updateTextArea("");
        viewer.setChangedTextArea(false);
        viewer.setUntitledFileNameToFrame();
    }

    public boolean checkExitAnswer() {
        int answer = getRequestForChangedSaveFile();
        return answer == 2 || answer == -1;
    }

    public int getRequestForChangedSaveFile() {
        int answer = 1;
        if (filePath != null) {
            if (viewer.isChangedTextArea()) {
                answer = getCurrentAnswerOpenFile();
            }
        } else if (!viewer.getTextFromTextArea().isEmpty()) {
            answer = getCurrentAnswerOpenFile();
            if (answer == 1) {
                answer = 0;
            } else if (answer == 3) {
                answer = 2;
            }

        }
        return answer;
    }

    public String getFileName() {
        if (filePath == null) {
            return "file without a name";
        }
        return filePath;
    }

    public void openFile() {
        if (checkExitAnswer()) {
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        while (true) {
            int answer = viewer.getDialogOpen(fileChooser);
            if (answer == 1) {
                return;
            }
            if (!fileChooser.getSelectedFile().exists()) {
                viewer.getMessageErrorOpenFile();
                continue;
            }
            viewer.setFileNameToFrame(fileChooser.getSelectedFile().getName());
            if (answer == 0) {
                filePath = readFile(fileChooser.getSelectedFile());
                return;
            }
        }
    }

    public String readFile(File file) {
        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(new FileReader(file));
            List<String> listLLineText = new ArrayList<String>();
            String line;
            while ((line = inputStream.readLine()) != null) {
                listLLineText.add(line);
            }
            String text = String.join("\n", listLLineText);
            viewer.updateTextArea(text);
            viewer.setChangedTextArea(false);
            listLLineText.clear();
            return file.getPath();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void saveAsText() {
        String textFromTextArea = viewer.getTextFromTextArea();
        File fileName = getFileNameForSave(viewer, null);
        if (fileName == null) {
            return;
        }
        saveTextToFile(textFromTextArea, fileName);
    }
}