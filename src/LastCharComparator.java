import java.util.Comparator;

public class LastCharComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        return s1.substring(s1.length()-1).compareTo(s2.substring(s2.length()-1));
    }
}
