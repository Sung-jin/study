public class Member {
    private long memberId;
    private String name;
    private String tel;
    private Team team; // 팀 연관관계가 추가됨.

    public Member(long memberId, String name, String tel, Team team) {
        this.memberId = memberId;
        this.name = name;
        this.tel = tel;
        this.team = team;
    }
    ...
}
