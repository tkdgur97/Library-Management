package admin;

import java.util.ArrayList;

import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import tcpserver.ReturnDTO;
import tcpserver.TCPClient;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * 도서 반환 
 */

@SuppressWarnings("serial")
public class BookReturn_admin implements ActionListener, MouseListener {
	private JPanel mainPanel;
	private ArrayList<?> returnInfo;
	private JTable infoTable;
	private DefaultTableModel dtm;
	private DefaultTableModel dtm2;
	private JButton returnButton;
	private JButton returnCancelButton;
	private JButton returnCheckButton;
	private JTable infoTable2;
	private int row = -1;
	private int row2 = -1;
	private String[] value;
	private JButton refreshButton;

	public BookReturn_admin() {

		// 메인 패널 설정
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 582, 688);
		mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		// DB에서 반납정보 가져오기
		returnInfo = new TCPClient().getReturnInfoAll();

		// 반납 정보 표만들기
		String[] column = { "Id", "Title", "반납신청일" };
		String[][] row = new String[returnInfo.size()][column.length];

		// row값에 데이터 입력
		for (int i = 0; i < row.length; i++) {
			ReturnDTO dto = (ReturnDTO) returnInfo.get(i);
			row[i][0] = dto.getId();
			row[i][1] = dto.getTitle();
			row[i][2] = dto.getApplyday();
		}

		// 테이블에 데이터 입력
		dtm = new DefaultTableModel(row, column) { // 셀 수정 못하게 하는 부분
			public boolean isCellEditable(int a, int column) {
				return false;
			}
		};
		infoTable = new JTable(dtm);

		// 표를 넣을 패널 설정
		JPanel tableMainPanel = new JPanel();
		tableMainPanel.setLayout(null);
		tableMainPanel.setBounds(12, 37, 461, 225);
		mainPanel.add(tableMainPanel);

		// 스크롤 pane에 데이터추가
		JScrollPane scrollPane = new JScrollPane(infoTable);
		scrollPane.setBounds(0, 0, 461, 226);
		tableMainPanel.add(scrollPane);

		// ▼버튼을 눌렀을 때 내려온 데이터를 담을 테이블 생성
		String[] column2 = { "Id", "Title", "반납신청일" };
		String[][] row2 = new String[0][column2.length];

		// 테이블에 데이터 입력
		dtm2 = new DefaultTableModel(row2, column2) { // 셀 수정 못하게 하는 부분
			public boolean isCellEditable(int a, int column) {
				return false;
			}
		};
		infoTable2 = new JTable(dtm2);

		// 표를 넣을 패널 설정
		JPanel tableMainPanel2 = new JPanel();
		tableMainPanel2.setLayout(null);
		tableMainPanel2.setBounds(12, 316, 461, 225);
		mainPanel.add(tableMainPanel2);

		// 스크롤 pane에 테이블 추가
		JScrollPane scrollPane_1 = new JScrollPane(infoTable2);
		scrollPane_1.setBounds(0, 0, 461, 226);
		tableMainPanel2.add(scrollPane_1);

		// ▲ 버튼 설정
		returnCancelButton = new JButton("▲");
		returnCancelButton.addActionListener(this);
		returnCancelButton.setFont(new Font("굴림", Font.BOLD, 30));
		returnCancelButton.setBounds(247, 272, 68, 38);
		mainPanel.add(returnCancelButton);

		// ▼ 버튼 설정
		returnButton = new JButton("▼");
		returnButton.addActionListener(this);
		returnButton.setFont(new Font("굴림", Font.BOLD, 30));
		returnButton.setBounds(168, 272, 68, 38);
		mainPanel.add(returnButton);

		// 반납확인 버튼 설정
		returnCheckButton = new JButton("반납 확인");
		returnCheckButton.setBounds(168, 584, 142, 38);
		returnCheckButton.addActionListener(this);
		mainPanel.add(returnCheckButton);

		// 테이블 설정
		infoTable.addMouseListener(this);
		infoTable2.addMouseListener(this);
		infoTable.getTableHeader().setReorderingAllowed(false); // 이동 불가
		infoTable2.getTableHeader().setReorderingAllowed(false); // 이동 불가

		refreshButton = new JButton("새로고침");
		refreshButton.setBounds(376, 10, 97, 23);
		refreshButton.addActionListener(this);
		mainPanel.add(refreshButton);

	} // default constructor end

	// 메인 패널 반환
	public JPanel getPanel() {
		return mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// ▼ 버튼을 눌렀을 경우
		if (e.getSource() == returnButton && row >= 0) {

			dtm.removeRow(row);
			dtm2.addRow(value);
			row = -1;

			// ▲ 버튼을 눌렀을 경우
		} else if (e.getSource() == returnCancelButton && row2 >= 0) {

			dtm2.removeRow(row2);
			dtm.addRow(value);
			row2 = -1;

			// 반납확인 버튼을 눌렀을 경우
		} else if (e.getSource() == returnCheckButton) {

			String[] bookReturn = null;

			bookReturn = new String[dtm2.getRowCount()];
			for (int i = 0; i < dtm2.getRowCount(); i++) {
				bookReturn[i] = dtm2.getValueAt(i, 1).toString();

			}

			new TCPClient().returnBook_admin(bookReturn);
			String[] column = { "Id", "Title", "반납신청일" };
			dtm2.setDataVector(null, column);

		} else if (e.getSource() == refreshButton) {
			// DB에서 반납정보 가져오기

			returnInfo = new TCPClient().getReturnInfoAll();

			// 반납 정보 표만들기
			String[] column = { "Id", "Title", "반납신청일" };
			String[][] row = new String[returnInfo.size()][column.length];

			// row값에 데이터 입력
			for (int i = 0; i < row.length; i++) {
				ReturnDTO dto = (ReturnDTO) returnInfo.get(i);
				row[i][0] = dto.getId();
				row[i][1] = dto.getTitle();
				row[i][2] = dto.getApplyday();
			}

			dtm.setDataVector(row, column);
			dtm2.setDataVector(null, column);
		}

	} // actionPerformed end

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == infoTable) {
			value = new String[3];
			infoTable2.clearSelection();
			row = infoTable.getSelectedRow();
			for (int i = 0; i < value.length; i++) {
				value[i] = infoTable.getValueAt(row, i).toString();
			}

		} else {
			value = new String[3];
			infoTable.clearSelection();
			row2 = infoTable2.getSelectedRow();
			for (int i = 0; i < value.length; i++) {
				value[i] = infoTable2.getValueAt(row2, i).toString();
			}

		}

	} // mouseClicked end

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
} // class end
