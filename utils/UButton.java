package utils;

import java.awt.Color;
import javax.swing.JButton;

public class UButton extends JButton {

	public UButton(String title, int size, int x, int y, int width, int height) {
		super(title);
		this.setBackground(new Color(2, 117, 216));
		this.setForeground(Color.white);
		this.setFont(new UFont(size).get());
		this.setBounds(x, y, width, height);
	}

}
