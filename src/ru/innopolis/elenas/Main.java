package ru.innopolis.elenas;

import java.util.List;
import java.util.function.Predicate;

public class Main {

    volatile static Integer sum = 0;
    volatile static boolean isCancelled = false;

    public static void main(String[] args) {

        for(String res : args){

            Thread thread = new Thread(() -> {

                Resource resource = new Resource(res);
                List<Integer> numbers = resource.getNumbers();

                Predicate<Integer> ifPositive = (Integer i) -> i > 0;
                Predicate<Integer> ifEven = (Integer i) -> (i%2) == 0;

                synchronized (sum) {
                    if(!isCancelled) {
                        sum += numbers.stream().filter(ifPositive).filter(ifEven).reduce(0, Integer::sum);
                        System.out.println("Calculating :" + sum);
                    }else {
                        System.out.println("Calculating cancelled");
                    }
                }
            });
            thread.start();
        }
    }
}
