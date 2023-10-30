package com.example.admin_app;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.admin_app.Models.Extra;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExtraPopupWindow extends PopupWindow {
    private EditText edtTitle;
    private EditText edtPrice;
    private Button btnSave;

    public AddExtraPopupWindow(Context context, String productId) {
        super(context);

        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_up_add_extra, null);
        setContentView(contentView);

        edtTitle = contentView.findViewById(R.id.edtExtraTitle);
        edtPrice = contentView.findViewById(R.id.edtExtraPrice);
        btnSave = contentView.findViewById(R.id.btnSave);

        edtTitle.requestFocus();

        // Ensure the popup window can accept input
        setFocusable(true);
        setOutsideTouchable(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input from the EditText fields
                String title = edtTitle.getText().toString();
                double price = Double.parseDouble(edtPrice.getText().toString());

                // Create an Extra object with the input data
                Extra extra = new Extra();
                extra.setTitle(title);
                extra.setPrice(price);


                // Push the extra to Firebase
                DatabaseReference extrasRef = FirebaseDatabase.getInstance().getReference().child("products").child(productId).child("productExtras");
                String extraId = extrasRef.push().getKey();
                assert extraId != null;
                extrasRef.child(extraId).setValue(extra);

                // Dismiss the popup window
                dismiss();
            }
        });

        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
    }
}
