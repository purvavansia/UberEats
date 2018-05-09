package com.example.purva.ubereats.ui.address;

import android.content.Context;
import android.content.Intent;

import com.example.purva.ubereats.ui.location.MapsActivity;

public class AddressPresenter implements IAddressPresenter {

    Context context;

    public AddressPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onClickEnter() {
        Intent intent = new Intent(context, MapsActivity.class);
        context.startActivity(intent);
    }


}
