package conversion_tools;

import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public final class ForeignExchange {

  private static final String LATEST_ENDPOINT = "https://openexchangerates.org/api/latest.json";
  private static final String CURRENCIES_ENDPOINT = "https://openexchangerates.org/api/currencies.json";
  private static final String APP_ID = "7027370c386349c2a2d9b5668d34499c";
  
  private static final Map<String, String> CURRENCIES;
  private static final Map<String, Double> USD_EXCHANGE_RATES;
  private static final List<String> CURRENCY_NAMES;

  static {
    CURRENCIES = fetchCurrencies();
    USD_EXCHANGE_RATES = null;//fetchUSDExchangeRates();
    CURRENCY_NAMES = extractCurrencyNames();
  }

  private static List<String> extractCurrencyNames() {
    return CURRENCIES.keySet().stream().sorted().collect(
      Collectors.toList()
    );
  }

  private static InputStream fetchData(String request) {
    InputStream rawData = null;

    try {
      URL url = new URL(request);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("GET");

      if(connection.getResponseCode() != 200) throw new IOException("Conexión fallida");

      rawData = connection.getInputStream();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return rawData;
  }

  private static Map<String, String> fetchCurrencies() {
    Map<String, String> currencies = null;

    InputStream rawData = fetchData(CURRENCIES_ENDPOINT);
    ObjectMapper json = new ObjectMapper();

    try {
      currencies = json.readValue(rawData, new TypeReference<Map<String, String>>() {});
      currencies = currencies.entrySet().stream().collect(
        Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)
      );
    } catch(Exception e) {
      e.printStackTrace();
    }

    return currencies;
  }

  private static Map<String, Double> fetchUSDExchangeRates() {
    Map<String, Double> usdExchangeRates = null;

    String request = LATEST_ENDPOINT + "?app_id=" + APP_ID;
    InputStream rawData = fetchData(request);
    ObjectMapper json = new ObjectMapper();

    try {
      usdExchangeRates = json.readValue(rawData, USDExchangeRates.class).getValues();
    } catch(Exception e) {
      e.printStackTrace();
    }

    return usdExchangeRates;
  }

  private static double getUSDExchangeRate(String currencyName) {
    String currencyCode = CURRENCIES.get(currencyName);
    return USD_EXCHANGE_RATES.get(currencyCode);
  }

  public static List<String> getCurrencyNames() {
    return CURRENCY_NAMES;
  }

  public static double getExchangeRate(String currencyName1, String currencyName2) {
    double usdExchangeRate1 = getUSDExchangeRate(currencyName1);
    double usdExchangeRate2 = getUSDExchangeRate(currencyName2);

    return usdExchangeRate2 / usdExchangeRate1;
  }

}
