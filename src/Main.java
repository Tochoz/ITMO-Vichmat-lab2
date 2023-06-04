import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            IterMatrix system = new IterMatrix(); // �������� ������� �������

            system.init("data.txt");
            system.print();                     // ����� ������� � �������
            int cond = system.prepare();        // ���������� ������� ����� ��������, �������� � ���������
            System.out.println();
            system.print();                     // ����� �������������� �������
            System.out.println();

            double[] ans = system.solve(cond);  // ������� �������
            if (ans != null)                    // ���� ������� ����, ����� ������� ������� �������
                system.printAns();
            else
                System.out.println("�� ������� ����� �������");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }

    }
}