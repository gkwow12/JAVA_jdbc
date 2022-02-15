package z_info;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InfotestOriginnal {

	//------------------------
	// [1] 멤버변수 선언
	
	JFrame f;
	JTextField tfName, tfId, tfTel, tfSex, tfAge, tfHome;
	JTextArea ta;
	JButton bAdd, bShow, bSearch, bDelete, bModify, bExit;
	
	Database db;
	
	//-------------------------
	// [2] 멤버변수 객체 생성
	
	InfotestOriginnal() {
		f = new JFrame("DB Test");		
		bAdd = new JButton("Add");
		bShow = new JButton("Show");
		bSearch = new JButton("Search");
		bDelete = new JButton("Delete");
		bModify = new JButton("Modify");
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
	         p.add(bModify);
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
		// 'Add' 버튼이 눌렸을 때
		bAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 각각의 텍스트필드에서 사용자의 입력값을 얻어오기
				String name = tfName.getText();
				String id = tfId.getText();
				String tel = tfTel.getText();
				String sex = tfSex.getText();
				int age = Integer.parseInt(tfAge.getText());
				String home = tfHome.getText();
				
				// 사용자 입력값들을 InfoVO 멤버로 지정
				InfoVO vo = new InfoVO();
				vo.setName(name);
				vo.setId(id);
				vo.setTel(tel);
				vo.setSex(sex);
				vo.setAge(age);
				vo.setHome(home);
				//InfoVO vo = new InfoVO(name,id,tel,sex,age, home);
				
				try {
					db.insert(vo);
					tfName.setText(null);
					
					selectAll();
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "입력실패:" + ex.getMessage());
				}
			}
		});
		
		// 전화번호 텍스트필드에서 엔터쳤을 때
		tfTel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchByTel();
			}
		});
		
		//서치 버튼 눌렀을 때
		bSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchByTel();
			}
		});
		
		//삭제 버튼 눌렀을 떄
		
		
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tel = tfTel.getText();
				try {
					int result = db.delete(tel);
					if(result == 1) {
						JOptionPane.showMessageDialog(null, "삭제 성공");
				}
			}catch(Exception ex) {
				System.out.println("삭제 실패 :" + ex.getMessage());
			}

			}
		});
		
		//수정 버튼을 눌렀을 떄
		
		bModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 각각의 텍스트필드에서 사용자의 입력값을 얻어오기
				String name = tfName.getText();
				String id = tfId.getText();
				String tel = tfTel.getText();
				String sex = tfSex.getText();
				int age = Integer.parseInt(tfAge.getText());
				String home = tfHome.getText();
				
				// 사용자 입력값들을 InfoVO 멤버로 지정
				InfoVO vo = new InfoVO();
				vo.setName(name);
				vo.setId(id);
				vo.setTel(tel);
				vo.setSex(sex);
				vo.setAge(age);
				vo.setHome(home);
				//InfoVO vo = new InfoVO(name,id,tel,sex,age, home);
				
				try {
					db.modify(vo);
					tfName.setText(null);
					// 나머지도
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "수정실패:" + ex.getMessage());
				}
			}
		});
		
		//Show 버튼을 눌렀을 떄
		
		bShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			selectAll();
		}
	});
		
	} // end of eventProc()
			
			
	//위에 이벤트에 불러올 함수 모음
			
			void selectAll() {
			try {
				ta.setText("----검색 결과----\n\n");
				ArrayList<InfoVO> result = db.selectAll();
				for(InfoVO vo : result) { 
					ta.append( vo.toString());
				}
				
			}catch(Exception ex) {
				System.out.println("검색실패:" + ex.getMessage());
			}
		}
			
	
	void searchByTel() {
		String tel = tfTel.getText();
		try {
			InfoVO result = db.searchByTel(tel);
			tfName.setText(result.getName());
			tfId.setText(result.getId());
			tfSex.setText(result.getSex());
			tfAge.setText(Integer.toString(result.getAge()));
			tfHome.setText(result.getHome());
			
		}catch(Exception ex) {
			System.out.println("검색실패:" + ex.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		InfotestOriginnal info = new InfotestOriginnal();
		info.addLayout();
		info.eventProc();

	}

}



	