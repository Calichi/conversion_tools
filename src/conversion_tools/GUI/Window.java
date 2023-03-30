package conversion_tools.GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import conversion_tools.ForeignExchange;

public class Window {

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
	
	private ComponentAdapter splitPaneCenterDivisor = new ComponentAdapter() {
		
		private void centerDivisor(ComponentEvent e) {
			JSplitPane split = (JSplitPane)e.getComponent();
			int location = split.getWidth() / 2;
			split.setDividerLocation(location);
		}
		
		@Override
		public void componentShown(ComponentEvent e) {
			centerDivisor(e);
		}
		
		@Override
		public void componentResized(ComponentEvent e) {
			centerDivisor(e);			
		}
		
	};

	//Validar que en la lista exista un nombre de moneda valido
	//Validar que el número de entrada sea mayor a 0;
	
	private JFrame frame = new JFrame();
	private JPanel mainContainer = new JPanel();
	
	private JComboBox<String> conversorType = new JComboBox<>();
	private JSplitPane systemContainer = new JSplitPane();
	
	private JPanel systemLeftPanel = new JPanel();
	private NameList leftList = new NameList("United States Dollar");
	private AmountInput input = new AmountInput();
	
	private JPanel systemRightPanel = new JPanel();
	private NameList rightList = new NameList("Mexican Peso");
	private JLabel output = new JLabel("Salida");
	
	private JButton convertionButton = new JButton("Convertir");
	
	private Font globalFont16 = new Font("Helvetica", Font.PLAIN, 16);
	private Font globalFont48 = new Font("Courier New", Font.PLAIN, 48);
	
	public Window() {

		this.conversorType.setFont(globalFont16);
		this.leftList.setFont(globalFont16);
		this.rightList.setFont(globalFont16);
		this.input.setFont(globalFont48);
		this.output.setFont(globalFont48);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.initializeFrame();
		this.initializeMainContainer();
		this.initializeConversorType();
		this.initializeSystemContainer();
		
		this.initializeSystemLeftPanel();
		
		this.initializeSystemRightPanel();
		this.initializeOutput();
		
		this.initializeConvertionButton();
		
		
		this.systemLeftPanel.add(this.leftList);
		this.systemLeftPanel.add(this.input);
		
		this.systemRightPanel.add(this.rightList);
		this.systemRightPanel.add(this.output);
		
		systemContainer.setLeftComponent(this.systemLeftPanel);
		systemContainer.setRightComponent(this.systemRightPanel);
		
		this.mainContainer.add(this.conversorType);
		this.mainContainer.add(Box.createVerticalStrut(10));
		this.mainContainer.add(systemContainer);
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
	
	private void initializeSystemContainer() {
		systemContainer.setBorder(null);
		systemContainer.setDividerSize(1);
		systemContainer.setEnabled(false);
		systemContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
		systemContainer.setBackground(new Color(0,0,0,0));
		systemContainer.addComponentListener(this.splitPaneCenterDivisor);
	}
	
	private void setSystemPanelLayout(JPanel container, JComboBox<?> list, JComponent component) {
		SpringLayout layout = (SpringLayout)container.getLayout();
		
		layout.putConstraint(SpringLayout.WEST, list, 10, SpringLayout.WEST, container);
		layout.putConstraint(SpringLayout.NORTH, list, 10, SpringLayout.NORTH, container);
		layout.putConstraint(SpringLayout.EAST, list, -10, SpringLayout.EAST, container);
		
		layout.putConstraint(SpringLayout.WEST, component, 10, SpringLayout.WEST, container);
		layout.putConstraint(SpringLayout.NORTH, component, 10, SpringLayout.SOUTH, list);
		layout.putConstraint(SpringLayout.EAST, component, -10, SpringLayout.EAST, container);
		layout.putConstraint(SpringLayout.SOUTH, component, -10, SpringLayout.SOUTH, container);
	}
	
	private void initializeSystemPanel(JPanel panel, JComboBox<?> list, JComponent component) {
		panel.setLayout(new SpringLayout());
		panel.setBackground(new Color(215, 235, 255));
		this.setSystemPanelLayout(panel, list, component);
	}
	
	private void initializeSystemLeftPanel() {
		this.initializeSystemPanel(this.systemLeftPanel, this.leftList, this.input);
	}
	
	private void initializeSystemRightPanel() {
		this.initializeSystemPanel(this.systemRightPanel, this.rightList, this.output);
	}
	
	private void initializeOutput() {
		this.output.setHorizontalAlignment(SwingConstants.CENTER);
		this.output.setForeground(new Color(45,45,45));
	}
	
	private void initializeConvertionButton() {
		this.convertionButton.setFont(globalFont16);
		this.convertionButton.addActionListener(this::bttClick);
		this.convertionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private void bttClick(ActionEvent e) {
		double amount = this.input.getAmount();
		String currencyName1 = this.leftList.getCurrencyName();
		String currencyName2 = this.rightList.getCurrencyName();
		double exchangeRate = ForeignExchange.getExchangeRate(currencyName1, currencyName2);
		double value = exchangeRate * amount;
		this.output.setText(String.format("%.2f", value));
	}

}
