package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 0;
    private CheckBox checkBoxWhipped;
    private CheckBox checkBoxChocolate;
    private EditText nameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxWhipped = (CheckBox) findViewById(R.id.checkbox_whipped_cream_id);
        checkBoxChocolate = (CheckBox) findViewById(R.id.checkbox_chocolate_id);
        nameText = (EditText) findViewById(R.id.name_edit_view);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean whipped = checkBoxWhipped.isChecked();
        boolean chocolate = checkBoxChocolate.isChecked();
        int price = calculatePrice(quantity, whipped, chocolate);
        String name = nameText.getText().toString();
        String message = createOrderSummary(price, name, whipped, chocolate);
//
//        displayMessage(message);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        String subject = getString(R.string.app_name) + getString(R.string.order);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity, boolean whipped, boolean chocolate) {
        int price = 5;
        price = whipped ? price+1 : price;
        price = chocolate ? price+2 : price;
        return quantity * price;
    }

    private String createOrderSummary(int price, String name, boolean whippedCream, boolean chocolate){

        String message = getString(R.string.order_summary_name, name)
                + "\n" + getString(R.string.order_summary_quantity, quantity);

        if (whippedCream) {
            message += "\n" + getString(R.string.add_topping, getString(R.string.whipped_cream));
        }
        if (chocolate) {
            message += "\n" + getString(R.string.add_topping, getString(R.string.chocolate));
        }

        return  message
                + "\n" + getString(R.string.price, NumberFormat.getCurrencyInstance().format(price))
                + "\n" + getString(R.string.thank_you);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        quantity++;
        if(quantity > 100 ){
            quantity = 100;
            //should be in resource and translated
            Toast.makeText(this, "Too many coffes!", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void decrement(View view) {
        quantity--;
        if(quantity < 0 ){
            quantity = 0;
            //should be in resource and translated
            Toast.makeText(this, "Too little coffes!", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}