import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;

public class PrintFile implements Printable {
    private int[] pageBreaks;
    private List<String> textLinesList;
    private final Font font;
    private final Font teamFontName;
    private final Font fontPageNumber;
    private final String textPageNumber;
    private final String textNameTeam;
    private boolean aDocumentHasBeenPrinted;
    private FontMetrics metrics;

    public PrintFile(List<String> textLinesList, Font font, String textPageNumber) throws
            TextNotFoundException, FontNotFoundException {
        if (textLinesList == null) {
            throw new TextNotFoundException("Invalid text content");
        }
        if (font == null) {
            throw new FontNotFoundException("Invalid font object");
        }

        this.textPageNumber = textPageNumber;
        textNameTeam = "Intern Labs Team #4";
        fontPageNumber = new Font("Consolas", Font.PLAIN, 9);
        teamFontName = new Font("Vladimir Script", Font.BOLD, 9);
        this.textLinesList = textLinesList;
        this.font = font;
    }

    private void initTextLines(int pageWidth) {
        for (int i = 0; i < textLinesList.size(); i++) {
            int strWidth = metrics.stringWidth(textLinesList.get(i));
            if (pageWidth <= strWidth) {
                // Checking for spaces
                boolean checkSpace = !textLinesList.get(i).contains(" ");
                do {
                    if (textLinesList.get(i) == null) {
                        textLinesList.remove(i);
                        i = i - 1;
                        break;
                    }
                    if (checkSpace) {
                        textLinesList.add(i + 1, separateLine(textLinesList.get(i), pageWidth, i));
                    } else {
                        textLinesList.add(i + 1, separateLineByWords(textLinesList.get(i), pageWidth, i));
                    }
                    i = i + 1;

                    strWidth = metrics.stringWidth(textLinesList.get(i));
                    checkSpace = !textLinesList.get(i).contains(" ");
                } while (pageWidth <= strWidth);
            }
        }
    }

    private String separateLineByWords(String line, int pageWidth, int pos) {
        int index = 0;
        int lineWidth = -1;

        // Finding a position to trim a line
        for (int i = 1; i <= line.length(); i++) {
            lineWidth = getLineWidth(line, i);

            if (pageWidth <= lineWidth) {
                break;
            }
            char symbol = line.charAt(i);
            if (symbol == ' ') {
                index = i;
            }
        }

        //trim a line
        if (index == 0) {
            line = separateLine(line, pageWidth, pos);
        } else {
            String newLine = line.substring(0, index);
            textLinesList.set(pos, newLine);
            if (index == line.length()) {
                return null;
            }
            line = line.substring(index + 1);
        }

        return line;
    }

    private String separateLine(String line, int pageWidth, int pos) {
        int index = 0;
        int lineWidth = -1;

        // Finding a position to trim a line
        for (index = 0; index <= line.length(); index++) {
            lineWidth = getLineWidth(line, index);
            if (pageWidth <= lineWidth) {
                break;
            }
        }
        if (pageWidth < lineWidth) {
            index = index - 1;
        }

        //trim a line
        String newLine = line.substring(0, index);
        textLinesList.set(pos, newLine);
        line = line.substring(index);

        return line;
    }

    private int getLineWidth(String line, int index) {
        char[] symbols = new char[index];
        line.getChars(0, index, symbols, 0);
        int lineWidth = metrics.charsWidth(symbols, 0, index);

        symbols = null;
        return lineWidth;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        metrics = g.getFontMetrics(font);
        int x = 50;
        int y = 50;

        // Height of 1 row
        int lineHeight = metrics.getHeight();

        // Page margins on the sides
        int paddingWidthPage = x * 2;
        int pageWidth = (int) (pf.getImageableWidth()) - paddingWidthPage;
        int pageHeight = (int) pf.getImageableHeight() - (y * 2);

        // Initializing the print
        if (pageBreaks == null) {
            // Text processing
            initTextLines(pageWidth);

            // Count of lines per page
            int linesPerPage = pageHeight / lineHeight;

            // Page count
            int numBreaks = (textLinesList.size() - 1) / linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b = 0; b < numBreaks; b++) {
                pageBreaks[b] = (b + 1) * linesPerPage;
            }
        }
        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        //Processing print
        metrics = g.getFontMetrics(fontPageNumber);

        int pageNumberX = (int) pf.getImageableWidth() - metrics.stringWidth(textPageNumber) - x;
        int pageNumberY = (int) pf.getImageableHeight() - y / 2;

        //Erkin baike - This is for you :)
        //g.setColor(Color.BLUE);
        //g.drawRect(x, y, pageWidth, heightPage);

        //print team name
        g.setColor(Color.BLUE);
        g.setFont(teamFontName);
        g.drawString(textNameTeam, x, pageNumberY);

        //print page number
        g.setColor(java.awt.Color.BLUE);
        g.setFont(fontPageNumber);
        g.drawString(textPageNumber + (pageIndex + 1), pageNumberX, pageNumberY);

        //print text lines
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
        int end = (pageIndex == pageBreaks.length)
                ? textLinesList.size() : pageBreaks[pageIndex];
        for (int line = start; line < end; line++) {
            y += lineHeight;
            g.drawString(textLinesList.get(line), x, y);
        }
        return PAGE_EXISTS;
    }

    public void showDialog() {
        PrinterJob job = PrinterJob.getPrinterJob();
        if (job == null) {
            return;
        }
        job.setPrintable(this);
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
                aDocumentHasBeenPrinted = true;
            } catch (PrinterException e) {
                System.err.println(e.getMessage());
            } finally {
                textLinesList.clear();
                textLinesList = null;
                pageBreaks = null;
                metrics = null;
            }
        }
    }

    public boolean isPrinted() {
        return aDocumentHasBeenPrinted;
    }
}