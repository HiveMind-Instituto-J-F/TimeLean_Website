package hivemind.hivemindweb.Tool;

// Esta classe e temporaria mais seu conceito deve ser mantido sendo a Idea de manter funçoes statics ultilirias globlais   

import java.util.ArrayList;
import java.util.Arrays;

public class Tool {
    public static boolean verifySQL(String input) {
        input = input.toLowerCase();
        ArrayList<String> sqlKeywords = new ArrayList<>(
                Arrays.asList(
                        "truncate",
                        "delete",
                        "drop",
                        "insert",
                        "update",
                        "1=1",
                        "--",
                        ";",
                        " or ",
                        " and "
                )
        );
        for (String keyword : sqlKeywords) {
            if (input.contains(keyword)) {
                return true; // input perigoso
            }
        }
        return false;
    }
}
