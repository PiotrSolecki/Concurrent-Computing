import java.util.ArrayList;
import java.util.Arrays;

public class ThreadClass extends Thread {
    private ArrayList<Integer> unorderedList;
    private ArrayList<Integer> sortedList;

    public ThreadClass(ArrayList<Integer> startingList) {
        this.unorderedList = startingList;
    }

    @Override
    public void run() {
        sortedList = bubbleSort(unorderedList);
    }

    private ArrayList<Integer> bubbleSort(ArrayList<Integer> startingList) {
        int arrayLength = startingList.size();
        Integer[] array = startingList.toArray(new Integer[0]);
        for (int i = 0; i < arrayLength - 1; i++) {
            for (int j = 0; j < arrayLength - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return new ArrayList<>(Arrays.asList(array));
    }

    public ArrayList<Integer> getSortedList() {
        return sortedList;
    }
}
