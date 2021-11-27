public class TextNotFoundException extends Exception {

    private static final long serialVersionUID = -897856973823710492L;

    public TextNotFoundException() {
        super();
    }

    public TextNotFoundException(String s) {
        super(s);
    }

    private TextNotFoundException(String path, String reason) {
        super(path + ((reason == null)
                ? ""
                : " (" + reason + ")"));
    }
}
