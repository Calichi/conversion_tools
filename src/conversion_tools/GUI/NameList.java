package conversion_tools.GUI;

import java.util.List;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import conversion_tools.ForeignExchange;

public class NameList extends JComboBox<String> {

  private static final List<String> DATA = ForeignExchange.getCurrencyNames();

  private final String defaultCurrencyName;

  public NameList(String defaultCurrencyName) {
    super();
    this.defaultCurrencyName = defaultCurrencyName;
    this.setDefaultModel();
    this.setEditable(true);
    this.setSelectedItem(this.defaultCurrencyName);

    this.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
		
      @Override
      public void keyTyped(KeyEvent e) {
        NameList list = NameList.this;
        JTextField input = (JTextField)e.getComponent();
        String text = input.getText();
        String matchText = text.toLowerCase();
  
        String[] suggestions = text.isBlank()
          ? DATA.toArray(String[]::new)
          : DATA.stream()
                .filter(currencyName -> currencyName.toLowerCase().contains(matchText))
                .toArray(String[]::new);
        
        list.setModel(suggestions);
        //list.setSelectedIndex(-1);
        list.showPopup();
        input.setText(text);
      }
      
    });
  }

  private void setModel(String[] items) {
    this.setModel(new DefaultComboBoxModel<String>(items));
  }

  private void setDefaultModel() {
    this.setModel(DATA.toArray(String[]::new));
  }

  public String getCurrencyName() throws IllegalArgumentException {
    if(!DATA.contains(this.getEditor().getItem())) {
      this.setSelectedItem(this.defaultCurrencyName);
      throw new IllegalArgumentException("Verifique ");
    }

    return (String)this.getSelectedItem();
  }
}
