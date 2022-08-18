
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



 /*
  Программа устанавливает приоритетное(дорогое) время посещения кинозала и заполняет зал сначала в в рамках приоритетного времени, а потом дозаполняет по обычному времени.
  calculateFloatingFirstActivity - программа устанавливает первый элемент (левая граница) приоритетного отрезка времени. В этом ей помогает метод checkIfElementOnRightSide,
  который проверяет, является ли элемент элементом правой части, включая те, что находятся в двух временных отрезках одновременно. Метод calculateSimplyFirstActivity занимается
  вычислением первого элемента НЕприоритетной т.е. левой части массива. Метод понадобился для того чтобы избежать пересечения первого элемента левой части с правой.
  Метод selectActivity непосредственно заполняет сначала приоритетный отрезок времени, а потом дозаполняет левую часть.
  */

public class HallSelection {

    // проверяем принадлежит ли элемент к правой части, т.е. после 13:00 или скольки там...
    public boolean checkIfElementOnRightSide(Activity activity, int switchTime) {
        if (activity.getStart() <= switchTime && activity.getEnd() > switchTime)
            return Math.abs((activity.getStart() - switchTime)) <= Math.abs((activity.getEnd() - switchTime));
        else return false;
    }

    // вычисляем первый элемент для правой части
    public Activity calculateFloatingFirstActivity(List<Activity> list, int switchTime) {
        System.out.println("calculateFirstElementForRightSide for " + switchTime);
        Activity firstActivity = new Activity();
        int firstElement = 24;
        for (Activity activity : list) {
            if (checkIfElementOnRightSide(activity, switchTime)) {
                if (activity.getEnd() <= firstElement) {
                    firstElement = activity.getEnd();
                    firstActivity = activity;
                }
            } else if (activity.getStart() > switchTime)
                if (activity.getEnd() <= firstElement) {
                    firstElement = activity.getEnd();
                    firstActivity = activity;
                }
        }
        return firstActivity;
    }

    // вычисляем первый элемент для левой части с условием что он не пересечётся с первым(или больше) элементом правой части
    public Activity calculateSimplyFirstActivity(List<Activity> list, int rightBorder) {
        Activity firstActivity = new Activity();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEnd() <= rightBorder) {
                firstActivity = list.get(i);
                break;
            }
        }
        return firstActivity;
    }

    public void selectActivity(List<Activity> list, int switchTime) {
        list.sort(Comparator.comparing(Activity::getStart));
        Activity first = calculateFloatingFirstActivity(list, switchTime); // первый опорный элемент будет плавающая левая граница правой части списка
        System.out.println("First = " + first + " switchTime " + switchTime);

        List<Activity> result = new ArrayList<>();
        result.add(first);

        int endTime = first.getEnd();

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getStart() >= endTime) {
                Activity current = list.get(i);
                result.add(current);
                endTime = current.getEnd();
            }
        }

        Activity second = calculateSimplyFirstActivity(list, first.getStart());  // второй опорный элемент будет левая граница левой части

        if (second.getEnd() != 0) {

            result.add(0, second);

            int count = 1; // индекс чтобы засунуть переменную из левой части в начало списка (в массиве уже имеются элементы из правой части)
            endTime = second.getEnd();
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getEnd() <= first.getStart()) {
                    if (list.get(i).getStart() >= endTime) {
                        Activity current = list.get(i);
                        result.add(count, current);
                        count++;
                        endTime = current.getEnd();
                    }
                }
            }
        }
        printList(result);
    }

    private void printList(List<Activity> list) {
        for (Activity a : list) {
            System.out.println(a.getName());
        }
    }

}