package basic;

import java.sql.*;


public class InsertTest2 {

	
	
	public static void main(String[] args) {
		String ur1 = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "scott";
		String pass = "tiger";
		
		
		try {
			//1. 드라이버 메모리에 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2. 연결 객체 얻어오기
			Connection con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("DB 연결 성공");
			
			//3. SQL 만들기
			
			int empno = 7788;
			String job = "개발";
			int sal = 8000;
			
			String sql = "UPDATE emp SET job = ?', sal = ? "+" WHERE empno = ?";
			System.out.println(sql);
			//4. SQL 전송객체 얻어오기

			//완벽한 쿼리를 사용함 
			//Statement ps = con.createStatement();
			
			
			
			
			//5. 전송
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, job);
			ps.setInt(2, sal);
			ps.setInt(3, empno);
			ps.executeUpdate();
			
			
			//6. 닫기
			ps.close();
			con.close();
			
		} catch (Exception e) {
			System.out.println("예외 :" + e.getMessage());
			
		}
	}

}
