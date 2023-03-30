package conversion_tools;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class USDExchangeRates {
	@JsonProperty("rates")
	private Map<String, Double> values;

	public Map<String, Double> getValues() {
		return values;
	}

	public void setValues(Map<String, Double> value) {
		this.values = value;
	}
}
