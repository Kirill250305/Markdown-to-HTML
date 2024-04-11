package md2html;

import java.util.HashMap;

public class Text extends AbstractMarkupClass {
    StringBuilder str = new StringBuilder();

    char chr = System.lineSeparator().charAt(0);

    char chr2 = System.lineSeparator().charAt(System.lineSeparator().length() - 1);
    public Text() {}

    final static HashMap<String, String> marks1 = new HashMap<>();

    static {
        marks1.put("```", "PreCode");
        marks1.put("__", "Strong");
        marks1.put("**", "Strong");
        marks1.put("--", "Strikeout");
        marks1.put("`", "Code");
        marks1.put("*", "Emphasis");
        marks1.put("_", "Emphasis");
    }

    private String special(Character chr) {
        if (chr == '&') {
            return "&amp;";
        }
        if (chr == '<') {
            return "&lt;";
        }
        if (chr == '>') {
            return  "&gt;";
        }
        if (chr == '\\') {
            return "";
        }
        return chr.toString();
    }
    @Override
    public int parseMd(StringBuilder sb, int pointer) {
        while ((pointer < sb.length()) && ((pointer > 0) && (sb.charAt(pointer - 1) == '\\')
        || !(
                (pointer < sb.length()) && marks1.containsKey(sb.substring(pointer, pointer + 1))
                && ((pointer == 0 || sb.charAt(pointer - 1) != ' ' && sb.charAt(pointer - 1) != chr2)
                        || (pointer + 1 >= sb.length() || sb.charAt(pointer + 1) != ' ' && sb.charAt(pointer + 1) != chr))
                || (pointer + 1 < sb.length()) && marks1.containsKey(sb.substring(pointer, pointer + 2))
                && ((pointer == 0 || sb.charAt(pointer - 1) != ' ' && sb.charAt(pointer - 1) != chr2)
                        || (pointer + 2 >= sb.length() || sb.charAt(pointer + 2) != ' ' && sb.charAt(pointer + 2) != chr))
                || (pointer + 2 < sb.length()) && marks1.containsKey(sb.substring(pointer, pointer + 3))
                )))
        {
            str.append(special(sb.charAt(pointer)));
            pointer++;
        }
        return pointer;
    }
    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(this.str);
    }
}
