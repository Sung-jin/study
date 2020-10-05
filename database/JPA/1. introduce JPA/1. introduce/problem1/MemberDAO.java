public class MemberDAO {
    // JDBC API 를 이용하여 개발한 경우
    public Member find(int memeberId) {
        String sql = "SELECT member_id, name FROM member m WHERE meber_id = ?";

        // 위 쿼리를 JDBC API 를 사용할 경우
        ResultSet rs = stmt.executeQuery(sql);

        // JDBC API 를 사용한 결과를 Member 객체에 맵핑
        long memberId = rs.getInt("member_id");
        String name = rs.getString("name");
        Member member = new Member(memberId, name);
    }
    public void save(Member member) {
        // 회원 생성용 SQL
        String sql = "INSERT INTO member(member_id, name) values(?, ?)";

        // 위 쿼리에 회원 객체의 값들을 넣어서 SQL 에 전달
        pstmt.setString(1, member.getMemeberId());
        pstmt.setString(2, member.getName());

        // JDBC API 를 사용하여 SQL 실행
        pstmt.executeUpdate(sql);
    }
}

// 멤버의 수정, 삭제도 위 방식으로 반복되는 작업을 하면 된다.
// 즉, 다른 테이블이 생길때 마다 CRUD 를 반복해서 작업을 하게 된다. -> 테이블의 수 만큼 반복되는 작업을 해야 한다.
// 또한, 자바 컬렉션에 보관된 데이터를 데이터베이스에 저장하려고 하면 객체지향 애플리케이션과 데이터베이스 중간에서 SQL 과 JDBC API 를 사용해서 변환 작업을 직접 해주어야 한다.
