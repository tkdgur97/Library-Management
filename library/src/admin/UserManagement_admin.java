package admin;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import tcpserver.MemberDTO;
import tcpserver.TCPClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JButton;

import javax.swing.JComboBox;

/*
 * 유저 정보를 표로 변환
 */

@SuppressWarnings("serial")
public class UserManagement_admin implements ActionListener, MouseListener {

	private JComboBox<?> combo;
	private JTextField searchText;
	private JButton searchButton;
	private JTable infoTable;
	private ArrayList<MemberDTO> memberInfo;
	private JPanel tableMainPanel;
	private JScrollPane scrollPane;
	private JPanel mainPanel;

	public JPanel getPanel() {

		return mainPanel;
	}

	public UserManagement_admin() {

		// 메인 패널 설정
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 1173, 505);
		mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		// 검색 메뉴 만들기 combo
		String[] searchMenu = { "아이디", "이름" };
		combo = new JComboBox<>(searchMenu);
		combo.setBounds(397, 7, 76, 21);
		mainPanel.add(combo);

		// 입력 받는 부분 만들기
		searchText = new JTextField(15);
		searchText.setBounds(478, 7, 171, 21);
		mainPanel.add(searchText);

		// 검색 버튼 만들기
		searchButton = new JButton("검색");
		searchButton.setBounds(654, 6, 76, 23);
		mainPanel.add(searchButton);
		searchButton.addActionListener(this);

		// 표 만들기

		String[] column = { "아이디", "이름", "전화번호", "주소", "현재 대출 권수 ", "대출 횟 수 ", "연체 횟 수", "주민 번호" };
		memberInfo = new TCPClient().getUserInfoAll();
		Object row[][] = new Object[memberInfo.size()][column.length];
		for (int i = 0; i < row.length; i++) {
			MemberDTO dto = (MemberDTO) memberInfo.get(i);
			row[i][0] = dto.getId();
			row[i][1] = dto.getName();
			row[i][2] = dto.getTel();
			row[i][3] = dto.getAddress();
			row[i][4] = dto.getBookrentcurrent();
			row[i][5] = dto.getBookrentcumlative();
			row[i][6] = dto.getBooklate();
			row[i][7] = dto.getRrn();

		}

		DefaultTableModel dtm = new DefaultTableModel(row, column) { // 셀 수정 못하게 하는 부분
			public boolean isCellEditable(int a, int column) {
				return false;
			}
		};
		infoTable = new JTable(dtm);
		infoTable.getTableHeader().setReorderingAllowed(false); // 이동 불가
		infoTable.addMouseListener(this);

		// 스크롤 pane에 테이블 추가
		scrollPane = new JScrollPane(infoTable);
		scrollPane.setBounds(0, 0, 1122, 439);

		// 표를 넣을 패널 생성
		tableMainPanel = new JPanel();
		tableMainPanel.setBounds(12, 39, 1149, 439);
		tableMainPanel.setLayout(null);
		tableMainPanel.add(scrollPane);
		mainPanel.add(tableMainPanel);

	} // default constructor end

	public void refresh() {
		memberInfo = new TCPClient().getUserInfoAll();
		tableMainPanel.removeAll();
		String select = combo.getSelectedItem().toString();
		if (select.equals("아이디")) {
			ArrayList<MemberDTO> list = new ArrayList<>();
			for (int i = 0; i < memberInfo.size(); i++) {
				MemberDTO dto = (MemberDTO) memberInfo.get(i);
				if (dto.getId().indexOf(searchText.getText()) != -1) {

					list.add(dto);
				}

			} // for

			String[] column = { "아이디", "이름", "전화번호", "주소", "현재 대출 권수 ", "대출 횟 수 ", "연체 횟 수", "주민 번호" };

			// list에 있는 dto를 스트링 배열에 저장
			String[][] row2 = new String[list.size()][column.length];
			for (int i = 0; i < list.size(); i++) {
				MemberDTO dto = (MemberDTO) list.get(i);
				row2[i][0] = dto.getId();
				row2[i][1] = dto.getName();
				row2[i][2] = dto.getTel();
				row2[i][3] = dto.getAddress();
				row2[i][4] = dto.getBookrentcurrent();
				row2[i][5] = dto.getBookrentcumlative();
				row2[i][6] = dto.getBooklate();
				row2[i][7] = dto.getRrn();

			}
			DefaultTableModel dtm = new DefaultTableModel(row2, column) { // 셀 수정 못하게 하는 부분
				public boolean isCellEditable(int a, int column) {
					return false;
				}
			};
			infoTable = new JTable(dtm);
			infoTable.addMouseListener(this);
			scrollPane = new JScrollPane(infoTable);

			tableMainPanel.setLayout(null);
			tableMainPanel.add(scrollPane);
			scrollPane.setBounds(0, 0, 1122, 439);

			infoTable.setDragEnabled(false);
		} // if

		else if (select.equals("이름")) {
			ArrayList<MemberDTO> list = new ArrayList<>();
			for (int i = 0; i < memberInfo.size(); i++) {
				MemberDTO dto = (MemberDTO) memberInfo.get(i);
				if (dto.getName().indexOf(searchText.getText()) != -1) {

					list.add(dto);
				}

			} // for

			String[] column = { "아이디", "이름", "전화번호", "주소", "현재 대출 권수 ", "대출 횟 수 ", "연체 횟 수", "주민 번호" };

			// list에 있는 dto를 스트링 배열에 저장
			String[][] row2 = new String[list.size()][column.length];
			for (int i = 0; i < list.size(); i++) {
				MemberDTO dto = (MemberDTO) list.get(i);
				row2[i][0] = dto.getId();
				row2[i][1] = dto.getName();
				row2[i][2] = dto.getTel();
				row2[i][3] = dto.getAddress();
				row2[i][4] = dto.getBookrentcurrent();
				row2[i][5] = dto.getBookrentcumlative();
				row2[i][6] = dto.getBooklate();
				row2[i][7] = dto.getRrn();

			}
			DefaultTableModel dtm = new DefaultTableModel(row2, column) { // 셀 수정 못하게 하는 부분
				public boolean isCellEditable(int a, int column) {
					return false;
				}
			};
			infoTable = new JTable(dtm);
			infoTable.addMouseListener(this);
			scrollPane = new JScrollPane(infoTable);

			tableMainPanel.setLayout(null);
			tableMainPanel.add(scrollPane);
			scrollPane.setBounds(0, 0, 1122, 439);

			infoTable.setDragEnabled(false);
		}

	} // refresh end

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {

			refresh();

		} else if (e.getActionCommand().equals("check")) {

			refresh();

		}
	} // actionPerformed end

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getClickCount() == 2) {

			int a = infoTable.getSelectedRow();
			for (int i = 0; i < memberInfo.size(); i++) {
				MemberDTO dto = (MemberDTO) memberInfo.get(i);

				if (dto.getId().equals(infoTable.getValueAt(a, 0))) {

					String[] buttons = { "회원 정보 수정", "탈퇴 처리", "취소" };
					int c = JOptionPane.showOptionDialog(null, "아이디 : " + infoTable.getValueAt(a, 0), "회원 정보 수정",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째값");

					// 회원 정보 수정
					if (c == 0) {
						String id = infoTable.getValueAt(a, 0).toString();

						dto = new TCPClient().getUserInfo(id);
						UserUpdateJFrame_admin update = new UserUpdateJFrame_admin(dto);
						update.btnCheck.addActionListener(this);

						// 탈퇴 처리
					} else if (c == 1) {

						// 취소
					} else if (c == 2) {

					}
				}
			}
		}

	} // mousereleased end

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
} // class end
