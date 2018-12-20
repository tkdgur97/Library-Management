package admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.text.DecimalFormat;

import java.util.TreeMap;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import tcpserver.TCPClient;

import org.jfree.chart.plot.PiePlot3D;

/*
 * 도서 분류 차트
 * 도서 기호를 통해 종류별로 나눈 것을 차트로 변환
 */

public class BookChart_admin {
	private ChartPanel chartPanel;

	public BookChart_admin() {

		PieDataset dataset = createData(); // 데이터셋 객체 정의
		JFreeChart chart = createChart(dataset); // 데이터셋을 참조하는 차트객체만들기
		chartPanel = new ChartPanel(chart); // 패널
		chartPanel.setPreferredSize(new Dimension(500, 400)); // 크기
		chartPanel.setLayout(null);
		chartPanel.validate();
		chartPanel.setBackground(Color.LIGHT_GRAY);

	}// default constructor end

	private PieDataset createData() {
		TreeMap<String, Integer> b = new TCPClient().getTypeOfBook();
		String[] kind = { "사회과학", "기술과학", "역사", "예술", "철학", "총류", "자연과학", "언어", "기타", "종교", "문학" }; // 차트에 들어갈 종류
		DefaultPieDataset date = new DefaultPieDataset();
		for (int i = 0; i < kind.length; i++) { // 차트에 들어간 종류에 대한 값 설정
			date.setValue(kind[i], b.get(kind[i]));
		}

		return date;
	} // PieDataset end

	private JFreeChart createChart(PieDataset dataset) { // 위의 파이 데이터셋 메서드에서 가져오기

		JFreeChart chart = ChartFactory.createPieChart3D( // 3D차트 만들기
				"", // 제목
				dataset, // 데이터셋 연동으로 처리
				true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: ({2})", new DecimalFormat("0"),
				new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);
		chart.getTitle().setFont(new Font("굴림", Font.BOLD, 15)); // 폰트 지정
		chart.getLegend().setItemFont(new Font("돋움", Font.PLAIN, 10));
		plot.setLabelFont(new Font("굴림", Font.BOLD, 15));
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);

		return chart;
	} // JFreeChart end

	public JPanel getChart() {
		return chartPanel;
	}

}
