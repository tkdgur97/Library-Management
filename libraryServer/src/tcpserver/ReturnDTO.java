package tcpserver;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReturnDTO implements Serializable {
	private String id;
	private String title;
	private String applyday;

	public ReturnDTO(String id, String title, String applyday) {
		super();
		this.id = id;
		this.title = title;
		this.applyday = applyday;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getApplyday() {
		return applyday;
	}

	public void setApplyday(String applyday) {
		this.applyday = applyday;
	}

}
