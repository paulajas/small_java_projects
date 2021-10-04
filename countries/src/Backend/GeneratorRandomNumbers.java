package Backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneratorRandomNumbers implements Runnable {

    private int number;
    List<String> countries;

    private void getCountries(String fileName) {
//        List<String> result;
//        throws IOException
//        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
//            result = lines.collect(Collectors.toList());
//        }
        List<String> list = new ArrayList<>();
        list.add("Bulgaria");
        list.add("Poland");
        list.add("Rusia");

        countries = list;
    }

    protected static String country (List<String> list){
        Random rand = new Random();
        String number = list.get(rand.nextInt(list.size()));
        return number;
    }
    public static void main(String[] args){

        GeneratorRandomNumbers obj = new GeneratorRandomNumbers();

        obj.getCountries("a");

        Runnable r = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println(country(obj.countries));
                }
            }catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        };

        new Thread(r).start();

//        System.out.println(country(obj.countries));

    }

    @Override
    public void run() {
        System.out.println(country(countries));
    }

    Runnable r = () -> {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(country(countries));
            }
        }catch(Exception e){}
    };

}
