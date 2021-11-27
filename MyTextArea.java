import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyTextArea extends JTextArea {

    public List<String> getListText() {
        Document doc = getDocument();
        List<String> listTxt = new ArrayList<>();
        try {
            String txt = doc.getText(0, doc.getLength());
            listTxt.addAll(Arrays.asList(txt.split("\n")));
        } catch (BadLocationException e) {
            System.err.println(e.getMessage());
        }
        return listTxt;
    }
}