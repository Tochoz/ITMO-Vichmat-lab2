import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            IterMatrix system = new IterMatrix(); // Создание объекта системы

            system.init("data.txt");
            system.print();                     // Вывод системы в консоль
            int cond = system.prepare();        // Подготовка системы перед решением, получаем её состояние
            System.out.println();
            system.print();                     // Вывод подготовленной системы
            System.out.println();

            double[] ans = system.solve(cond);  // Решение системы
            if (ans != null)                    // Если решения есть, вывод массива ответов системы
                system.printAns();
            else
                System.out.println("Не удалось найти решение");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }

    }
}