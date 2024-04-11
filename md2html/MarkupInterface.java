package md2html;

public interface MarkupInterface {
    int parseMd(StringBuilder sb, int pointer);

    void toHtml(StringBuilder sb);
}
