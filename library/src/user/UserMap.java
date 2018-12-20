package user;

import java.awt.Point;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/*
 * 지도 띄우기
 */

public class UserMap {

	private JPanel mainPanel;

	public UserMap() {
		initFX();
	}

	// 브라우저를 띄우기위한 설정
	public void initAndLoadWebView(JFXPanel fxPanel) {
		Group group = new Group();
		Scene scene = new Scene(group);
		fxPanel.setScene(scene);
		WebView webView = new WebView();
		group.getChildren().add(webView);
		webView.setMinSize(638, 681);
		webView.setMaxSize(638, 681);
		WebEngine webEngine = webView.getEngine();
		webEngine.load("http://map.naver.com/");
		
	} // initAndLoadWebView end

	public void initFX() {
		// 메인 패널 설정
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 638, 681);

		// 웹 브라우저를 띄울 패널 설정
		JFXPanel fxPanel = new JFXPanel();
		fxPanel.setBounds(0, 0, 638, 681);
		fxPanel.setLocation(new Point(0, 0));
		mainPanel.add(fxPanel);
		Platform.runLater(new Runnable() {
			public void run() {
				initAndLoadWebView(fxPanel);
			}
		});

	} // initFX end

	public JPanel getPanel() {
		return mainPanel;
	}

} // class end
