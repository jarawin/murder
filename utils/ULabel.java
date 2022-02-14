package utils;

import javax.swing.JLabel;

public class ULabel extends JLabel {
	private static final long serialVersionUID = 1L;

	public ULabel(String title, int size, int x, int y, int w, int h) {
		super(title);
		setFont(new UFont(size).get());
		setBounds(x, y, w, h);
	}
}
