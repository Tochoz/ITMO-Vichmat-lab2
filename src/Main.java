import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            IterMatrix system = new IterMatrix(); // �������� ������� �������

            system.init("data.txt");
            system.print();                     // ����� ������� � �������
            System.out.println();

            // TODO ��� ������ �������, �������� �������

            double[] ans = system.solve();// ������� �������
            if (ans != null) // ����� ������� ������� �������
                system.printAns();
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }

    }
}