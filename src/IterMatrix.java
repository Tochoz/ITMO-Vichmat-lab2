import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class IterMatrix {
    private final int ITERATIONS = 10; // ���������� ��������
    private final int NEEDDESC = 4; // ����������� ���������� ��������� ��������� ���������
    private final double ACCURACY = 0.0001; // �������� �����������

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

                sums[i] += Math.abs(a[i][j]);   // ������� ������ ����
            }
        }
        //   �������� �������
        scan.close();
    }

    //  ����� ��������� ������� ����� �� ������� ���������
    private boolean checkZeroes(int[] order){
        // ������ �� ���������, ���� ��������� ���� isZero(), ������ ��� �� ���� � return false
        // return true
        return false;
    }

    // ���������� ����������� �������, �� ���� ����� ������� � �������� ������ ������������
    // ���������� true ���� ��� �������
    private boolean findOrder(int start, int[] order){
        if (checkDUS(order)) // ����� ������� ����� ���
            return true;
        if (noZeroesOrder == null && checkZeroes(order)){ // ������� ���� ���� ������������ � ������� ��� �����, ���� �� �������
            System.arraycopy(order, 0, noZeroesOrder, 0, num + 1);
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

    private boolean checkDUS(int[] order) {
        // �������� �� ������������ ��������� � ��������� �������� �� ��� �� ������ ����� ���������
    }

    // ����� �������������� ������� � �������, ���������� 0 ���� ���� ����, 1 ���� ��� �� �����������, 2 ���� ��� �����������
    public int prepare(){
        // ��������� �����

        // �������� ���������� ������� �������� order
        int[] order = new int[num];
        for (int i=0; i<num; i++){

        }


        // �������� ���� �� ���� �� ���������
        // ���� ��
            // ������� ����� ������������ � ���
            // if (findOrder(order))
                // replace(order);
                // return 2;
            // else if (noZeroesOrder != null) ���� ����� ������������ ��� �����
                // replace(order);
                // return 1;
            // else
                // return 0;
        // if (checkDUS(order))
            // return 2;
        // else
            // return 1;
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
        // �������� � ����� ��������� �����������
        for (int i=0; i < num; i++)
            ans[i] = 0;

        switch (cond){
            case 0:
                System.out.println("������� ����� ���� �� ������� ���������");
                ans = null;
                break;
            case 1:
                solve_control();
                break;
            case 2:
                solve_no_control();
                break;
        }
        return ans;
    }

    private void solve_control(){
        int i;
        double cur, prev; // prev - ���������� ������, cur - �������
        for(i = 0; i < ITERATIONS - NEEDDESC - 1; i++) { // �������� ������ �������� �� ��������
            prev = iterate();
        }
        for(i = 0; i < NEEDDESC; i++) {  // �������� � ���������
            cur = iterate();
            if(prev < cur) {             // ���� ������������ ��������
                System.out.println("����� ����������");
                ans = null;
                return;
            }
            prev = cur;
        } // ���� �� ������� ������� �� �����
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

