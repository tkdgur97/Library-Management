package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import tcpserver.TCPClient;

import javax.swing.JButton;

/*
 *  아이디 비밀번호 찾기 화면
 */

@SuppressWarnings("serial")
public class IdPwFindJFrame extends JFrame implements ActionListener {
	private JTextField nameField;
	private JTextField telField;
	private JTextField idField;
	private JTextField nameField1;
	private JButton btnFind;
	private JButton btnFind1;

	public IdPwFindJFrame() {

		// 메인 프레임 설정
		setTitle("아이디/비밀번호 찾기");
		setSize(400, 550);
		getContentPane().setLayout(null);

		// 탭 설정
		JTabbedPane find = new JTabbedPane(JTabbedPane.TOP);
		find.setBounds(12, 10, 360, 491);
		getContentPane().add(find);

		// 아이디, 비밀번호 찾기 탭 추가
		JPanel idPan = new JPanel();
		idPan.setLayout(null);
		idPan.setBounds(10, 10, 380, 530);

		JPanel pwPan = new JPanel();
		pwPan.setLayout(null);
		pwPan.setBounds(10, 10, 380, 530);
		find.add("아이디 찿기", idPan);
		find.add("비밀번호 찿기", pwPan);

		// 비밀번호 탭 아이디 라벨
		JLabel idLab = new JLabel("아이디");
		idLab.setFont(new Font("굴림", Font.PLAIN, 14));
		idLab.setBounds(32, 122, 56, 15);
		pwPan.add(idLab);

		// 비밀번호 탭 아이디 텍스트필드
		idField = new JTextField();
		idField.setColumns(10);
		idField.setBounds(106, 120, 237, 21);
		pwPan.add(idField);

		// 비밀번호 탭 이름 라벨
		JLabel nameLab1 = new JLabel("이름");
		nameLab1.setFont(new Font("굴림", Font.PLAIN, 14));
		nameLab1.setBounds(32, 183, 56, 15);
		pwPan.add(nameLab1);

		// 비밀번호 탭 이름 텍스트필드
		nameField1 = new JTextField();
		nameField1.setColumns(10);
		nameField1.setBounds(106, 181, 237, 21);
		pwPan.add(nameField1);

		// 비밀번호 탭 찾기 버튼
		btnFind1 = new JButton("찾기");
		btnFind1.setBounds(106, 315, 125, 37);
		btnFind1.addActionListener(this);
		pwPan.add(btnFind1);

		// 아이디 탭 이름 라벨
		JLabel nameLab = new JLabel("이름");
		nameLab.setFont(new Font("굴림", Font.PLAIN, 14));
		nameLab.setBounds(32, 122, 41, 15);
		idPan.add(nameLab);

		// 아이디 탭 이름 텍스트필드
		nameField = new JTextField();
		nameField.setBounds(106, 120, 237, 21);
		idPan.add(nameField);
		nameField.setColumns(10);

		// 아이디 탭 전화번호 라벨
		JLabel telLab = new JLabel("전화번호");
		telLab.setFont(new Font("굴림", Font.PLAIN, 14));
		telLab.setBounds(32, 183, 56, 15);
		idPan.add(telLab);

		// 아이디 탭 전화번호 텍스트필드
		telField = new JTextField();
		telField.setColumns(10);
		telField.setBounds(106, 181, 237, 21);
		idPan.add(telField);

		// 아이디 탭 찾기 버튼
		btnFind = new JButton("찾기");
		btnFind.setBounds(106, 301, 125, 37);
		btnFind.addActionListener(this);
		idPan.add(btnFind);

		setVisible(true);
	} // default constructor end

	@Override
	public void actionPerformed(ActionEvent e) {

		// 아이디 찾기 버튼을 눌렀을 경우
		if (e.getSource() == btnFind) {
			String name = nameField.getText();
			String tel = telField.getText();
			String result = new TCPClient().idFind(name + "\n" + tel);
			if (!result.equals("null") && !(result == null)) {
				JOptionPane.showMessageDialog(null, "아이디 : " + result);
			} else {
				JOptionPane.showMessageDialog(null, "해당 이름과 전화번호의 아이디가 없습니다.");
			}

			// 비밀번호 찾기 버튼을 눌렀을 경우
		} else if (e.getSource() == btnFind1) {
			String id = idField.getText();
			String name = nameField1.getText();
			String result = new TCPClient().pwFind(id + "\n" + name);

			if (!result.equals("null") && !(result == null)) {
				JOptionPane.showMessageDialog(null, "비밀번호 : " + result);
			} else {
				JOptionPane.showMessageDialog(null, "해당 아이디와 이름의 비밀번호가 없습니다.");
			}
		}

	} // actionPerformed end
} // class end
