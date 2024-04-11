package md2html;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Paragraph implements MarkupInterface {

    List<AbstractMarkupClass> list = new LinkedList<>();

    char chr = System.lineSeparator().charAt(0);

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

    @Override
    public int parseMd(StringBuilder sb, int pointer) {
        while (pointer < sb.length()) {
            if (pointer == 0 || sb.charAt(pointer - 1) != '\\') {
                AbstractMarkupClass typeOfText = new Text();
                if (pointer + 2 < sb.length()
                        && marks1.containsKey(sb.substring(pointer, pointer + 3))
                ) {
                    pointer += 3;
                    typeOfText = new PreCode();
                } else if (pointer + 1 < sb.length()
                        && marks1.containsKey(sb.substring(pointer, pointer + 2))
                        && (pointer + 2 >= sb.length() || sb.charAt(pointer + 2) != ' ' && sb.charAt(pointer + 2) != chr)
                ) {
                    if (marks1.get(sb.substring(pointer, pointer + 2)).equals("Strong")) {
                        typeOfText = new Strong();
                    } else {
                        typeOfText = new Strikeout();
                    }
                    pointer += 2;
                } else if (pointer < sb.length()
                        && marks1.containsKey(sb.substring(pointer, pointer + 1))
                        && (pointer + 1 >= sb.length() || sb.charAt(pointer + 1) != ' ' && sb.charAt(pointer + 1) != chr)
                ) {
                    if (marks1.get(sb.substring(pointer, pointer + 1)).equals("Emphasis")) {
                        typeOfText = new Emphasis();
                    } else {
                        typeOfText = new Code();
                    }
                    pointer += 1;
                }
                pointer = typeOfText.parseMd(sb, pointer);
                list.add(typeOfText);
                typeOfText = new Text();
                pointer = typeOfText.parseMd(sb, pointer);
                list.add(typeOfText);
            }
        }
        return pointer;
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append("<p>");
        for (AbstractMarkupClass elements : this.list) {
            elements.toHtml(sb);
        }
        if (sb.toString().endsWith(System.lineSeparator())) {
            sb.setLength(sb.length() - System.lineSeparator().length());
        }
        sb.append("</p>");
    }
}
