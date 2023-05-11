import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class IterMatrix {
    private final int ITERATIONS = 10; // ���������� ��������
    private final int NEEDDESC = 5; // ����������� ���������� ��������� ��������� ���������

    private double[][] a; // ��������� ������ ������������� �
    private int num; // ���������� ��������� � �������
    private int order[];
    private double[] ans; // ������ ������� �������, ���� ������� �� ���� ������, �������� null
    private double sums[]; // ������ ���� ������������� �����

    // ����������� �� ���������, ������������� ���������� ������ ������
    public IterMatrix(){
        num = 0;
        order = null;
        a = null;
        ans = null;
    }

    // ����� ���������� ������ ��� �������� ���������� �����
    private void create(int num){
        this.num = num;
        // ��������� ������ ��� ���������� �������
        a = new double[num][num + 1];
        // ��������� ������ ��� ������� ������� �����
        order = new int[num];
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
            order[i] = i;
            str = scan.nextLine();
            sn = pat.split(str.trim());
            for (j=0; j <= num; j++)
                a[i][j] = Double.parseDouble(sn[j]);
        }
        //   �������� �������
        scan.close();
    }

    //  ����� ��������� ������� ����� �� ������� ���������
    private boolean checkZeroes(){
        // ������ �� ���������, ���� ��������� ���� isZero(), ������ ��� �� ���� � return false
        // return true
        return false;
    }

    // ����� ��������� ����� ������������� � ������
    private void computeSums(){
        // TODO ���� �� �������� ��� ��� ����� � init �����
    }

    // ���������� ����������� �������, �� ���� ����� ������� � �������� ������ ������������ � ����� �� �������� ��� ���������
    private boolean findOrder(int start, boolean DUS_needed){
        // TODO ����� �� ������������ ����� ��� ���� �� ���������� ��� ����� � ���� ����� ��� �����
        return true;
    }

    private boolean checkDUS() {// TODO ����� �� ��� ������������� ��������
        return false;
    }

    public double[] solve(){
        // �������� ���� �� ���� �� ���������
        // ���� ��
            // ������� ����� ������������ � ���
            // DUS = findOrder(0, true);
            // if (!DUS)
            //      findOrder(0, false);
        // else
            // DUS = checkDUS()

        // TODO ��� ������ ���� ��� �� ����������� � ���� 5

        // ���������� ��������, ���� ���������� ������� null
        if (!doIterations()) // TODO ����� �� ���
            return null;
        return ans;
    }

    // ����� ������� �������� ����� ���������� � ���������� ��������� �� ������� ������� �����������
    // �� ���� �������� ������ ����� �����������
    private double computeDelta(double[] newIter){
        // ������� ���������� ���������
        double max = newIter[0] - ans[0];
        // ����������� �������� ����� ����������� ����� ���������� � ��������� ��������
        return max;
    }

    // ����� ���������� ��������, ������� �������� � ������� ���������� ��� ���
    // ���� ����� ���������� ����� false
    private boolean doIterations(){
        // ������� ���������� ��������� ��������
        // �������� ����� ����������� newAns = iterate() ..
        // ��������� � �������� �������� computeDelta(newAns)
        return false;
    }

    // ����� ���������� ���� �������� ��� �������� ������� � ���������� ������ ����� �����������
    private double[] iterate(){
        // ������� ����� ������
        // � ����� �����������
        return null;
    }

    // �������� �������� �� ������ ����� ���� � ��������� 4 �����
    private static boolean isZero(double a){
        return Math.abs(a) < 0.0001;
    }

    // ����� ������ ���������� ������� ������������� ������� � ��������������� ����
    public void print(){
    // ������� ������ � ������� order
    // ��� ������� �������� ������ ������ ���������� ������� a[][] ���������
    // ��������������� ����� �������� � ��������������� ����
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

