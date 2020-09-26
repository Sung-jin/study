public class MemberDAO {
    public Member find(int memeberId) {...}
    public Member findWithTeam(int memeberId) {
        String sql = """
                SELECT m.member_id, m.tel, m.tel, t.team_id, t.team_name
                FROM member m
                JOIN team t ON m.team_id = t.team_id
        """;

        ...
    }
    public void save(Member member) {...}
}

// 기존 member 테이블에 team 과의 관계가 추가되면서 team 을 join 하여 select 하여 조회하는 기능이 추가되었다.
// JDBC API 를 사용해서 관계가 생긴 테이블을 사용하기 위해서는 전적으로 사용하는 SQL 에 의해 달려있다.
// 즉, 기존에 find 는 그대로 사용하고 team 의 join 된 정보가 필요할 경우 findWithTeam 을 생성한 것처럼 SQL 에 따라서 가져와지는 데이터가 의존되어 있기 때문이다.
// 데이터 접근 계층을 사용해서 SQL 을 숨겨도 어쩔 수 없이 DAO 를 열어서 어떤 SQL 이 실행되는지 확인해야 한다.

// Member 나 Team 과 같은 모델링한 객체를 엔티티라 한다.
// SQL 에 모든 것을 의존하는 상황에서 개발자들은 엔티티를 신뢰하고 사용할 수 없다. -> 연관관계나 컬럼이 추가 될 때마다 DAO 도 같이 변경되어야 하고 조회에 필요한 데이터의 수나 방법 등에 따라 그때그때 달라지기 때문이다.
// 즉, DAO 를 열어서 사용되는 SQL 을 그때그때 확인해서 리턴되는 객체들이 어떤 것들인지 알고 로직을 짜야되는 상황이 많아지게 된다.
// 이는 진정한 의미의 계층 분할이 아니며, 물리적으로 SQL 과 JDBC API 를 데이터 접근 계층에 숨기는 데 성공했을지 몰라도 논리적으로 엔티티와 아주 강한 의존 관계를 가진다.
// -> 강한 의존관계 떄문에 회원 조회나 회원 객체에 필드를 하나 추가할 때마다 DAO 의 CRUD 코드와 SQL 대부분을 변경하게 된다.

// 문제점 정리
// 1. 진정한 의미의 계층 분할이 어렵다.
// 2. 엔티티를 신뢰할 수 없다.
// 3. SQL 에 의존적인 개발을 피하기 어렵다.
