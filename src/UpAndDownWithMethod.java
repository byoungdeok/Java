import java.util.Random;
import java.util.Scanner;

public class UpAndDownWithMethod {
    public static int randomNumber;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Random random = new Random();
        randomNumber = random.nextInt(256);

        int attempt = 0;

        while (attempt < 10) {
            boolean isCorrect = play();

            if (isCorrect) {
                break;
            }

            attempt = attempt + 1;
            System.out.println(attempt + "회 시도 하였습니다.");
        }

        System.out.println("게임을 종료 합니다.");
    }

    public static boolean play() {
        System.out.println("숫자를 입력 하세요.");
        int input = scanner.nextInt();
        
        if (input == randomNumber) {
            System.out.println("짝짝짝. 정답 입니다.");
            return true;
        } else if (input > randomNumber) {
            System.out.println("더 작게요.");
        }  else {
            System.out.println("더 크게요");
        }

        return false;
    }

}
