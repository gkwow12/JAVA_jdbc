package jfreechart;

import java.sql.*;
import java.util.*;

public class Database {

	String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String USER ="scott";
	String PASS = "tiger";

	public ArrayList<ArrayList> getData() {

		ArrayList<ArrayList> data = new ArrayList<ArrayList>(); // 2차원 배열
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(URL, USER , PASS);	
			
			//************************차트에 올라오는 쿼리 작성***************************************
			//(1)사원 수
			//String sql = "SELECT sal, ename FROM emp";
			
			//(2) 월별 입사한 사원 수
			/*String sql = "SELECT count(*) CNT, NVL(TO_CHAR(hiredate, 'MM'),'etc') HIRE_MONTH "
					+ "FROM emp  "
					+ "GROUP BY TO_CHAR(hiredate, 'MM')  "
					+ "order by TO_CHAR(hiredate, 'MM')";*/
			
			//(3) 업무별 평균 월급
			/*String sql = "SELECT NVL(job,'임시'), job, round(avg(NVL(sal),0)) avg  "
					+ "FROM emp  "
					+ "GROUP BY job";*/
			//(4) 월급을 많이 받는 사원 10명
			String sql = "SELECT ename, sal  "
					+ "  FROM (SELECT ename, sal"
					+ "   FROM emp  "
					+ "   order by sal desc nulls last)  "
					+ "WHERE rownum <= 10";
			
			//***************************************************************
			
			PreparedStatement stmt = con.prepareStatement( sql );	
			
			
			ResultSet rset = stmt.executeQuery();

			// *** 인자에 맞게 컬럼명 쓰기 ***
			while( rset.next() ){
				ArrayList temp = new ArrayList();
				temp.add( rset.getInt("sal"));				//****************
				temp.add( rset.getString("ename"));		//****************		
				data.add(temp);
			}
			rset.close();
			stmt.close();
			con.close();
		} catch(Exception ex){
			System.out.println("에러 : " + ex.getMessage() );
		}
		return data;
	}
}
