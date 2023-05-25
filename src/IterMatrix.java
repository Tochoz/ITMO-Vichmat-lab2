import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class IterMatrix {
    private final int ITERATIONS = 10; // ���������� ��������
    private final int NEEDDESC = 4; // ����������� ���������� ��������� ��������� ���������
    private final double ACCURACY = 0.0001; // �������� �����������
    private final double EPS = 0.001; // �������, ������ �������� ����� ��������� ����

    private double[][] a; // ��������� ������ ������������� �
    private int num; // ���������� ��������� � �������
    private double[] ans; // ������ ������� �������, ���� ������� �� ���� ������, �������� null
    private double sums[]; // ������ ���� ������������� �����
    private int[] noZeroesOrder;

    // ����������� �� ���������, ������������� ���������� ������ ������
    public IterMatrix(){
        num = 0;
        a = null;
        ans = null;
        noZeroesOrder = null;
    }

    // ����� ���������� ������ ��� �������� ���������� �����
    private void create(int num){
        this.num = num;
        // ��������� ������ ��� ���������� �������
        a = new double[num][num + 1];
        // ���������� ��� ������� �������
        ans = new double[num];

        sums = new double[num];
    }

    // ������������� ������� ������� �� �����, �� ���� ��� ����� ����������� ���������� ���� ����� ���� �� ������
    public void init(String s) throws FileNotFoundException{
        //  �������� �������� File, Scanner � Pattern
        File file = new File(s);
        Scanner scan = new Scanner(file);
        //   ������������� ����������� ��������� � Pattern
        Pattern pat = Pattern.compile("[\\s\\t]+");
        //   ������ ������ ������ ����������� ��������� � �������
        String str = scan.nextLine();
        //   ���������� ����� � ���������� � ���������� n
        String [] sn = pat.split(str.trim());
        num = Integer.parseInt(sn[0]);
        //   ��������� ������ ��� n ��������� ������� create
        create(num);

        //   ���������� ������������ n ����� � n+1 ������� � ������ � a[][]
        int i, j;
        for (i=0; i<num; i++){
            str = scan.nextLine();
            sn = pat.split(str.trim());
            for (j=0; j <= num; j++) {
                a[i][j] = Double.parseDouble(sn[j]);
            }
        }
        //   �������� �������
        scan.close();
    }

    // ����� ������� ������ ����
    private void computeSums(){
        for (int i=0; i<num; i++)
            for (int j=0; j<num; j++)
                sums[i] += Math.abs(a[i][j]);   // ������� ������ ����
    }

    //  ����� ��������� ������� ����� �� ������� ��������� true, ���� ����
    private boolean checkZeroes(int[] order){
        for (int i=0; i<num; i++)
            if (Math.abs(a[order[i]][i]) < EPS)
                return true;
        return false;
    }

    // ����� �������� ����� ������� ������� �� ������
    private static void arrayCopy(int[] a, int[] b){
        for (int i=0; i<a.length; i++)
            b[i] = a[i];
    }

    // ���������� ����������� �������, �� ���� ����� ������� � �������� ������ ������������
    // ���������� true ���� ��� �������
    private boolean findOrder(int start, int[] order){
        if (checkDUS(order)) // ����� ������� ����� ���
            return true;
        if (noZeroesOrder == null && checkZeroes(order)){ // ������� ���� ���� ������������ � ������� ��� �����, ���� �� �������
            noZeroesOrder = new int[num];
            arrayCopy(order, noZeroesOrder);
        }
        if (start >= num) // ����� �� ����� ��������
            return false;

        if (findOrder(start + 1, order))        // ��������� ���������� ������, ���� ��� ������������
            return true;

        for (int i = start + 1; i < num; i++) {
            swapElements(i, start, order);           // ������ �������� ����� start � ���
            if (findOrder(start + 1, order))   // ��������� ������� �� ������������
                return true;
            swapElements(start, i, order);          // ������ �������� �����
        }
        return false;
    }

    // ����� ������ �������� � ������� �������
    private static void swapElements(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // ����� ��������� ����������� ������� ���������� �� ������� ������������������
    private boolean checkDUS(int[] order) {
        int check = 0; // ���������� �����, ��� ����� �� ��������� ����� ����� ���������
        for(int i = 0; i < num; i++) {
            if(sums[i] > 2 * Math.abs(a[order[i]][i]))
                return false;
            else {
                if(Math.abs(sums[i] - 2 * Math.abs(a[order[i]][i])) < EPS) { // ���� �������� ������ EPS �� ��� �����
                    if(check != num - 1) { // ���� ��� ����� ����� ������
                        check++;
                    }
                    else
                        return false;
                }
            }
        }
        return true;
        // �������� �� ������������ ��������� � ���������, �������� �� ��� �� ������ ����� ���������
    }

    // ����� �������������� ������� � �������, ���������� 0 ���� ���� ����, 1 ���� ��� �� �����������, 2 ���� ��� �����������
    public int prepare(){
        // ��������� �����
        computeSums();
        // �������� ���������� ������� �������� order
        int[] order = new int[num];
        for (int i=0; i<num; i++)
            order[i] = i;

        // �������� ���� �� ���� �� ���������
        if (checkZeroes(order)){
            // ������� ����� ������������ � ���
            if (findOrder(0, order)){
                 replace(order); // ������������ ������
                 return 2;
            } else if (noZeroesOrder != null) { // ���� ����� ������������ ��� �����
                replace(noZeroesOrder);
                return 1;
            } else
                 return 0;
        }
        if (checkDUS(order)) // ���� ����� � �� ����
             return 2;
         else
             return 1;
    }

    // ����� ������������ ������ ������� � �������� �������
    public void replace(int[] order) {
        double[][] temp = new double[num][]; // ��������� �������
        for (int i = 0; i < num; i++) {
            temp[i] = a[order[i]];
        }
        a = temp;
    }

    // ����� �������� ��������� �������������� �������
    public double[] solve(int cond){

        switch (cond){
            case 0:
                System.out.println("������� ����� ���� �� ������� ���������");
                ans = null;
                break;
            case 1:
                // �������� � ����� ��������� �����������
                solve_control();
                break;
            case 2:
                // �������� � ����� ��������� �����������
                solve_no_control();
                break;
        }
        return ans;
    }

    private void solve_control(){
        int i, count=0; // ���-�� ��������� ���������
        double cur=0, prev; // prev - ���������� ������, cur - �������
        prev = iterate();

        for(i = 1; i < ITERATIONS; i++) {  // �������� � ���������
            cur = iterate();
            if(prev > cur) { // �������
                count++;
                if (count == NEEDDESC) // ���������� ������ ����������
                    break;
            } else {
                count = 0;
            }
            prev = cur;
        }
        if (count != NEEDDESC){ // ���� �� �������
            System.out.println("����� ����������");
            ans = null;
            return;
        }
        if (cur >= ACCURACY) // ���� �������� ����������� ������������
            solve_no_control();
    }

    private void solve_no_control(){
        while (iterate() >= ACCURACY){}
    }

    // ����� ���������� ���� �������� ��� �������� ������� � ���������� ������������ ��������
    private double iterate(){
        double delta = 0, temp;                // ������ � ���������� � ������� ����� ������� �����������

        for(int j = 0; j < num; j++) {        // ��� ���� �����������

            temp = ans[j];
            ans[j] = a[j][num];  // ������������ ���������� �������� ���������� �����

            for(int k = 0; k < j; k++) {    // �������� ������������ � ������������ �� ������
                ans[j] -= a[j][k] * ans[k];
            }
            for(int k = j + 1; k < num; k++) { // �����
                ans[j] -= a[j][k] * ans[k];
            }

            ans[j] /= a[j][j];         // ����� �� ������������
            temp = Math.abs(temp - ans[j]);  // ������� �������� � ���������� ���������
            if(temp > delta)    // ��������� ��������
                delta = temp;
        }
        return delta;
    }

    // ����� ������ ���������� ������� ������������� ������� � ��������������� ����
    public void print(){
    // ��� ������� �������� ������ ������ ���������� ������� a[][] ���������
    // ��������������� ����� �������� � ��������������� ����
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

