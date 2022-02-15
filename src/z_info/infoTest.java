package z_info;

import java.awt.BorderLayout;

//db 연동 첫째날만 하던 거 잘못 작성해서 InfotestOriginnal에 다시 작성함
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class infoTest {
	//------------------------
	// [1] 멤버변수 선언
	
	JFrame f;
	JTextField tfName, tfId, tfTel, tfSex, tfAge, tfHome;
	JTextArea ta;
	JButton bAdd, bShow, bSearch, bDelete, bCancel, bExit;
	
	Database db;
	//-------------------------
	// [2] 멤버변수 객체 생성
	
	infoTest() {
		f = new JFrame("DB Test");		
		bAdd = new JButton("Add");
		bShow = new JButton("Show");
		bSearch = new JButton("Search");
		bDelete = new JButton("Delete");
		bCancel = new JButton("Cancel");
		bExit = new JButton("Exit");
		ta = new JTextArea();		
		
		tfName = new JTextField(15);
		tfId = new JTextField();
		tfTel = new JTextField();
		tfSex = new JTextField();
		tfAge = new JTextField();
		tfHome = new JTextField();
		
		try {
			db = new Database();
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		}
	}

	
	//---------------------------
	// [3] 화면 붙이기
	void addLayout() {
		 f.setLayout(new BorderLayout());
	     
		 f.add(ta, BorderLayout.CENTER);
	     	      
	         // 버튼영역-south
	         JPanel p = new JPanel();	         
	         p.add(bAdd);
	         p.add(bShow);
	         p.add(bSearch);
	         p.add(bDelete);
	         p.add(bCancel);
	         p.add(bExit);
	      f.add(p, BorderLayout.SOUTH);

	      	// 라벨+텍스트필드 -west
	        JPanel pwest = new JPanel();
	        pwest.setLayout(new GridLayout(6,2));
	        pwest.add(new JLabel("Name"));
	        pwest.add(tfName);
	        pwest.add(new JLabel("Id"));
	        pwest.add(tfId);
	        pwest.add(new JLabel("Tel"));
	        pwest.add(tfTel);
	        pwest.add(new JLabel("Sex"));
	        pwest.add(tfSex);
	        pwest.add(new JLabel("Age"));
	        pwest.add(tfAge);
	        pwest.add(new JLabel("Home"));
	        pwest.add(tfHome);
		
	        f.add(pwest, BorderLayout.WEST);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100,200,800,350);
		f.setVisible(true);
	}
	
	
	//----------------------------
	// [4] 이벤트처리
	void eventProc() {
		// add 버튼을 눌렀을 때
		bAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//각각의 텍스트 필드에서 사용자의 입력값 얻어오기
				String name = tfName.getText();
				String id = tfId.getText();
				String tel = tfTel.getText();
				String sex = tfSex.getText();
				int age = Integer.parseInt(tfAge.getText());
				String home = tfHome.getText();
				
				//사용자의 입력값들을 infoVO 멤버로 지
				// getter, setter 를 이용한 방법
				InfoVO vo = new InfoVO();
				vo.setName(name);
				vo.setId(id);
				vo.setTel(tel);
				vo.setSex(sex);
				vo.setAge(age);
				vo.setHome(home);
				
				
				try {
				db.insert(vo);
				
				//입력 성공하면 화면 글씨 지우기
				tfName.setText(null);
				tfId.setText(null);
				tfTel.setText(null);
				tfSex.setText(null);
				tfAge.setText(null);
				tfHome.setText(null);
				
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "입력실패" + ex.getMessage());
				}
				
			}
		});
		
		
		//전화번호 텍스트필드에서 엔터쳤을 때 정보 나오게 하기
		// 전화번호 텍스트필드에서 엔터쳤을 때
		
		tfTel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
		String tel = tfTel.getText();
		
		try {
			InfoVO result = db.searchByTel(tel);
			tfName.setText(result.getName());
			tfId.setText(result.getId());
			tfSex.setText(result.getSex());
			tfAge.setText(Integer.toString(result.getAge()));
			tfHome.setText(result.getHome());
			
			//나머지는 이따가 
		}catch(Exception ex) {
			System.out.println("검색 실패 :" + ex.getMessage());
		}
		
		
		}
			});

	
		
		
		//주민번호 입력장에서 엔터쳤을 때
		tfId.addActionListener(new ActionListener()
		{
	public void actionPerformed(ActionEvent e) {
		//성별을 구해서
		
		String id = tfId.getText();     //읽어온 주민번호 저장할거야
		
		//
		char sex = id.charAt(7);       // 주민번호에서 7번째 문자 저장해서 성별 구별할거야
		switch (sex) {
		case '1' : tfSex.setText("남자"); break;
		case '2' : tfSex.setText("여자"); break;
		case '3' : tfSex.setText("남자"); break;
		case '4' : tfSex.setText("남자"); break;
		
		} 
		
		
	
		//출신지를 구해서
		
		String juso = tfId.getText();      //읽어온 주민번호 저장할거야
		
		char adrass = juso.charAt(8);      //주민번호 8번째 문자 변수에 저장해서 출신지 구별할거야 
		switch(adrass) {
		case '0': tfHome.setText("서울");break;
		case '1': tfHome.setText("인천/부산");break;
		case '2': tfHome.setText("경기");break;	
		case '9': tfHome.setText("제주");break;
		}
		
		
		
		// 나이를 구해서
		String nai = tfId.getText();                    // 
		String how = tfId.getText();
		
		//나이를 알 수 있는 앞 두자리 int형으로 변환(if 문에서 계산하기 위해서)
		int old = Integer.parseInt(nai.substring(0, 2));
		
		//성별에 따라 나이 계산하기 (1,2 => 1900년생 / 3,4 => 2000년생이라서 계산 방법이 다름)
		char age = how.charAt(7);
		
		// 주민번호 뒷자리가  1,2자리로 시작하는 경우 이렇게 계산
		if(age == '1'|| age == '2' ) { 
			old=2022-(1900+old)+1;
			
			//다시 String 으로 변환해서 화면에 나오게 하기 
			String lastOld = Integer.toString(old);
			 tfAge.setText(lastOld);
		
			// 주민번호 뒷자리가  3.4 자리로 시작하는 경우 이렇게 계산
		}else if (age == '3'|| age == '4' )	{
			old=2022-(2000+old)+1;}
		   
		   //다시 String 으로 변환해서 화면에 나오게 하기 
		   String lastOld = Integer.toString(old);
		   tfAge.setText(lastOld);
		
		
		//tfAge.setText(String.valueOf(30));
		
	
	}
		});

		
		
		//취소 버튼이 눌렀을 때
		bCancel.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent e) {
				tfName.setText(" ");
				tfId.setText(" ");
				tfId.setText(" ");
				tfTel.setText(" ");
				tfSex.setText(" ");
				tfAge.setText(" ");
				tfHome.setText(" ");
			}
				});
		
	
		
		
		
		//종료 버튼이 눌렸으 ㄹ때 프로그램 종료
		bExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}		
		});

	}
	
	public static void main(String[] args) {
		infoTest info = new infoTest();
		info.addLayout();
		info.eventProc();

	}

}
