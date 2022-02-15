package z_info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Database {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String ur1 = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "scott";
	String pass = "tiger";
	
	
	
	public Database() throws ClassNotFoundException {
		Class.forName(driver);
	}

	
	public void insert(InfoVO vo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		try {
		//2. 연결객체 얻어오기
		con = DriverManager.getConnection(ur1, user, pass);
		System.out.println("DB 연결 성공");
		
		//3. SQL 만들기
		
		String sql = "INSERT INTO info(name, id, tel, sex, age, home) " 
		+ " VALUES(?,?,?,?,?,?)";
		
		//4. SQL 전송객체 얻어오기
		
		 ps = con.prepareStatement(sql);
		ps.setString(1, vo.getName());
		ps.setString(2, vo.getId());
		ps.setString(3, vo.getTel());
		ps.setString(4, vo.getSex());
		ps.setInt(5, vo.getAge());
		ps.setString(6, vo.getHome());
		
		//5. 전송
		//int = executeUpdate
		//resultSet - executeQuery
		
		int result = ps.executeUpdate();
		System.out.println(result + "행 수행");
		
		} finally {
		//6. 닫기
		ps.close();
		con.close();
		}
	}
	
	
	public InfoVO searchByTel(String tel) throws Exception {
		
		InfoVO vo = new InfoVO();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		//2. 연결객체 얻어오기
		con = DriverManager.getConnection(ur1, user, pass);
		System.out.println("DB 연결 성공");
		
		//3 문장
		String sql ="SELECT * FROM info WHERE tel=?";
		
		//4.전송객체 얻어오기
		ps = con.prepareStatement(sql);
		
		ps.setString(1, tel); //위에 ?에 tel값이 들어가게 해줌
		
		
		
		//5. 전송
		//데이터 베이스에서 찾은 값을 불러서 rs에 저장
		rs = ps.executeQuery();

		//6. 결과처리
		//tel로 검색한 결과값을 불러오는 거
		if(rs.next()) {
			vo.setName( rs. getString("NAME"));
			vo.setId( rs. getString("ID"));
			vo.setTel( rs. getString("TEL"));
			vo.setSex( rs. getString("SEX"));
			vo.setAge( rs. getInt("AGE"));
			vo.setHome( rs. getString("HOME"));
		}
		
		} finally {
		
		rs.close();
		ps.close();
		con.close();
		}
		
		
		
		return vo;
	}

	
	public int delete(String tel) throws Exception {
		
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			//2. 연결객체 얻어오기
			con = DriverManager.getConnection(ur1, user, pass);
			System.out.println("DB 연결 성공");
			
			//3. SQL 만들기
			String sql = "DELETE FROM info WHERE tel = ?";
			
			//4. SQL 전송객체 얻어오기
			 ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			
			//5. 전송
			 result = ps.executeUpdate();
			System.out.println(result + "행 수행");
			
			} finally {
			//6. 닫기
			ps.close();
			con.close();
			}
		return result;
		}
		
		
	
	public int  modify(InfoVO vo) throws Exception {
	Connection con = null;
	PreparedStatement ps = null;
	int result = 0;
	try {
	//2. 연결객체 얻어오기
	con = DriverManager.getConnection(ur1, user, pass);
	System.out.println("DB 연결 성공");
	
	//3. SQL 만들기
	
	String sql = "UPDATE SET name = ?, id = ?, tel = ?, sex = ?, age = ? home = ? " + " WHERE tel=? "; // = 전화번호가 같다면
	
	//4. SQL 전송객체 얻어오기
	
	 ps = con.prepareStatement(sql);
	ps.setString(1, vo.getName());
	ps.setString(2, vo.getId());
	ps.setString(3, vo.getTel());
	ps.setString(4, vo.getSex());
	ps.setInt(5, vo.getAge());
	ps.setString(6, vo.getHome());
	
	//5. 전송
	//int = executeUpdate
	//resultSet - executeQuery
	
	 result = ps.executeUpdate();
	System.out.println(result + "행 수행");
	
	
	
	} finally {
	//6. 닫기
	ps.close();
	con.close();
	}
	
	return result;
}
	

	
// 전체 검색
public ArrayList<InfoVO> selectAll() throws Exception{
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		
		ArrayList<InfoVO> list = new ArrayList<InfoVO>();
		
		try {
		// 2.
		con = DriverManager.getConnection(ur1, user, pass);
		// 3
		String sql = "SELECT * FROM info";
		// 4
		ps = con.prepareStatement(sql);
		//5.
		rs = ps.executeQuery();
		
		while( rs.next()) {
			InfoVO vo = new InfoVO();
			vo.setName( rs.getString("NAME"));
			vo.setId( rs.getString("ID"));
			vo.setTel( rs.getString("Tel"));
			vo.setSex( rs.getString("SEX"));
			vo.setAge( rs.getInt("AGE"));
			vo.setHome( rs.getString("HOME"));
			list.add(vo);
					
		}
		
		}finally {
			rs.close();
			ps.close();
			con.close();
		}	
		
		return list;


}

//id로 찾기
public InfoVO searchByID(String tel) throws Exception {
	
	InfoVO vo = new InfoVO();
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	try {
	//2. 연결객체 얻어오기
	con = DriverManager.getConnection(ur1, user, pass);
	System.out.println("DB 연결 성공");
	
	//3 문장
	String sql ="SELECT * FROM info WHERE trim(id)=?";
	
	//4.전송객체 얻어오기
	ps = con.prepareStatement(sql);
	
//	ps.setString(1, id); //위에 ?에 tel값이 들어가게 해줌
	
	
	
	//5. 전송
	//데이터 베이스에서 찾은 값을 불러서 rs에 저장
	rs = ps.executeQuery();

	//6. 결과처리
	//tel로 검색한 결과값을 불러오는 거
	if(rs.next()) {
		vo.setName( rs. getString("NAME"));
		vo.setId( rs. getString("ID"));
		vo.setTel( rs. getString("TEL"));
		vo.setSex( rs. getString("SEX"));
		vo.setAge( rs. getInt("AGE"));
		vo.setHome( rs. getString("HOME"));
	}
	
	} finally {
	
	rs.close();
	ps.close();
	con.close();
	}
	
	
	
	return vo;
}


}
