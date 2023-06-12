package highScoreKit.hash;

import java.util.Arrays;

public class HashExecute {
    public void execute() {
        // hash
        Pokemon pokemon = new Pokemon();
        PhoneNumberList phoneNumberList = new PhoneNumberList();
        UnreachedAthlete unreachedAthlete = new UnreachedAthlete();
        BestAlbum bestAlbum = new BestAlbum();

        System.out.println(
                "phoneNumberList > [119, 97674223, 1195524421] -> false: " +
                        phoneNumberList.solution(new String[]{"119", "97674223", "1195524421"})
        );
        System.out.println(
                "phoneNumberList > [123,456,789] -> true: " +
                        phoneNumberList.solution(new String[]{"123","456","789"})
        );
        System.out.println(
                "phoneNumberList > [12,123,1235,567,88] -> false: " +
                        phoneNumberList.solution(new String[]{"12","123","1235","567","88"})
        );
        System.out.println("------");
        System.out.println(
                "unreachedAthlete > [leo, kiki, eden] | [eden, kiki] -> leo: " +
                        unreachedAthlete.solution(
                                new String[]{"leo", "kiki", "eden"},
                                new String[]{"eden", "kiki"}
                        )
        );
        System.out.println(
                "unreachedAthlete > [marina, josipa, nikola, vinko, filipa] | [josipa, filipa, marina, nikola] -> vinko: " +
                        unreachedAthlete.solution(
                                new String[]{"marina", "josipa", "nikola", "vinko", "filipa"},
                                new String[]{"josipa", "filipa", "marina", "nikola"}
                        )
        );
        System.out.println(
                "unreachedAthlete > [mislav, stanko, mislav, ana] | [stanko, ana, mislav] -> mislav: " +
                        unreachedAthlete.solution(
                                new String[]{"mislav", "stanko", "mislav", "ana"},
                                new String[]{"stanko", "ana", "mislav"}
                        )
        );
        System.out.println("------");
        System.out.println(
                "bestAlbum > [classic, pop, classic, classic, pop] | [500, 600, 150, 800, 2500] -> [4, 1, 3, 0]: " +
                        Arrays.toString(bestAlbum.solution(
                                new String[]{"classic", "pop", "classic", "classic", "pop"},
                                new int[]{500, 600, 150, 800, 2500}
                        ))
        );
    }
}
