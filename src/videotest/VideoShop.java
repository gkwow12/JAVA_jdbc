package videotest;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class VideoShop extends JFrame{
	CustomerView cv;
	VideoView vv;
	RentView rv;
	
	public VideoShop() {
		cv = new CustomerView();
		vv = new VideoView();
		rv = new RentView();
		
		//**********화면 탭 관리**********
		// 탭별로 색깔 다르게
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("고객 관리", cv);
		pane.addTab("비디오 관리", vv);
		pane.addTab("대여 관리", rv);
		
		//대여 관리 탭을 먼저 나오게
		pane.setSelectedIndex(2);
		
		//위에서 만든 탭창 화면에 붇이기
		add(pane, BorderLayout.CENTER);
		
	setBounds(300,300,600,500);
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
	
	public static void main(String[] args) {
		new VideoShop();

	}

}
