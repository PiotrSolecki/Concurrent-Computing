import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class main {
    public static void main(String[] args) throws IOException {
        generateNumbersFile("numbers0.1k.txt", 100);
        generateNumbersFile("numbers1k.txt", 1000);
        generateNumbersFile("numbers10k.txt", 10000);

        System.out.println("Sorting 100 numbers");
        sorting("numbers0.1k.txt", 1);
        sorting("numbers0.1k.txt", 2);
        sorting("numbers0.1k.txt", 3);
        sorting("numbers0.1k.txt", 4);

        System.out.println("Sorting 1000 numbers");
        sorting("numbers1k.txt", 1);
        sorting("numbers1k.txt", 2);
        sorting("numbers1k.txt", 3);
        sorting("numbers1k.txt", 4);

        System.out.println("Sorting 10000 numbers");
        sorting("numbers10k.txt", 1);
        sorting("numbers10k.txt", 2);
        sorting("numbers10k.txt", 3);
        sorting("numbers10k.txt", 4);
    }

    private static void generateNumbersFile(String filename, int number_count) throws IOException {
        Random random = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < number_count; i++) {
                writer.write((random.nextInt(2 * 1000000) - 1000000) + "\n");
            }
        }
    }

    private static void sorting(String filename, int numberOfThreads){
        ArrayList<Integer> listFromFile = new ArrayList<>();
        ArrayList<Integer> sortedList = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                listFromFile.add(Integer.parseInt(linia));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long startTimeNs = System.nanoTime();

        ThreadClass thread = new ThreadClass(listFromFile);
        thread.start();

        try {
            thread.join();
            sortedList = thread.getSortedList();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Posortowana lista: " + sortedList);



        //Merge list z threadów

        //Wypisanie czasu
        long endTimeNs = System.nanoTime();
        System.out.println("Time for " + numberOfThreads + " thread(s): " + ((endTimeNs - startTimeNs)/ 1_000.0) + " μs - Microseconds");

        //Przekazanie posortowanej listy do nowego pliku
    }


}
