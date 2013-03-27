package ar.org.plmaker.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import ar.org.plmaker.aux.ProgressMonitor;
import ar.org.plmaker.server.PlMakerServer;
import ar.org.plmaker.utils.ProgressUtil;

public class PlMakerServiceUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 8573650830301342423L;
	private static Logger LOGGER = Logger.getLogger(PlMakerServiceUI.class);

	private static final Insets insets = new Insets(0, 0, 0, 0);

	private static void addComponent(Container container, Component component,
			int gridx, int gridy, int gridwidth, int gridheight, int anchor,
			int fill) {
		addComponent(container, component, gridx, gridy, gridwidth, gridheight,
				anchor, fill, 1.0, 1.0);
	}

	private static void addComponent(Container container, Component component,
			int gridx, int gridy, int gridwidth, int gridheight, int anchor,
			int fill, double weightx, double weighty) {
		final GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,
				gridwidth, gridheight, weightx, weighty, anchor, fill, insets,
				0, 0);
		container.add(component, gbc);
	}

	public static void main(String[] args) {
		new PlMakerServiceUI();
	}

	private boolean started = false;
	private JTextField musicDirText;
	private JTextField portTextField;

	private JButton actionButton;

	private JTextField stateTextField;

	private JButton musicDirButton;

	private final ResourceBundle captions;

	public PlMakerServiceUI() {
		captions = ResourceBundle.getBundle("Messages", Locale.getDefault());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(captions.getString("title"));

		final JPanel mainPanel = new JPanel(new GridBagLayout());
		final Properties data = getProperties();
		addWidgets(mainPanel, data);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.setSize(50, 50);

		setSize(450, 150);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addWidgets(final JPanel mainPanel, Properties data) {
		final JLabel musicDirLabel = new JLabel(
				captions.getString("music_dir"), SwingConstants.LEFT);
		musicDirText = new JTextField();
		musicDirText.setText(getString(data, "music_dir"));
		musicDirText.setEditable(false);
		musicDirButton = new JButton();
		musicDirButton.setText("...");
		musicDirButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final String dir = PlMakerServiceUI.this
						.openDirSelectionDialog(mainPanel,
								musicDirText.getText());
				if (dir != null) {
					musicDirText.setText(dir);
				}
			}
		});

		final JLabel portLabel = new JLabel(captions.getString("service_port"),
				SwingConstants.LEFT);
		portTextField = new JTextField();
		portTextField.setText(getString(data, "service_port"));

		final JLabel addressLabel = new JLabel(
				captions.getString("service_address"), SwingConstants.LEFT);
		final JTextField addressTextField = new JTextField();
		addressTextField.setText(getIp(captions));
		addressTextField.setEditable(false);

		final JLabel stateLabel = new JLabel(
				captions.getString("service_state"), SwingConstants.LEFT);
		stateTextField = new JTextField();
		stateTextField.setText(captions.getString("stopped"));
		stateTextField.setEditable(false);

		final JLabel actionLabel = new JLabel(captions.getString("action"),
				SwingConstants.LEFT);
		actionButton = new JButton();
		actionButton.setText(captions.getString("start"));
		actionButton.addActionListener(new ActionListener() {

			@Override
			// TODO: handle exception
			public void actionPerformed(ActionEvent e) {
				if (PlMakerServiceUI.this.checkInputs(captions)) {
					PlMakerServiceUI.this.switchServiceState();
				}
			}
		});

		addComponent(mainPanel, musicDirLabel, 0, 0, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(mainPanel, musicDirText, 1, 0, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(mainPanel, musicDirButton, 2, 0, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 0.0, 0.0);

		addComponent(mainPanel, addressLabel, 0, 1, 2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(mainPanel, addressTextField, 1, 1, 2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);

		addComponent(mainPanel, portLabel, 0, 2, 2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(mainPanel, portTextField, 1, 2, 2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);

		addComponent(mainPanel, stateLabel, 0, 3, 2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(mainPanel, stateTextField, 1, 3, 2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);

		addComponent(mainPanel, actionLabel, 0, 4, 2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(mainPanel, actionButton, 1, 4, 2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH);
	}

	protected boolean checkInputs(ResourceBundle captions) {
		final String port = portTextField.getText();
		if (port.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					captions.getString("port_cannot_be_null"));
			return false;
		}
		if (!checkNumeric(port)) {
			JOptionPane.showMessageDialog(null,
					captions.getString("port_must_be_numeric"));
			return false;
		}
		return true;
	}

	private boolean checkNumeric(String port) {
		try {
			Integer.parseInt(port);
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	private void createDefaultProperties(File dataFile) throws IOException {
		dataFile.createNewFile();
		final Properties properties = new Properties();
		properties.put("music_dir", System.getProperty("user.home"));
		properties.put("service_port", "80");
		properties.store(new FileOutputStream(dataFile), null);
	}

	private String getIp(ResourceBundle captions) {
		try {
			final byte[] ipAddr = InetAddress.getLocalHost().getAddress();
			String ipAddrStr = "";
			for (int i = 0; i < ipAddr.length; i++) {
				if (i > 0) {
					ipAddrStr += ".";
				}
				ipAddrStr += ipAddr[i] & 0xFF;
			}
			return ipAddrStr;
		} catch (final Exception e) {
			return captions.getString("cannot_resolve_address");
		}
	}

	private Properties getProperties() {
		try {
			final File dataFile = new File("plmaker.properties");
			if (!dataFile.exists()) {
				createDefaultProperties(dataFile);
			}
			final Properties properties = new Properties();
			properties.load(new FileInputStream(dataFile));
			return properties;
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private String getString(Properties data, String key) {
		final String value = data.getProperty(key);
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}

	protected String openDirSelectionDialog(Component parent, String current) {
		final JFileChooser chooser = new JFileChooser(current);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		final int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getPath();
		} else {
			return null;
		}
	}

	@Override
	public void run() {
		try {
			final ProgressMonitor monitor = ProgressUtil
					.createModalProgressMonitor(this, false, captions);
			PlMakerServer.getInstance().start(
					Integer.valueOf(portTextField.getText()),
					musicDirText.getText(), monitor);
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(null,
					captions.getString("error_msg"),
					captions.getString("error"), JOptionPane.ERROR_MESSAGE);
			LOGGER.error(e);
			System.exit(1);
		}
	}

	private void saveCurrentProperties() {
		try {
			final File dataFile = new File("plmaker.properties");
			final Properties properties = new Properties();
			properties.put("music_dir", musicDirText.getText());
			properties.put("service_port", portTextField.getText());
			properties.store(new FileOutputStream(dataFile), null);
		} catch (final Exception e) {
			LOGGER.error("Error while saving the preferences", e);
		}
	}

	protected void switchServiceState() {
		if (started) {
			// Stop the service
			portTextField.setEditable(true);
			musicDirButton.setEnabled(true);
			actionButton.setText(captions.getString("start"));
			stateTextField.setText(captions.getString("stopped"));
			PlMakerServer.getInstance().stop();
			started = false;
		} else {
			// Start the service
			portTextField.setEditable(false);
			musicDirButton.setEnabled(false);
			saveCurrentProperties();
			new Thread(this).start();
			actionButton.setText(captions.getString("stop"));
			stateTextField.setText(captions.getString("running"));
			started = true;
		}
	}

}
