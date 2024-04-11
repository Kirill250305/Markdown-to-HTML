package md2html;

import java.util.HashMap;
import java.util.Objects;

public abstract class Markings extends AbstractMarkupClass {
    String mark;
    StringBuilder sb2 = new StringBuilder();

    public Markings(String mark) {
        this.mark = mark;
    }

    final static HashMap<String, String> marks = new HashMap<>();
    final static HashMap<String, String> marks2 = new HashMap<>();


    static {

        marks.put("Emphasis1", "*");
        marks.put("Emphasis2", "_");
        marks.put("Strong1", "__");
        marks.put("Strong2", "**");
        marks.put("Strikeout1", "--");
        marks.put("Strikeout2", "--");
        marks.put("Code1", "`");
        marks.put("Code2", "`");

        marks2.put("Emphasis1", "<em>");
        marks2.put("Emphasis2", "</em>");
        marks2.put("Strong1", "<strong>");
        marks2.put("Strong2", "</strong>");
        marks2.put("Strikeout1", "<s>");
        marks2.put("Strikeout2", "</s>");
        marks2.put("Code1", "<code>");
        marks2.put("Code2", "</code>");


    }


    @Override
    public int parseMd(StringBuilder sb, int pointer) {
        while (pointer < sb.length()) {
            if (sb.substring(pointer).startsWith(marks.get(mark + "1")) || sb.substring(pointer).startsWith(marks.get(mark + "2"))) {
                return pointer + 1;
            }
            AbstractMarkupClass typeOfText;
            while (pointer < sb.length()) {
                if (!(pointer == 0 || sb.charAt(pointer - 1) != '\\')) {
                    typeOfText = new Text();
                    int pointer2 = typeOfText.parseMd(sb, pointer);
                    typeOfText.toHtml(sb2);
                    pointer = pointer2;
                } else {
                    if ((pointer < sb.length() - 1)
                            && (Objects.equals(sb.substring(pointer, pointer + 2), "__")
                            || (Objects.equals(sb.substring(pointer, pointer + 2), "**")))) {
                        pointer += 2;
                        if (mark.equals("Strong")) {
                            return pointer;
                        } else {
                            typeOfText = new Strong();
                            int pointer2 = typeOfText.parseMd(sb, pointer);
                            StringBuilder sb32 = new StringBuilder();
                            typeOfText.toHtml(sb32);
                            sb2.append(sb32);
                            pointer = pointer2;
                        }
                    } else if ((pointer < sb.length())
                            && ((sb.charAt(pointer) == '_') || (sb.charAt(pointer) == '*'))) {
                        pointer++;
                        if (mark.equals("Emphasis")) {
                            return pointer;
                        } else {
                            typeOfText = new Emphasis();
                            int pointer2 = typeOfText.parseMd(sb, pointer);
                            StringBuilder sb32 = new StringBuilder();
                            typeOfText.toHtml(sb32);
                            sb2.append(sb32);
                            pointer = pointer2;
                        }
                    } else if ((pointer != sb.length() - 1)
                            && Objects.equals(sb.substring(pointer, pointer + 2), "--")) {
                        pointer += 2;
                        if (mark.equals("Strikeout")) {
                            return pointer;
                        } else {
                            typeOfText = new Strikeout();
                            int pointer2 = typeOfText.parseMd(sb, pointer);
                            StringBuilder sb32 = new StringBuilder();
                            typeOfText.toHtml(sb32);
                            sb2.append(sb32);
                            pointer = pointer2;
                        }
                    } else if ((pointer != sb.length() - 1) && (pointer != sb.length() - 2)
                            && Objects.equals(sb.substring(pointer, pointer + 3), "```")) {
                        pointer += 3;
                        typeOfText = new PreCode();
                        int pointer2 = typeOfText.parseMd(sb, pointer);
                        StringBuilder sb32 = new StringBuilder();
                        typeOfText.toHtml(sb32);
                        sb2.append(sb32);
                        pointer = pointer2;
                    } else if (sb.charAt(pointer) == '`') {
                        pointer++;
                        if (mark.equals("Code")) {
                            return pointer;
                        } else {
                            typeOfText = new Code();
                            int pointer2 = typeOfText.parseMd(sb, pointer);
                            StringBuilder sb32 = new StringBuilder();
                            typeOfText.toHtml(sb32);
                            sb2.append(sb32);
                            pointer = pointer2;
                        }
                    } else {
                        typeOfText = new Text();
                        int pointer2 = typeOfText.parseMd(sb, pointer);
                        typeOfText.toHtml(sb2);
                        pointer = pointer2;
                    }
                }
            }
        }
        return pointer;
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(marks2.get(this.mark + "1"));
        sb.append(sb2);
        sb.append(marks2.get(this.mark + "2"));
    }
}