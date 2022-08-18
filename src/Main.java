import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Activity> list = new ArrayList<>();
        list.add(new Activity("b", 1, 7));
        list.add(new Activity("w", 1, 2));
        list.add(new Activity("a", 2, 3));
        list.add(new Activity("d", 5, 9));
        list.add(new Activity("c", 2, 4));
        list.add(new Activity("f", 7, 8));
        list.add(new Activity("g", 8, 10));
        list.add(new Activity("e", 4, 6));
        list.add(new Activity("k", 3, 5));


        HallSelection hallSelection = new HallSelection();
        hallSelection.selectActivity(list,4);


    }
}