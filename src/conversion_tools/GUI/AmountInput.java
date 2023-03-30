package conversion_tools.GUI;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

public class AmountInput extends JFormattedTextField {

  public AmountInput() {
    super();

    new NumberFormatter() {
      {
        DecimalFormat format = new DecimalFormat();

        format.setGroupingUsed(true);
        format.setGroupingSize(3);

        this.setFormat(format);
        this.setValueClass(Double.class);
        this.setMaximum(1000000d);
        this.setMinimum(0d);
        this.setAllowsInvalid(false);

        this.install(AmountInput.this);
      }

      @Override
      public Object stringToValue(String s) throws ParseException {
        try {
          if("0".equals(AmountInput.this.getText())) s = s.replaceAll("0","");
          return super.stringToValue(s);
        } catch(ParseException e) {
          if(s == null || s.isBlank()) return 0;
          else throw e;
        }
      }
    };

    this.setHorizontalAlignment(CENTER);

  }

	private void updateAmountDisplayed(double amount) {
		this.setText(String.format("%s", amount));
	}

  private double validateAmount(double amount) {
		if(amount == 0) {
			amount = 1;
			updateAmountDisplayed(amount);
		}

		return amount;
	}

  public double getAmount() {
		double amount = Double.parseDouble(this.getText().replaceAll(",",""));
		return validateAmount(amount);
	}
  
}
