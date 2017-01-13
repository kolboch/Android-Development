package dev.kb.justjava;

import android.icu.math.BigDecimal;
import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int COFFEE_PRICE = 5;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshQuantityAndPrice();
    }

    /**
     * method called when order button is clicked
     * @param view
     */
    public void submitOrder(View view){
        if(quantity != 0) {
            displayOrderMessage();
            quantity = 0;
            displayQuantity();
        }
    }

    /**
     * refreshes textViews of quantity and Price
     */
    private void refreshQuantityAndPrice(){
        displayQuantity();
        displayPrice(new BigDecimal(quantity * COFFEE_PRICE));
    }
    /**
     * increments quantity ( current quantity + 1 ), called when + button is clicked
     */
    public void incrementQuantity(View view){
        quantity++;
        refreshQuantityAndPrice();
    }

    /**
     * updates quantity textView
     */
    private void displayQuantity(){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * decrements quantity ( current quantity - 1), called when - button is clicked
     */
    public void decrementQuantity(View view){
        if(quantity > 0) {
            quantity--;
            refreshQuantityAndPrice();
        }
    }

    /**
     * displays price
     * @param price
     */
    private void displayPrice(BigDecimal price){
        TextView priceTextView = (TextView)findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
    }

    /**
     * displays given message in textView
     * @param message
     */
    private void displayMessage(String message){
        TextView priceTextView = (TextView)findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    private void displayOrderMessage(){
        int price = quantity * COFFEE_PRICE;
        displayMessage("You ordered: " + quantity + " coffees. \n" +
        "Prepare " + NumberFormat.getCurrencyInstance().format(price) + "\n" + "Thank you!");
    }

}
