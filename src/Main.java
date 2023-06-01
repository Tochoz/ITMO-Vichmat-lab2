import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            IterMatrix system = new IterMatrix(); // создание объекта системы

            system.init("data.txt");
            system.print();                     // вывод системы в консоль
            int cond = system.prepare();
            System.out.println();
            system.print();
            System.out.println();

            double[] ans = system.solve(cond);// решение системы
            if (ans != null) // вывод массива ответов системы
                system.printAns();
            else
                System.out.println("Ќе удалось найти решение");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }

    }
}