package md2html;

public class Header implements MarkupInterface {

    Paragraph par = new Paragraph();

    int pointer = 0;

    @Override
    public int parseMd(StringBuilder sb, int level) {
        pointer = level;
        pointer = par.parseMd(sb, pointer);
        return pointer;
    }

    @Override
    public void toHtml(StringBuilder sb) {
        int i = 3;
        par.toHtml(sb);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(sb);
        while (sb.charAt(i) == '#') {
            i++;
        }
        sb.setLength(0);
        sb.append("<h");
        sb.append(i - 3);
        sb.append(">");
        if (sb2.toString().endsWith(System.lineSeparator() + "</p>")) {
            sb.append(sb2.substring(i + 1, sb2.length() - 4 - System.lineSeparator().length()));
        } else {
            sb.append(sb2.substring(i + 1, sb2.length() - 4));
        }
        sb.append("</h");
        sb.append(i - 3);
        sb.append(">");
    }
}
