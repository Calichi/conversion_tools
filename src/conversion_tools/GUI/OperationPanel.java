package conversion_tools.GUI;

import javax.swing.JSplitPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import conversion_tools.ForeignExchange;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class OperationPanel extends JSplitPane {

  private ComponentAdapter centerDivisor = new ComponentAdapter() {
		
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

  private NameList leftList = new NameList("United States Dollar");
  private NameList rightList = new NameList("Mexican Peso");
  private AmountInput input = new AmountInput();
  private JLabel output = new JLabel("Salida");

  public OperationPanel() {

    this.leftList.setFont(Window.TEXT_FONT);
    this.rightList.setFont(Window.TEXT_FONT);
    this.output.setFont(Window.NUMBER_FONT);
    this.input.setFont(Window.NUMBER_FONT);

    this.initializeOutput();
    this.addComponentListener(this.centerDivisor);
    this.setBorder(null);
		this.setDividerSize(1);
		this.setEnabled(false);
    this.setLeftComponent(this.generateLeftPanel());
    this.setRightComponent(this.generateRightPanel());
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		//this.setBackground(new Color(0,0,0,0));
  }

  private JPanel generateSidePanel(JComponent list, JComponent peripheral) {
    JPanel container = new JPanel();
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.WEST, list, 10, SpringLayout.WEST, container);
		layout.putConstraint(SpringLayout.NORTH, list, 10, SpringLayout.NORTH, container);
		layout.putConstraint(SpringLayout.EAST, list, -10, SpringLayout.EAST, container);
		
		layout.putConstraint(SpringLayout.WEST, peripheral, 10, SpringLayout.WEST, container);
		layout.putConstraint(SpringLayout.NORTH, peripheral, 10, SpringLayout.SOUTH, list);
		layout.putConstraint(SpringLayout.EAST, peripheral, -10, SpringLayout.EAST, container);
		layout.putConstraint(SpringLayout.SOUTH, peripheral, -10, SpringLayout.SOUTH, container);

    container.setBackground(new Color(215, 235, 255));

    container.add(list);
    container.add(peripheral);
    container.setLayout(layout);
    return container;
	}

  private JPanel generateLeftPanel() {
    return this.generateSidePanel(this.leftList, this.input);
  }

  private JPanel generateRightPanel() {
    return this.generateSidePanel(this.rightList, this.output);
  }

  private void initializeOutput() {
		this.output.setHorizontalAlignment(SwingConstants.CENTER);
		this.output.setForeground(new Color(45,45,45));
	}

  public void executeOperation() {
    double amount = this.input.getAmount();
		String currencyName1 = this.leftList.getCurrencyName();
		String currencyName2 = this.rightList.getCurrencyName();
		double exchangeRate = ForeignExchange.getExchangeRate(currencyName1, currencyName2);
		double value = exchangeRate * amount;
		this.output.setText(String.format("%.2f", value));
  }
}
