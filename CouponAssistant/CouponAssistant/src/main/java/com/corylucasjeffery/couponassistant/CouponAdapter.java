package com.corylucasjeffery.couponassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CouponAdapter extends ArrayAdapter<Coupon> {
    private ArrayList<Coupon> objects;
    private Context context;

    public CouponAdapter(Context context, int resource, ArrayList<Coupon> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_coupon, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Coupon coupon = objects.get(position);

        if (coupon != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.
            TextView discount = (TextView) v.findViewById(R.id.list_coupon_disc_amt);
            TextView limitations = (TextView) v.findViewById(R.id.list_coupon_limitations);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (discount != null){
                discount.setText(coupon.getDisc());
            }
            if (limitations != null){
                limitations.setText(coupon.getLimits());
            }
        }

        // the view must be returned to our activity
        return v;
    }
}
