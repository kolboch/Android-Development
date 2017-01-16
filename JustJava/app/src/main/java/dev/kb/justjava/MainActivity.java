package dev.kb.justjava;

import android.icu.math.BigDecimal;
import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int COFFEE_PRICE = 5;
    private final int CREAM_PRICE = 1;
    private final int CHOCOLATE_PRICE = 2;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshQuantityAndPrice();
    }

    /**
     * method called when order button is clicked
     *
     * @param view
     */
    public void submitOrder(View view) {
        if (quantity != 0) {
            displayOrderMessage();
            quantity = 0;
            displayQuantity();
        }
    }

    /**
     * refreshes textViews of quantity and Price
     */
    private void refreshQuantityAndPrice() {
        displayQuantity();
        displayPrice(calculatePrice());
    }

    /**
     * increments quantity ( current quantity + 1 ), called when + button is clicked
     */
    public void incrementQuantity(View view) {
        quantity++;
        refreshQuantityAndPrice();
    }

    /**
     * calculates price for coffee with optional toppings chocolate and cream
     *
     * @return
     */
    private BigDecimal calculatePrice() {
        int base = COFFEE_PRICE;
        if (chocolateSelected()) {
            base += CHOCOLATE_PRICE;
        }
        if (creamSelected()) {
            base += CREAM_PRICE;
        }
        return new BigDecimal(quantity * base);
    }

    /**
     * check whether chocolate topping is selected
     *
     * @return true if chocolate is selected, false other case
     */
    private boolean chocolateSelected() {
        return ((CheckBox) findViewById(R.id.chocolateTopping)).isChecked();
    }

    /**
     * check whether cream topping is selected
     *
     * @return true if cream is selected, false other case
     */
    private boolean creamSelected() {
        return ((CheckBox) findViewById(R.id.creamTopping)).isChecked();
    }

    /**
     * updates quantity textView
     */
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * decrements quantity ( current quantity - 1), called when - button is clicked
     */
    public void decrementQuantity(View view) {
        if (quantity > 0) {
            quantity--;
            refreshQuantityAndPrice();
        }
    }

    /**
     * refreshes displayed priced, used by checkbox items to reflect changes to price
     *
     * @param view
     */
    public void refreshPrice(View view) {
        displayPrice(calculatePrice());
    }

    /**
     * displays price
     *
     * @param price
     */
    private void displayPrice(BigDecimal price) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
    }

    /**
     * displays given message in price textView after order is clicked
     *
     * @param message
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    /**
     * @return client name typed in in app
     */
    private String getClientName() {
        EditText t = (EditText) findViewById(R.id.clientName);
        String name = t.getText().toString();
        return name;
    }

    /**
     * displays order message after hitting order button
     */
    private void displayOrderMessage() {
        BigDecimal price = calculatePrice();
        displayMessage("Welcome " + getClientName() + "!\n" +
                "You ordered: " + quantity + " coffees. \n" +
                "With cream: " + creamSelected() + "\n" +
                "With chocolate: " + chocolateSelected() + "\n" +
                "Prepare " + NumberFormat.getCurrencyInstance().format(price) + "\n" +
                "Thank you!");
    }

}
