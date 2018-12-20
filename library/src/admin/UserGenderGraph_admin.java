package admin;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Font;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import tcpserver.MemberDTO;
import tcpserver.TCPClient;

/*
 * 회원 성별을 분류하여 그래프로 변환
 */

public class UserGenderGraph_admin {
	ChartPanel chartPanel;

	public UserGenderGraph_admin() {
		ArrayList<MemberDTO> member = new TCPClient().getUserInfoAll();
		int[] count = new int[2];
		for (int i = 0; i < member.size(); i++) {
			if (member.get(i).getRrn().charAt(6) == '1') {
				count[0]++;
			} else {
				count[1]++;
			}
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(count[0], "사람 수", "남");
		dataset.addValue(count[1], "사람 수", "녀");

		JFreeChart chart = ChartFactory.createBarChart("", "성별", "명 수", dataset, PlotOrientation.VERTICAL, true, true,
				false);
		chart.setBackgroundPaint(Color.WHITE);

		// 2. 그래프 전체의 경계선 설정
		chart.setBorderVisible(true); // 차트전체의 경계선이 나타난다.
		chart.setBorderPaint(Color.WHITE); // 차트전체의 경계선의 색을 파란색으로 정한다.
		chart.setBorderStroke(new BasicStroke(5)); // 차트전체의 경계선의 두께를 정한다.

		// 제목
		chart.getTitle().setFont(new Font("돋움", Font.BOLD, 15));
		// 범례
		chart.getLegend().setItemFont(new Font("돋움", Font.PLAIN, 10));

		CategoryPlot plot = chart.getCategoryPlot();

		Font font = plot.getDomainAxis().getLabelFont();
		// X축 라벨
		plot.getDomainAxis().setLabelFont(new Font("돋움", font.getStyle(), font.getSize()));
		// X축 도메인
		plot.getDomainAxis().setTickLabelFont(new Font("굴림", font.getStyle(), 15));

		font = plot.getRangeAxis().getLabelFont();
		// Y축 라벨
		plot.getRangeAxis().setLabelFont(new Font("돋움", font.getStyle(), font.getSize()));
		// Y축 범위
		plot.getRangeAxis().setTickLabelFont(new Font("돋움", font.getStyle(), 10));
		chartPanel = new ChartPanel(chart);
		chartPanel.setLocation(2, 5);
		chartPanel.setSize(259, 250);

	} // default constructor end

	public ChartPanel getPanel() {
		return chartPanel;
	}

} // class end