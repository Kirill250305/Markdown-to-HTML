package md2html;

import java.util.Objects;

public class PreCode extends AbstractMarkupClass {
    StringBuilder str = new StringBuilder();

    public PreCode() {}

    @Override
    public int parseMd(StringBuilder sb, int pointer) {
        while (pointer + 2 < sb.length() && !Objects.equals(sb.substring(pointer, pointer + 3),"```")) {
            str.append(sb.charAt(pointer));
            pointer++;
        }
        return pointer + 3;
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append("<pre>");
        sb.append(this.str);
        sb.append("</pre>");
    }
}