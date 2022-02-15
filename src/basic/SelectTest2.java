package basic;


import java.sql.*;

public class SelectTest2 {

	public static void main(String[] args) {
		String ur1 = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "scott";
		String pass = "tiger";
		
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			//1. 드라이버 메모리에 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		

			//2. 연결 객체 얻어오기
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("DB 연결 성공");
			
			// 3. SQL 만들기
			String sql = "SELECT * FROM emp WHERE empno=7788";
			
			
			//4. SQL 전송객체 얻어오기
			//	Statement :  단순한 쿼리
			// **preparedStatement** :  주로 사용  
			// CallableStatement : pl sql의 함수를 호출할 때
			ps = con.prepareStatement(sql);
			
			//5. 전송
			
			rs = ps.executeQuery();
			
			//6. 결과처리
			if(rs.next()) {
				int empno = rs.getInt("EMPNO");
				String ename = rs.getString("ENAME");
				String job = rs.getString("JOB");
				int manager = rs.getInt("MGR");
				int salary = rs.getInt("SAL");
				int comm = rs.getInt("COMM");
				int deptno = rs.getInt("DEPTNO");
			
				
				System.out.println(empno + "/" + ename + "/" + job + "/" + manager + "/" + salary + "/" + comm + "/" + deptno);
				
			} 
			
			//7. 닫기
			
			System.out.println("프로그램 종료");
		}catch(Exception e) {
			System.out.println("실패 : " + e.getMessage());
			
			
		}finally {
			try { 
			rs.close();
			ps.close();
			con.close();
			}catch(Exception e) {}
			
		}

	}

}
