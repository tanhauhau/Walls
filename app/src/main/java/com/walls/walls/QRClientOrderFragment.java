package com.walls.walls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by lhtan on 9/5/15.
 */
public class QRClientOrderFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
//        integrator.setCaptureLayout(R.layout.qr_scan_area);
//        integrator.setLegacyCaptureLayout(R.layout.qr_scan_area);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                processContent(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void processContent(String contents) {
        OrderTask.startTask(getActivity(), contents, "qrtable");
    }
}
