import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class IterMatrix {
    private final int ITERATIONS = 10; // Количество итераций
    private final int NEEDDESC = 4; // Необходимое количество монотонно убывающих разностей
    private final double ACCURACY = 0.0001; // Точность приближений
    private final double EPS = 0.001; // Епсилон, меньше которого числа считается нулём

    private double[][] a; // Двумерный массив коэффициентов и
    private int num; // Количество уравнений в системе
    private double[] ans; // Массив решений системы, если система не была решена, содержит null
    private double sums[]; // Массив сумм коэффициентов строк
    private int[] noZeroesOrder;

    // Конструктор по умолчанию, инициализация переменных класса нулями
    public IterMatrix(){
        num = 0;
        a = null;
        ans = null;
        noZeroesOrder = null;
    }

    // Метод выделяющий память под заданное количество строк
    private void create(int num){
        this.num = num;
        // Выделение памяти для двумерного массива
        a = new double[num][num + 1];
        // Аналогично для массива решений
        ans = new double[num];

        sums = new double[num];
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
            str = scan.nextLine();
            sn = pat.split(str.trim());
            for (j=0; j <= num; j++) {
                a[i][j] = Double.parseDouble(sn[j]);
            }
        }
        //   Закрытие сканера
        scan.close();
    }

    // Метод считает массив сумм
    private void computeSums(){
        for (int i=0; i<num; i++)
            for (int j=0; j<num; j++)
                sums[i] += Math.abs(a[i][j]);   // Считаем массив сумм
    }

    //  Метод проверяет наличие нулей на главной диагонали true, если есть
    private boolean checkZeroes(int[] order){
        for (int i=0; i<num; i++)
            if (Math.abs(a[order[i]][i]) < EPS)
                return true;
        return false;
    }

    // Метод копирует числа первого массива во второй
    private static void arrayCopy(int[] a, int[] b){
        for (int i=0; i<a.length; i++)
            b[i] = a[i];
    }

    // Нахождение правильного порядка, на вход даётся элемент с которого начать переставлять
    // Возвращает true если ДУС нашлось
    private boolean findOrder(int start, int[] order){
        if (checkDUS(order)) // Нашли порядок удовл ДУС
            return true;
        if (noZeroesOrder == null && checkZeroes(order)){ // Находим хоть одну перестановку у которой нет нулей, если не найдена
            noZeroesOrder = new int[num];
            arrayCopy(order, noZeroesOrder);
        }
        if (start >= num) // Дошли до конца перебора
            return false;

        if (findOrder(start + 1, order))        // Проверяем комбинацию дальше, если нет переставляем
            return true;

        for (int i = start + 1; i < num; i++) {
            swapElements(i, start, order);           // Меняем элементы после start с ним
            if (findOrder(start + 1, order))   // Проверяем удачная ли перестановка
                return true;
            swapElements(start, i, order);          // Меняем элементы назад
        }
        return false;
    }

    // Метод меняет элементы в массиве местами
    private static void swapElements(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Метод проверяет достаточное условие сходимости на текущей последовательности
    private boolean checkDUS(int[] order) {
        int check = 0; // Количество строк, где число на диагонали равно сумме остальных
        for(int i = 0; i < num; i++) {
            if(sums[i] > 2 * Math.abs(a[order[i]][i]))
                return false;
            else {
                if(Math.abs(sums[i] - 2 * Math.abs(a[order[i]][i])) < EPS) { // Если разность меньше EPS то они равны
                    if(check != num - 1) { // Если все числа равны суммам
                        check++;
                    }
                    else
                        return false;
                }
            }
        }
        return true;
        // Проходит по диагональным элементам и проверяет, являются ли они не меньше суммы остальных
    }

    // Метод подготавливает систему к решению, возвращает 0 если есть нули, 1 если дус не выполняется, 2 если дус выполняется
    public int prepare(){
        // Посчитать суммы
        computeSums();
        // Создание начального порядка индексов order
        int[] order = new int[num];
        for (int i=0; i<num; i++)
            order[i] = i;

        // Проверка есть ли нули на диагонали
        if (checkZeroes(order)){
            // Попытка найти перестановку с ДУС
            if (findOrder(0, order)){
                 replace(order); // Переставляем строки
                 return 2;
            } else if (noZeroesOrder != null) { // если нашли перестановку без нулей
                replace(noZeroesOrder);
                return 1;
            } else
                 return 0;
        }
        if (checkDUS(order)) // Если нулей и не было
             return 2;
         else
             return 1;
    }

    // Метод переставляет строки матрицы в заданном порядке
    public void replace(int[] order) {
        double[][] temp = new double[num][]; // Временная матрица
        for (int i = 0; i < num; i++) {
            temp[i] = a[order[i]];
        }
        a = temp;
    }

    // Метод получает состояние подготовленной системы
    public double[] solve(int cond){

        switch (cond){
            case 0:
                System.out.println("Система имеет нули на главной диагонали");
                ans = null;
                break;
            case 1:
                // записать в цикле начальное приближение
                solve_control();
                break;
            case 2:
                // записать в цикле начальное приближение
                solve_no_control();
                break;
        }
        return ans;
    }

    private void solve_control(){
        int i, count=0; // Кол-во монотонно убывающих
        double cur=0, prev; // prev - предыдущая дельта, cur - текущая
        prev = iterate();

        for(i = 1; i < ITERATIONS; i++) {  // Итерации с контролем
            cur = iterate();
            if(prev > cur) { // Убывает
                count++;
                if (count == NEEDDESC) // Достигнуто нужное количество
                    break;
            } else {
                count = 0;
            }
            prev = cur;
        }
        if (count != NEEDDESC){ // Если не сошлось
            System.out.println("Метод расходится");
            ans = null;
            return;
        }
        if (cur >= ACCURACY) // Если точность приближений недостаточна
            solve_no_control();
    }

    private void solve_no_control(){
        while (iterate() >= ACCURACY){}
    }

    // Метод производит одну итерацию над массивом решений и возвращает максимальную разность
    private double iterate(){
        double delta = 0, temp;                // Дельта и переменная в которой будем считать приближения

        for(int j = 0; j < num; j++) {        // Для всех неизвестных

            temp = ans[j];
            ans[j] = a[j][num];  // Приравниваем переменной значение свободного члена

            for(int k = 0; k < j; k++) {    // Вычитаем произведения с неизвестными до данной
                ans[j] -= a[j][k] * ans[k];
            }
            for(int k = j + 1; k < num; k++) { // После
                ans[j] -= a[j][k] * ans[k];
            }

            ans[j] /= a[j][j];         // Делим на диагональный
            temp = Math.abs(temp - ans[j]);  // Находим разность с предыдущим значением
            if(temp > delta)    // Обновляем максимум
                delta = temp;
        }
        return delta;
    }

    // Метод вывода двумерного массива коэффициентов системы в экспонениальном виде
    public void print(){
    // Для каждого элемента каждой строки двумерного массива a[][] выполнять
    // Форматированный вывод значения в экпоненциальном виде
        int i, j;
        for (i=0; i<num; i++){
            for (j=0; j<num+1; j++)
                System.out.printf("%15.6E",a[i][j]);
            System.out.println();
        }
    }
    public void printAns(){
        for (int i=0; i<num; i++)
            System.out.printf("%15.6E",ans[i]);
        System.out.println();
    }
}

