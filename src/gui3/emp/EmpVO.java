package gui3.emp;
public class EmpVO {

	
	//emp 사원테이블의 레코드를 저장할 수 있는 클래스
	int empno;
	String ename;
	int sal;
	String job;
	
	public EmpVO() {
		
	}

	public EmpVO(int empno, String ename, int sal, String job) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.sal = sal;
		this.job = job;
	}

	public String toString( ) {
		return empno + "\t" + ename +"\t" + sal +"\t" + job + "\n";
	}
	
	
	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public int getSal() {
		return sal;
	}

	public void setSal(int sal) {
		this.sal = sal;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	
	

}