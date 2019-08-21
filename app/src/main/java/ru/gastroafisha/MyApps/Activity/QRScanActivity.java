package ru.gastroafisha.MyApps.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

//import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

//import info.androidhive.barcode.BarcodeReader;
import ru.gastroafisha.MyApps.R;

public class QRScanActivity extends CommonActivity /* implements BarcodeReader.BarcodeReaderListener*/ {

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_qrscan;
    }
/*
    @Override
    public void onScanned(Barcode barcode) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", barcode.rawValue);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void onCameraPermissionDenied() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }*/
}
