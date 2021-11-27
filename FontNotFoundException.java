public class FontNotFoundException extends Exception {
    private static final long serialVersionUID = -447856973823710492L;

    public FontNotFoundException() {
        super();
    }

    public FontNotFoundException(String s) {
        super(s);
    }

    private FontNotFoundException(String path, String reason) {
        super(path + ((reason == null)
                ? ""
                : " (" + reason + ")"));
    }
}
