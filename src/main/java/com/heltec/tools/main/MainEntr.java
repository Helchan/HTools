package com.heltec.tools.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class MainEntr extends JFrame {

	private JPanel mainPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainEntr frame = new MainEntr();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainEntr() {
		//窗体标题
		setTitle("HTools---Helchan作品");
		//应用随窗体关闭而关闭
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 911, 567);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setLayout(new BorderLayout(0, 0));
		//窗体居中
		this.setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem setMenuItem = new JMenuItem("设置");
		setMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(setMenuItem);
		
		JMenuItem helpMenuItem = new JMenuItem("帮助");
		helpMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(helpMenuItem);
		setContentPane(mainPane);
	}

}
