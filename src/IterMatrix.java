import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class IterMatrix {
    private final int ITERATIONS = 10; // Количество итераций
    private final int NEEDDESC = 5; // Необходимое количество монотонно убывающих разностей

    private double[][] a; // Двумерный массив коэффициентов и
    private int num; // Количество уравнений в системе
    private int order[];
    private double[] ans; // Массив решений системы, если система не была решена, содержит null
    private double sums[]; // Массив сумм коэффициентов строк

    // Конструктор по умолчанию, инициализация переменных класса нулями
    public IterMatrix(){
        num = 0;
        order = null;
        a = null;
        ans = null;
    }

    // Метод выделяющий память под заданное количество строк
    private void create(int num){
        this.num = num;
        // Выделение памяти для двумерного массива
        a = new double[num][num + 1];
        // Выделение памяти для массива порядка строк
        order = new int[num];
        // Аналогично для массива решений
        ans = new double[num];
    }

    // Инициализация системы чтением из файла, на вход имя файла выбрасывает исключение если такой файл не найден
    public void init(String s) throws FileNotFoundException{
        //  Создание объектов File, Scanner и Pattern
        File file = new File(s);
        Scanner scan = new Scanner(file);
        //   Инициализация регулярного выражения в Pattern
        Pattern pat = Pattern.compile("[\\s\\t]+");
        //   Чтение первой строки количеством уравнений в системе
        String str = scan.nextLine();
        //   Извлечение числа и присвоение в переменную n
        String [] sn = pat.split(str.trim());
        num = Integer.parseInt(sn[0]);
        //   Выделение памяти под n уравнений вызовом create
        create(num);

        //   Построчное сканирование n строк с n+1 числами и запись в a[][]
        int i, j;
        for (i=0; i<num; i++){
            order[i] = i;
            str = scan.nextLine();
            sn = pat.split(str.trim());
            for (j=0; j <= num; j++)
                a[i][j] = Double.parseDouble(sn[j]);
        }
        //   Закрытие сканера
        scan.close();
    }

    //  Метод проверяет наличие нулей на главной диагонали
    private boolean checkZeroes(){
        // Проход по диагонали, если встретили ноль isZero(), меняем его на ноль и return false
        // return true
        return false;
    }

    // Метод вычисляет сумму коэффициентов в строке
    private void computeSums(){
        // TODO надо ли отдельно это или можно в init сразу
    }

    // Нахождение правильного порядка, на вход даётся элемент с которого начать переставлять и нужно ли пытаться ДУС выполнять
    private boolean findOrder(int start, boolean DUS_needed){
        // TODO нужно их одновременно найти или если не получилось ДУС можно с нуля найти без нулей
        return true;
    }

    private boolean checkDUS() {// TODO нужно ли при перестановках вызывать
        return false;
    }

    public double[] solve(){
        // Проверка есть ли нули на диагонали
        // если да
            // Попытка найти перестановку с ДУС
            // DUS = findOrder(0, true);
            // if (!DUS)
            //      findOrder(0, false);
        // else
            // DUS = checkDUS()

        // TODO что делать если ДУС не выполняется в шаге 5

        // Произвести итерации, если расходится вернуть null
        if (!doIterations()) // TODO можно ли так
            return null;
        return ans;
    }

    // Метод считает разность между наибольшим и наименьшим элементом на текущем массиве приближений
    // На вход получает массив новых приблежений
    private double computeDelta(double[] newIter){
        // создать переменную максимума
        double max = newIter[0] - ans[0];
        // Высчитывать разности между оставшимися соотв элементами и обновлять максимум
        return max;
    }

    // Метод производит итерации, считает разности и смотрит расходится или нет
    // Если метод расходится вернёт false
    private boolean doIterations(){
        // создать переменную последней разности
        // получает новые приближения newAns = iterate() ..
        // посчитать и записать разность computeDelta(newAns)
        return false;
    }

    // Метод производит одну итерацию над массивом решений и возвращает массив новых приближений
    private double[] iterate(){
        // Создать новый массив
        // в цикле пересчитать
        return null;
    }

    // Проверка является ли данное число нулём с точностью 4 знака
    private static boolean isZero(double a){
        return Math.abs(a) < 0.0001;
    }

    // Метод вывода двумерного массива коэффициентов системы в экспонениальном виде
    public void print(){
    // Вывести строки в порядке order
    // Для каждого элемента каждой строки двумерного массива a[][] выполнять
    // Форматированный вывод значения в экпоненциальном виде
        int i, j;
        for (i=0; i<num; i++){
            for (j=0; j<num+1; j++)
                System.out.printf("%15.6E",a[order[i]][j]);
            System.out.println();
        }
    }
    public void printAns(){
        for (int i=0; i<num; i++)
            System.out.printf("%15.6E",ans[i]);
        System.out.println();
    }
}

