package conversion_tools.GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JComboBox;
import javax.swing.SpringLayout;

public final class Window {

	protected static final Font NUMBER_FONT = new Font("Courier New", Font.BOLD, 36);
	protected static final Font TEXT_FONT = new Font("Helvetica", Font.BOLD, 16);

	private static final List<String> CONVERSOR_TYPES = new ArrayList<String>();
	
	static {
		CONVERSOR_TYPES.add("Conversor de monedas");
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	//Validar que en la lista exista un nombre de moneda valido
	//Validar que el número de entrada sea mayor a 0;
	
	private JFrame frame = new JFrame();
	private JPanel mainContainer = new JPanel();
	
	private JComboBox<String> conversorType = new JComboBox<>();
	private OperationPanel systemContainer = new OperationPanel();
	
	private JButton convertionButton = new JButton("Convertir");
	
	public Window() {

		this.conversorType.setFont(TEXT_FONT);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.initializeFrame();
		this.initializeMainContainer();
		this.initializeConversorType();
		
		this.initializeConvertionButton();
		
		this.mainContainer.add(this.conversorType);
		this.mainContainer.add(Box.createVerticalStrut(10));
		this.mainContainer.add(this.systemContainer);
		this.mainContainer.add(Box.createVerticalStrut(10));
		this.mainContainer.add(this.convertionButton);
		
	}
	
	private void initializeFrame() {
		Container container = frame.getContentPane();
		
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		frame.setBounds(100, 100, (int)(height*1.1), (int)(height * 0.6));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setUndecorated(true);
		//frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		frame.setBackground(new Color(190,215,240));
		
		container.setLayout(new SpringLayout());
		container.setBackground(new Color(0,0,0,0));
	}
	
	private void initializeMainContainer() {
		BoxLayout layout = new BoxLayout(mainContainer, BoxLayout.Y_AXIS);
		Container container = frame.getContentPane();
		SpringLayout frameLayout = (SpringLayout)container.getLayout();
		mainContainer.setBackground(new Color(0,0,0,0));
		
		frameLayout.putConstraint(SpringLayout.WEST, mainContainer, 10, SpringLayout.WEST, container);
		frameLayout.putConstraint(SpringLayout.NORTH, mainContainer, 10, SpringLayout.NORTH, container);
		frameLayout.putConstraint(SpringLayout.EAST, mainContainer, -10, SpringLayout.EAST, container);
		frameLayout.putConstraint(SpringLayout.SOUTH, mainContainer, -10, SpringLayout.SOUTH, container);
		mainContainer.setLayout(layout);
		container.add(mainContainer);
	}
	
	private void initializeConversorType() {
		conversorType.setModel(new DefaultComboBoxModel<String>(CONVERSOR_TYPES.toArray(new String[0])));
	}

	private void initializeConvertionButton() {
		this.convertionButton.setFont(TEXT_FONT);
		this.convertionButton.addActionListener(this::bttClick);
		this.convertionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private void bttClick(ActionEvent e) {
		this.systemContainer.executeOperation();
	}

}