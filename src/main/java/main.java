import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main {
    public static void main(String[] args) throws IOException {

        System.out.println("\n<<<   Sorting 1000 number file   >>>");
        sorting("numbers1k.txt", "sorted1k.txt", 1, 1000);
        sorting("numbers1k.txt", "sorted1k.txt", 2, 1000);
        sorting("numbers1k.txt", "sorted1k.txt", 3, 1000);
        sorting("numbers1k.txt", "sorted1k.txt", 4, 1000);

        System.out.println("\n<<<   Sorting 10000 number file   >>>");
        sorting("numbers10k.txt", "sorted10k.txt", 1, 10000);
        sorting("numbers10k.txt", "sorted10k.txt", 2, 10000);
        sorting("numbers10k.txt", "sorted10k.txt", 3, 10000);
        sorting("numbers10k.txt", "sorted10k.txt", 4, 10000);

        System.out.println("\n<<<   Sorting 20000 number file   >>>");
        sorting("numbers20k.txt", "sorted20k.txt", 1, 20000);
        sorting("numbers20k.txt", "sorted20k.txt", 2, 20000);
        sorting("numbers20k.txt", "sorted20k.txt", 3, 20000);
        sorting("numbers20k.txt", "sorted20k.txt", 4, 20000);

    }

    private static void generateNumbersFile(String filename, int numberCount) throws IOException {
        Random random = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < numberCount; i++) {
                writer.write((random.nextInt(2 * 1000000) - 1000000) + "\n");
            }
        }
    }

    private static void sorting(String inputFile, String outputFile, int numberOfThreads, int numberCount) throws IOException {
        generateNumbersFile(inputFile, numberCount);
        ArrayList<Integer> listFromFile = new ArrayList<>();
        List<ArrayList<Integer>> sortedSublists = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                listFromFile.add(Integer.parseInt(linia));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long startTime = System.currentTimeMillis();

        int chunkSize = (int) Math.ceil((double) listFromFile.size() / numberOfThreads);
        ThreadClass[] threads = new ThreadClass[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, listFromFile.size());
            List<Integer> sublist = listFromFile.subList(start, end);
            threads[i] = new ThreadClass(new ArrayList<>(sublist), i + 1);
            threads[i].start();
        }

        for (ThreadClass thread : threads) {
            try {
                thread.join();
                sortedSublists.add(thread.getSortedList());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Integer> sortedList = mergeSortedLists(sortedSublists);

        long endTime = System.currentTimeMillis();
        System.out.println("Time for " + numberOfThreads + " thread(s): " + (endTime - startTime) + " ms.\n");

        writeListToFile(sortedList, outputFile);
    }

    private static ArrayList<Integer> mergeSortedLists(List<ArrayList<Integer>> sortedSublists) {
        ArrayList<Integer> mergedList = new ArrayList<>();
        int[] indices = new int[sortedSublists.size()];

        boolean done;
        do {
            int minIndex = -1;
            int minValue = Integer.MAX_VALUE;
            done = true;

            for (int i = 0; i < sortedSublists.size(); i++) {
                if (indices[i] < sortedSublists.get(i).size()) {
                    done = false;
                    if (sortedSublists.get(i).get(indices[i]) < minValue) {
                        minValue = sortedSublists.get(i).get(indices[i]);
                        minIndex = i;
                    }
                }
            }

            if (!done) {
                mergedList.add(minValue);
                indices[minIndex]++;
            }
        } while (!done);

        return mergedList;
    }

    private static void writeListToFile(ArrayList<Integer> list, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Integer number : list) {
                writer.write(number + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
