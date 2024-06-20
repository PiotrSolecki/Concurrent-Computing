import java.util.ArrayList;
import java.util.Arrays;

public class ThreadClass extends Thread {
    private ArrayList<Integer> unorderedList;
    private ArrayList<Integer> sortedList;
    private int threadNumber;

    public ThreadClass(ArrayList<Integer> startingList, int threadNumber) {
        this.unorderedList = startingList;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        sortedList = bubbleSort(unorderedList);
        System.out.println("- Thread " + threadNumber + " sorted chunk");
    }

    private ArrayList<Integer> bubbleSort(ArrayList<Integer> startingList) {
        int listLength = startingList.size();
        Integer[] values = startingList.toArray(new Integer[0]);
        for (int i = 0; i < listLength - 1; i++) {
            for (int j = 0; j < listLength - i - 1; j++) {
                if (values[j] > values[j + 1]) {
                    int temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }
            }
        }
        return new ArrayList<>(Arrays.asList(values));
    }

    public ArrayList<Integer> getSortedList() {
        return sortedList;
    }
}