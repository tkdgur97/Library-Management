package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tcpserver.BookDTO;
import tcpserver.TCPClient;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;

import javax.swing.table.DefaultTableModel;

/*
 *  일반 사용자 도서 검색
 */
@SuppressWarnings("serial")
public class UserBookSearch implements ActionListener, MouseListener {

	private JPanel mainPanel;
	private JButton searchButton;
	private JComboBox<?> combo;
	private JTextField searchText;
	private JScrollPane scrollPane;
	private JPanel tableMainPanel;
	private JCheckBox rentCheckBox;
	private UserReservationJFrame resFrame;
	private DefaultTableModel dtm;
	public ArrayList<BookDTO> bookInfo;
	public JTable infoTable;
	public String id;

	public UserBookSearch() {
		// 패널만들기
		mainPanel = new JPanel();
		mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mainPanel.setLayout(null);

		// 검색 메뉴 만들기 combo
		String[] searchMenu = { "책 제목", "저자" };
		combo = new JComboBox<>(searchMenu);
		combo.setBounds(12, 6, 82, 21);
		mainPanel.add(combo);

		// 입력 받는 부분 만들기 jtext
		searchText = new JTextField(15);
		searchText.setBounds(106, 6, 225, 21);
		mainPanel.add(searchText);

		// 검색 버튼 만들기
		searchButton = new JButton("검색");
		searchButton.setBounds(343, 5, 71, 23);
		mainPanel.add(searchButton);

		// 대출 가능한 책만 보이게 하는 체크박스 만들기
		rentCheckBox = new JCheckBox("대출 가능한 책만");
		rentCheckBox.setBounds(422, 5, 139, 23);
		mainPanel.add(rentCheckBox);

		// DB에서 책정보 가져오기
		bookInfo = new TCPClient().getBookInfo();

		// 표 만들기 jtable
		String[] column = { "일련번호", "제목", "저자명", "발행처", "발행년도", "청구기호", "대출 여부" };
		String[][] row = new String[bookInfo.size()][column.length];

		// row값에 데이터 입력
		for (int i = 0; i < row.length; i++) {
			BookDTO dto = (BookDTO) bookInfo.get(i);
			row[i][0] = dto.getNumber();
			row[i][1] = dto.getTitle();
			row[i][2] = dto.getAuthor();
			row[i][3] = dto.getPublisher();
			row[i][4] = dto.getYear();
			row[i][5] = dto.getBill();
			row[i][6] = dto.getRent();
		}
		mainPanel.setBounds(12, 10, 600, 650);
		searchButton.addActionListener(this);

		tableMainPanel = new JPanel();
		tableMainPanel.setBounds(12, 37, 580, 610);
		mainPanel.add(tableMainPanel);
		tableMainPanel.setLayout(null);

		// 테이블에 데이터 입력
		dtm = new DefaultTableModel(row, column) { // 셀 수정 못하게 하는 부분
			public boolean isCellEditable(int a, int column) {
				return false;
			}
		};

		infoTable = new JTable(dtm);
		infoTable.addMouseListener(this);

		// 스크롤 pane에 테이블 추가
		scrollPane = new JScrollPane(infoTable);
		scrollPane.setBounds(0, 0, 580, 610);
		tableMainPanel.add(scrollPane);
		infoTable.getTableHeader().setReorderingAllowed(false); // 이동 불가

	} // default constructor end

	// 메인 패널 반환
	public JPanel getPanel() {
		return mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 도서 검색 이벤트 처리

		String select = combo.getSelectedItem().toString();
		if (e.getSource() == searchButton) {
			// DB에서 책정보 가져오기
			bookInfo = new TCPClient().getBookInfo();
			// 표 만들기 jtable
			String[] column = { "일련번호", "제목", "저자명", "발행처", "발행년도", "청구기호", "대출 여부" };

			ArrayList<BookDTO> list = new ArrayList<>();
			if (select.equals("책 제목")) {
				for (int i = 0; i < bookInfo.size(); i++) {
					BookDTO dto = (BookDTO) bookInfo.get(i);

					if (dto.getTitle().indexOf(searchText.getText()) != -1) {
						if (rentCheckBox.isSelected()) {
							if (dto.getRent().equals("Y")) {
								list.add(dto);
							}
						} else {

							list.add(dto);
						}

					}
				}
			} else if (select.equals("저자")) {
				for (int i = 0; i < bookInfo.size(); i++) {
					BookDTO dto = (BookDTO) bookInfo.get(i);

					if (dto.getAuthor().indexOf(searchText.getText()) != -1) {
						if (rentCheckBox.isSelected()) {
							if (dto.getRent().equals("Y")) {
								list.add(dto);
							}
						} else {

							list.add(dto);
						}

					}
				}
			}

			String[][] row = new String[list.size()][column.length];
			for (int i = 0; i < list.size(); i++) {
				BookDTO dto = (BookDTO) list.get(i);
				row[i][0] = dto.getNumber();
				row[i][1] = dto.getTitle();
				row[i][2] = dto.getAuthor();
				row[i][3] = dto.getPublisher();
				row[i][4] = dto.getYear();
				row[i][5] = dto.getBill();
				row[i][6] = dto.getRent();
			}
			dtm.setDataVector(row, column);

		} else if (e.getActionCommand().equals("check")) {
			dtm.setValueAt("N", infoTable.getSelectedRow(), 6);

		} else if (e.getActionCommand().equals("cancel")) {
			String cancelTitle = resFrame.reserCancelButton.getToolTipText();
			System.out.println(cancelTitle);
			for (int i = 0; i < dtm.getRowCount(); i++) {
				if (dtm.getValueAt(i, 1).toString().equals(cancelTitle)) {
					dtm.setValueAt("Y", i, 6);
					break;
				}

			}
		}

	} // actionPerformed end

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getClickCount() == 2) {
			int row = infoTable.getSelectedRow();
			// 제목 가져오기
			String title = (String) dtm.getValueAt(row, 1);

			if (dtm.getValueAt(row, 6).toString().equals("Y")) {
				resFrame = new UserReservationJFrame(id, title);
				resFrame.checkButton.addActionListener(this);
				resFrame.reserCancelButton.addActionListener(this);

			} else {
				JOptionPane.showMessageDialog(null, "이미 대출 중인 도서 입니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
			}

		} // if end

	} // mouseReleased end

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

} // class end
