import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class HuntTheWumpus {
    public static Integer[] rooms = {
            0,  1,  2,  3,  4,  5,  6,  7,  8,  9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19
    };

    public static Integer[][] links = {
            {1, 7, 4},    {0, 9, 2},   {1, 11, 3},   {2, 13, 4},   {3, 0, 5},
            {4, 14, 6},   {5, 16, 7},  {6, 0, 8},    {7, 17, 9},   {8, 1, 10},
            {9, 18, 11},  {10, 2, 12}, {11, 19, 13}, {12, 3, 14},  {13, 5, 15},
            {14, 19, 16}, {15, 6, 17}, {16, 8, 18},  {17, 10, 19}, {18, 12, 15}
    };

    public static String WUMPUS = "Wumpus";
    public static String BAT = "Bat";
    public static String PIT = "Pit";
    public static String NOTHING = "Nothing";

    public static ArrayList<String> hazards = new ArrayList<>();
    public static HashMap<String, String> hazardMessages = new HashMap<>();

    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);

    public static boolean gameOver = true;

    //플레이어 현재 위치
    public static int currentRoom;

    //플레이어가 가지고 있는 화살의 개수
    public static int arrowCount;

    //현재 움퍼스의 위치
    public static int wumpusRoom;

    public static void main(String[] args) {
        showIntro();

        initializeStaticValues();

        while (true) {
            //초기화 (화살개수...)
            initializePlayVariables();

            //위험요소 랜덤배치
            setupHazards();

            //인트로
            delay(1000L);
            System.out.println("\n...");
            delay(1000L);
            System.out.println("...");
            delay(1000L);
            System.out.println("동굴에 들어왔습니다...\n");
            delay(1000L);
            System.out.println("\"섬뜩한 곳이군.\"");
            delay(1000L);

            game();

            selectReplay();
        }
    }

    
    /* game */
    private static void game() {
        while (gameOver == false) {
            System.out.println("\n당신은 " + currentRoom + "번 방에 있습니다.");
            System.out.println("행동을 선택하세요.");
            System.out.println("1. 이동");
            System.out.println("2. 화살쏘기");
            System.out.println("3. 통로 목록");
            System.out.println("4. 플레이 종료");

            String action = scanner.nextLine();

            if (action.equals("1")) {
                Integer[] nextRooms = links[currentRoom];

                System.out.println("\n몇번 방으로 이동하시겠습니까?");
                System.out.println(Arrays.toString(nextRooms));

                String nextRoomInput = scanner.nextLine();
                int nextRoom = parseIntegerOrNegative1(nextRoomInput);

                if (Arrays.asList(nextRooms).contains(nextRoom)) {
                    move(nextRoom);
                } else {
                    System.out.println("선택한 방으로는 이동할 수 없습니다.");
                }
            } else if (action.equals("2")) {
                Integer[] nextRooms = links[currentRoom];

                System.out.println("\n몇번 방에 화살을 쏘기겠습니까?");
                System.out.println(Arrays.toString(nextRooms));

                String targetRoomInput = scanner.nextLine();
                int targetRoom = parseIntegerOrNegative1(targetRoomInput);

                if (Arrays.asList(nextRooms).contains(targetRoom)) {
                    shoot(targetRoom);
                } else {
                    System.out.println("선택한 방에는 화살을 쏠 수 없습니다.");
                }
            }  else if (action.equals("3")) {
                System.out.println("현재 방에 연결된 통로는 다음과 같습니다.");
                System.out.println(Arrays.asList(links[currentRoom]));
            } else if (action.equals("0")) {
                System.out.println("게임 플레이를 종료합니다.");
                gameOver = true;
            } else {
                System.out.println("잘못된 입력 입니다.");
            }
        }
    }

    /* showIntro */
    private static void showIntro() {
        try {
            FileInputStream inputStream = new FileInputStream("src/intro.txt");
            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
                delay(500L);
            }
        } catch (FileNotFoundException e) {
            System.out.println("인트로를 읽을 수 없어 생략 합니다.");
        }
    }

    /* initializePlayVariables */
    private static void initializePlayVariables() {
        gameOver = false;

        currentRoom = random.nextInt(rooms.length);
        arrowCount = 5;
    }

    /* selectReplay */
    private static void selectReplay() {
        System.out.println("게임이 끝났습니다. 다시 플레이하시겠습니까?");

        while (true) {
            System.out.println("(0: 종료. 1: 다시 플레이)");
            String action = scanner.nextLine();

            if (action.equals("0")) {
                System.out.println("게임을 종료 합니다.");
                System.exit(0);;
            } else if (action.equals("1")) {
                System.out.println("게임을 다시 플레이 합니다.");
                break;
            } else {
                System.out.println("잘못된 입력 입니다.ㄴ");
            }
        }
    }




    /* move */
    public static void move(int room) {
        currentRoom = room;
        System.out.println(currentRoom + "번 방으로 이동했습니다.");

        String hazard = hazards.get(currentRoom);

        delay(1000L);

        if (hazard.equals(WUMPUS)) {
            System.out.println("\"으아아아아악!\"");
            delay(300L);
            System.out.println("움퍼스가 당신을 잡아먹었습니다.");
            gameOver = true;
        } else if (hazard.equals(PIT)) {
            System.out.println("\"으아아아아아악~\"");
            delay(1000L);
            System.out.println("쿵!");
            delay(300L);
            System.out.println("당신은 구덩이에 빠졌습니다.");
            delay(300L);
            System.out.println("더이상 움퍼스를 사냥할 수 없습니다.");
            gameOver = true;
        } else if (hazard.equals(BAT)) {
            System.out.println("쿵!");
            delay(300L);
            System.out.println("박쥐가 당신을 잡아 다른 방에 떨어트렸습니다.");

            do {
                currentRoom = random.nextInt(rooms.length);
            } while (hazards.get(currentRoom).equals(BAT));

            hazards.set(room, NOTHING);

            while (true) {
                int newBatRoom = random.nextInt(rooms.length);

                if (newBatRoom == currentRoom) {
                    continue;
                }

                if (hazards.get(newBatRoom).equals(NOTHING)) {
                    hazards.set(newBatRoom, BAT);
                    break;
                }
            }

            move(currentRoom);
        } else {
            List<Integer> nextRooms = Arrays.asList(links[currentRoom]);

            System.out.println("\n(연결되어 있는 통로를 살핀다.)");
            for (int nextRoom : nextRooms) {
                delay(700L);
                String hazardAround = hazards.get(nextRoom);
                System.out.println(hazardMessages.get(hazardAround));
            }
            Collections.shuffle(nextRooms);
        }
    }

    /* shoot */
    public static void shoot(int room) {
        arrowCount = arrowCount - 1;

        delay(1000L);
        System.out.println("슈웅");
        delay(300L);

        if (hazards.get(room).equals(WUMPUS)) {
            System.out.println("푸슉!");
            delay(100L);
            System.out.println("\"쿠에에에엑!\"");
            delay(1000L);
            System.out.println("축하합니다. 당신은 움퍼스를 죽였습니다!");
            gameOver = true;
        } else {
            System.out.println("(...)");
            delay(1000L);
            System.out.println("\"화살만 낭비했군.\"");

            if (arrowCount == 0) {
                delay(300L);
                System.out.println("\"이런, 화살이 남지 않았다!\"");
                delay(300L);
                System.out.println("당신은 움퍼스 사냥에 실패했습니다.");
                gameOver = true;
            } else if (random.nextInt(4) != 0) {
                System.out.println("당신은 움퍼스를 깨웠습니다.");
                delay(1000L);

                Integer[] nextRooms = links[wumpusRoom];

                int nextRoom = nextRooms[random.nextInt(3)];

                if (hazards.get(nextRoom).equals(NOTHING)) {
                    hazards.set(wumpusRoom, NOTHING);
                    hazards.set(nextRoom, WUMPUS);
                    wumpusRoom = nextRoom;
                }

                if (wumpusRoom == currentRoom) {
                    System.out.println("\"으아아아아아악!\"");
                    delay(500L);
                    System.out.println("움퍼스가 당신을 잡아먹었습니다.");
                    gameOver = true;
                } else if (wumpusRoom == nextRoom) {
                    System.out.println("움퍼스가 도망갔습니다.");
                }
            }
        }
    }




    /* initializeStaticValues */
    public static void initializeStaticValues() {
        hazardMessages.put(WUMPUS, "\"어디선가 끔찍한 냄새가 난다.\"");
        hazardMessages.put(BAT, "\"어디선가 부스럭거리는 소리가 들린다.\"");
        hazardMessages.put(PIT, "\"바람이 부는 소리가 들리는 것 같다\"");
        hazardMessages.put(NOTHING, "\"저 방에는 아무것도 없는 것 같다.\"");
    }

    /* setupHazards */
    public static void setupHazards() {
        if (hazards.size() == 0) {
            for (int i = 0; i< rooms.length; i = i + 1) {
                hazards.add(NOTHING);
            }
        }

        for (int i = 0; i < rooms.length; i = i + 1) {
            hazards.set(i, NOTHING);
        }

        String[] ordinals = {WUMPUS, BAT, BAT, BAT, PIT, PIT};

        for (String hazard : ordinals) {
            int room;

            while (true) {
                room = random.nextInt(rooms.length);

                if (isTooCloseWithPlayer(room)) {
                    continue;
                }

                if (hazards.get(room).equals(NOTHING)) {
                    break;
                }
            }

            hazards.set(room, hazard);

            if (hazard.equals(WUMPUS)) {
                wumpusRoom = room;
            }
        }
    }

    /* isTooCloseWithPlayer */
    public static boolean isTooCloseWithPlayer(int room) {
        if (currentRoom == room) {
            return true;
        }

        List<Integer> linkedRooms = Arrays.asList(links[currentRoom]);
        if (linkedRooms.contains(room)) {
            return true;
        }

        return false;
    }

    /* parseIntegerOrNegative1 */
    public static int parseIntegerOrNegative1(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* delay */
    public static void delay(Long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {

        }
    }


}