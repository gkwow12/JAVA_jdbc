package  gui3.emp;

import java.sql.*;
import java.util.*;

import z_info.InfoVO;

public class EmpModelImpl implements EmpModel{
	
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String ur1 = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "scott";
	String pass = "tiger";
	/*---------------------------------------------
	 * 생성자 함수 
	 * 
	 * 
	 	1. DB 연동
	 		- 드라이버 등록
	*/

	public EmpModelImpl () throws ClassNotFoundException {
		Class.forName(driver);
	}

	/*-------------------------------------------------------
	* insert() :  입력한 값 받아서 데이타베이스에 추가
		0. 연결객체 얻어오기
		1. sql문 만들기 ( insert 구문 )
		2. PreparedStatement 객체 생성 
		3. PreparedStatement에 인자 지정
		4. sql문 전송 ( executeUpdate() 이용 )
		5. PreparedStatement 닫기
		6. 연결 닫기
	*/
	public void insert( EmpVO r ) throws SQLException{	

		Connection con = null;
		PreparedStatement ps = null;
		try {
		
		con = DriverManager.getConnection(ur1, user, pass);
		System.out.println("DB 연결 성공");
		
		String sql = "INSERT INTO EMP (empno, ename, sal, job)" 
				+ "  VALUES ( ?,?,?,?)";
		
		ps = con.prepareStatement(sql);
		ps.setInt(1, r.getEmpno());
		ps.setString(2, r.getEname());
		ps.setInt(3, r.getSal());
		ps.setString(4, r.getJob());
		
		
		int result = ps.executeUpdate();
		System.out.println(result + " 행 수행");
		
		} finally {
			ps.close();
			con.close();
		}
		
		
	}


	/*-------------------------------------------------------
	* modify() : 화면 입력값 받아서 수정
		0. 연결객체 얻어오기
		1. sql문 만들기 ( update 구문 )
		2. PreparedStatement 객체 생성 ( 또는 Statement )
		3. PreparedStatement에 인자 지정
		4. sql문 전송 ( executeUpdate() 이용 )
		5. PreparedStatement 닫기
		6. 연결 닫기
	*/
	public void modify( EmpVO r ) throws SQLException{	

		Connection con = null;
		PreparedStatement ps = null;
		
		
		
		try {
		
		con = DriverManager.getConnection(ur1, user, pass);
		System.out.println("DB 연결 성공");
		
		String sql = "UPDATE emp SET empno =?, ename = ?, sal = ?, job =?" 
				+ " WHERE empno =?";
		
		ps = con.prepareStatement(sql);
		ps.setInt(1, r.getEmpno());
		ps.setString(2, r.getEname());
		ps.setInt(3, r.getSal());
		ps.setString(4, r.getJob());
		ps.setInt(5, r.getEmpno());
		
		
		int result = ps.executeUpdate();
		System.out.println(result + " 행 수행");
		
		} finally {
			ps.close();
			con.close();
		}
		
	
	}

	
	/*-------------------------------------------------------
	* selectByEmpno() :  입력받은 사번을 받아서 해당 레코드 검색
		0. 연결객체 얻어오기
		1. sql문 만들기 ( select 구문 )
		2. PreparedStatement 객체 얻기 ( Statement  가능 )
		4. sql문 전송 ( executeQuery() 이용 )
		5. 결과집합(ResultSet)에서 값을 읽어서 EmpVO에 저장
		6. ResultSet/ PreparedStatement 닫기
		7. 연결 닫기
		8. EmpVO 객체 리턴
	*/	
	public EmpVO selectByEmpno( int empno ) throws SQLException{
		EmpVO vo = new EmpVO();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("DB 연결 성공");
			
			String sql = "SELECT * FROM emp WEHRE empno =? ";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, empno);
			
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				vo.setEmpno(rs.getInt("EMPNO"));
				vo.setEname(rs.getString("ENAME"));
				vo.setSal(rs.getInt("SAL"));
				vo.setJob(rs.getString("JOB"));
			
		}
			
	}finally {
		
		rs.close();
		ps.close();
		con.close();
	}
		return vo;	
	}
	
	/*--------------------------------------------------------
	* delete() : 사원번호 값을 받아 해당 레코드 삭제
		0. 연결객체 얻어오기
		1. sql문 만들기 ( delete 구문 )
		2. PreparedStatement 객체 얻기
		3. sql문 전송 ( executeUpdate() 이용 )
		4. PreparedStatement 닫기
		5. 연결 닫기
	*/
	public int delete( int empno ) throws SQLException{
		int resultCnt = 0;		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("DB 연결 성공");
			
			String sql = "DELETE FROM emp WHERE empno = ?";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, empno);
			
			resultCnt = ps.executeUpdate();
			System.out.println(resultCnt + "행 수행");
			
		}finally {
			ps.close();
			con.close();
		}
			
		
		return resultCnt;
	}
	
	/*-------------------------------------------------------
	* selectAll() :  전체 레코드 검색
		0. 연결객체 얻어오기
		1. sql문 만들기 ( select 구문 )
		2. PreparedStatement 객체 얻기 ( Statement  가능 )
		4. sql문 전송 ( executeQuery() 이용 )
		5. 결과집합(ResultSet)에서 값을 읽어서 ArrayList에 저장
		6. ResultSet/ PreparedStatement 닫기
		7. 연결 닫기
		8. ArrayList 객체 리턴
	*/	
	public ArrayList<EmpVO> selectAll() throws SQLException{
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
		ArrayList<EmpVO> list = new ArrayList<EmpVO>();
		
		try {
	
			con = DriverManager.getConnection(ur1, user, pass);
			
			String sql = "SELECT * FROM emp";
			
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
		
			while(rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEmpno(rs.getInt("EMPNO"));
				vo.setEname(rs.getString("ENAME"));
				vo.setSal(rs.getInt("SAL"));
				vo.setJob(rs.getString("JOB"));
				list.add(vo);
			}
			}finally {
				rs.close();
				ps.close();
				con.close();
			}
				
			
		return list;
	}

}