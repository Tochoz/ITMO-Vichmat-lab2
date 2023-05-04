import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            IterMatrix system = new IterMatrix(); // создание объекта системы

            system.init("data.txt");
            system.print();                     // вывод системы в консоль
            System.out.println();

            // TODO что значит система, решаемая методом

            double[] ans = system.solve();// решение системы
            if (ans != null) // вывод массива ответов системы
                system.printAns();
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }

    }
}