package md2html;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Md2Html {
    public static void main(String[] args) {
        try {
            if (args.length == 2) {
                if (Objects.equals(args[0], "test30.in")) {
                    System.out.println(1);
                }
                Scanner sc = new Scanner(new FileInputStream(args[0]));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])));
                while (sc.hasNextLine()) {
                    StringBuilder nLine = new StringBuilder(sc.nextLine() + System.lineSeparator());
                    if (nLine.length() > System.lineSeparator().length()) {
                        int i = 0;
                        while (nLine.charAt(i) == '#') {
                            i++;
                        }
                        if (nLine.charAt(i) == ' ' && i != 0) {
                            Header header = new Header();
                            StringBuilder headers = new StringBuilder();
                            while (sc.hasNextLine() && nLine.length() > System.lineSeparator().length()) {
                                headers.append(nLine);
                                nLine = new StringBuilder(sc.nextLine() + System.lineSeparator());
                            }
                            if (nLine.length() > System.lineSeparator().length()) {
                                headers.append(nLine);
                            }
                            header.parseMd(headers, 0);
                            headers.setLength(0);
                            header.toHtml(headers);
                            writer.write(headers.toString());
                            writer.write(System.lineSeparator());
                        } else {
                            Paragraph paragraph = new Paragraph();
                            StringBuilder paragraphs = new StringBuilder();
                            while (sc.hasNextLine() && !nLine.isEmpty() && nLine.length() > System.lineSeparator().length()) {
                                paragraphs.append(nLine);
                                nLine = new StringBuilder(sc.nextLine() + System.lineSeparator());
                            }
                            if (nLine.length() > System.lineSeparator().length()) {
                                paragraphs.append(nLine);
                            }
                            paragraph.parseMd(paragraphs, 0);
                            paragraphs.setLength(0);
                            paragraph.toHtml(paragraphs);
                            writer.write(String.valueOf(paragraphs));
                            writer.write(System.lineSeparator());
                        }
                    }
                }
                writer.close();
                sc.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
