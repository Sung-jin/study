public class MemberDAO {
    public Member find(int memeberId) {
        // 기존 회원 조회용 SQL
        // String sql = "SELECT member_id, name FROM member m WHERE meber_id = ?";
        // tel 이 추가된 회원 조회용 SQL
        String sql = "SELECT member_id, name, tel FROM member m WHERE meber_id = ?";

        // 위 쿼리를 JDBC API 를 사용할 경우
        ResultSet rs = stmt.executeQuery(sql);

        // JDBC API 를 사용한 결과를 Member 객체에 맵핑
        ...
        String tel = rs.getString("tel");
        Member member = new Member(memberId, name, tel);
    }
    public void save(Member member) {
        // 기존 회원 생성용 SQL
        // String sql = "INSERT INTO member(member_id, name) values(?, ?)";
        // 변경된 생성 sql
        String sql = "INSERT INTO member(member_id, name, tel) values(?, ?, ?)";

        // 위 쿼리에 회원 객체의 값들을 넣어서 SQL 에 전달
        ...
        pstmt.setString(3, member.getTel());

        // JDBC API 를 사용하여 SQL 실행
        pstmt.executeUpdate(sql);
    }
}

// 위와 같이 수정, 삭제도 컬럼이 추가 될 때마다 대응해줘야 한다.
