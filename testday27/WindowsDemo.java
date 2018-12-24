package testday27;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class WindowsDemo {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel label_2;
	private JTextField textField_3;
	private JLabel label_3;
	private JTextField textField_4;
	private JLabel lblyyyyddmmHhmmss_1;
	private JTextField textField_6;
	private JTextField textField_5;
	private JTextField textField_7;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel lblyyyyddmmHhmmss;
	private JButton button;
	public static JTextArea textArea;
	private JTextField textField_8;
	private JTextField textField_9;
	static WindowsDemo window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new WindowsDemo();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
					window.click();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


		});
	}

	/**
	 * Create the application.
	 */
	public WindowsDemo() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u533A\u95F4\u8F66\u901F\u8BA1\u7B97");
		frame.setBounds(100, 100, 756, 588);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 10, 78, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(112, 10, 86, 29);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel label = new JLabel("\u6570\u636E\u5E93\u540D\u79F0");
		label.setBounds(10, 42, 66, 21);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel(" \u7528\u6237\u540D");
		label_1.setBounds(122, 42, 54, 21);
		frame.getContentPane().add(label_1);

		textField_2 = new JTextField();
		textField_2.setBounds(224, 10, 78, 29);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		label_2 = new JLabel("  \u5BC6\u7801");
		label_2.setBounds(234, 42, 54, 21);
		frame.getContentPane().add(label_2);

		textField_3 = new JTextField();
		textField_3.setBounds(333, 10, 92, 29);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		label_3 = new JLabel("  \u8868\u540D");
		label_3.setBounds(351, 42, 54, 21);
		frame.getContentPane().add(label_3);

		textField_4 = new JTextField();
		textField_4.setBounds(10, 73, 292, 29);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);

		lblyyyyddmmHhmmss_1 = new JLabel(
				"\u521D\u59CB\u65F6\u95F4 \u683C\u5F0F\u4E3A\uFF1A\"yyyy-dd-MM hh:mm:ss SSS\"");
		lblyyyyddmmHhmmss_1.setBounds(10, 112, 292, 21);
		frame.getContentPane().add(lblyyyyddmmHhmmss_1);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(333, 73, 278, 29);
		frame.getContentPane().add(textField_6);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(451, 10, 127, 29);
		frame.getContentPane().add(textField_5);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(604, 10, 127, 29);
		frame.getContentPane().add(textField_7);

		label_6 = new JLabel("\u5361\u53E3\u7F16\u53F71");
		label_6.setBounds(481, 45, 66, 15);
		frame.getContentPane().add(label_6);

		label_7 = new JLabel("\u5361\u53E3\u7F16\u53F72");
		label_7.setBounds(636, 45, 78, 15);
		frame.getContentPane().add(label_7);

		lblyyyyddmmHhmmss = new JLabel("\u7ED3\u675F\u65F6\u95F4 \u683C\u5F0F\u4E3A\uFF1A\"yyyy-dd-MM hh:mm:ss SSS\"");
		lblyyyyddmmHhmmss.setBounds(333, 112, 278, 21);
		frame.getContentPane().add(lblyyyyddmmHhmmss);

		button = new JButton("\u8BA1\u7B97");
		button.setBackground(new Color(0, 255, 0));
		button.setBounds(636, 89, 78, 70);
		frame.getContentPane().add(button);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 223, 694, 296);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		textField_8 = new JTextField();
		textField_8.setBounds(17, 154, 66, 28);
		frame.getContentPane().add(textField_8);
		textField_8.setColumns(10);
		
		JLabel label_4 = new JLabel("\u5361\u53E3\u8DDD\u79BB");
		label_4.setBounds(23, 191, 65, 21);
		frame.getContentPane().add(label_4);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(112, 154, 66, 28);
		frame.getContentPane().add(textField_9);
		
		JLabel label_5 = new JLabel("\u4E0B\u6E38\u65B9\u5411");
		label_5.setBounds(111, 191, 65, 21);
		frame.getContentPane().add(label_5);

	
	}
	
	public void click() {
		window.button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				textArea.setText("");
				window.getValue();
				new jisuan().run();
			
			}
		});
	}

	private void getValue() {

		jisuan.dbname = textField.getText();
		jisuan.dbuser = textField_1.getText();
		jisuan.dbpassword = textField_2.getText();
		jisuan.sheet = textField_3.getText();
		jisuan.kakou1 = textField_5.getText();
		jisuan.kakou2 = textField_7.getText();
		jisuan.time1 = textField_4.getText();
		jisuan.time2 = textField_6.getText();
		jisuan.distance = Integer.parseInt(textField_8.getText());
		jisuan.downfx = Integer.parseInt(textField_9.getText());
		
	}
}
