public class StringEx {
    public static void main(String[] args) {
//        String chicken = "치킨은 살 안 쪄요.";
//        String me = "살은 내가 쪄요.";
//
//        String chickenAndMe = chicken + me;
//        System.out.println(chicken);
//        System.out.println(me);
//        System.out.println(chickenAndMe);

//        String me = "살은 내가 쪄요.";
//        int length = me.length();
//        System.out.println(length);
//        System.out.println(me.replaceAll("내가", "치킨이"));
//
        String birthday = "1994/04/19";
        String year = birthday.substring(0, 4);
        String  day = birthday.substring(8);
        System.out.println(year);
        System.out.println(day);
    }
}
