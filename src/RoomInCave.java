import java.util.Arrays;
import java.util.Scanner;

public class RoomInCave {
    public static void main(String[] args) {
        int[] rooms = {0, 1, 2, 3};

        int[][] links = {{1, 2, 3}, {2, 3, 0}, {3, 0, 1}, {0, 1, 2}};

        int currentRoom = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("지금 " + currentRoom + "번 방에 있습니다.");
            System.out.println("다음 번호 중에서 이동할 방 번호를 입력 해주세요.");

            System.out.println(Arrays.toString(links[currentRoom]));;

            int roomNumber = scanner.nextInt();

            currentRoom = roomNumber;
        }

    }
}
