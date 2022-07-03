import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {

        String regStr = "\\s\\S+?\\s";

        String msg = " hello world ";

        Pattern pattern = Pattern.compile(regStr);

        Matcher matcher = pattern.matcher(msg);

        while (matcher.find())
        {
            System.out.println(1);
            System.out.println(matcher.group(0));
        }


    }
}
