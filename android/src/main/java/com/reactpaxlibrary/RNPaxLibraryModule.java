
package com.reactpaxlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.pax.dal.ICashDrawer;
import com.pax.dal.IDAL;
import com.pax.dal.IPrinter;
import com.pax.neptunelite.api.NeptuneLiteUser;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class RNPaxLibraryModule extends ReactContextBaseJavaModule {

    private static final String NAME = "Pax";
    private final ReactApplicationContext reactContext;

    private IDAL dal;
    private IPrinter printer;
    private ICashDrawer cashDrawer;
    private static QRCodeUtil qrcodeUtility;


    public RNPaxLibraryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        try {
            dal = NeptuneLiteUser.getInstance().getDal(reactContext);
            printer = dal.getPrinter();
            cashDrawer = dal.getCashDrawer();
            qrcodeUtility = new QRCodeUtil();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void printStr(String text, Double cutMode) {
        try {
            printer.setGray(3);
            printer.printStr(text, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void printText(String text, int grayLevel, boolean large, int lineSpace, boolean invert) {
        try {
            printer.setGray(grayLevel);
            printer.spaceSet((byte) 0, (byte) lineSpace);
            printer.doubleWidth(large, large);
            printer.doubleHeight(large, large);
            printer.invert(invert);
            printer.printStr(text, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @ReactMethod
    public void printQRCode(String text) {
        try {
            printer.setGray(500); 
            printer.printBitmap(qrcodeUtility.encodeAsBitmap(text, 1024, 1024 ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @ReactMethod
    public void init() {
        try {
             printer.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @ReactMethod
    public void start() {
        try {
             printer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @ReactMethod
    public void printBarcode(String text) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            BitMatrix bitMatrix = barcodeEncoder.encode(text, BarcodeFormat.CODE_128, 600, 200);

            printer.setGray(500);
            printer.printBitmap(barcodeEncoder.createBitmap(bitMatrix));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 

    @ReactMethod
    public void printBitmap(String base64, Double cutMode) {
        try {
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            printer.printBitmap(bitmap);
            printer.cutPaper(cutMode.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void openDrawer(Promise promise) {
        final int result = cashDrawer.open();

        if (result == 0) {
            promise.resolve(result);
        } else {
            promise.reject("Error "+ result, "The cash drawer cannot be opened.");
        }
    }
}
