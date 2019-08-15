package edu.fullsail.mgems.cse.treasurehunter.matthewmartnick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button credits = findViewById(R.id.btnCredits);
        credits.setOnClickListener(this);

        Button inventory = findViewById(R.id.btnInventory);
        inventory.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCredits) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Made by");
            dialog.setMessage("Matt Martnick\nMGMS | APM\n8/7/2019");
            dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int button) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else if (v.getId() == R.id.btnInventory) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Inventory");

            ArrayList<Item> inv = DrawSurface.getInventory();
            List<String> treasure = new ArrayList<>();
            for (Item item : inv) {
                String newline = System.getProperty("line.separator");
                treasure.add(item.name + newline);
            }

            dialog.setMessage(treasure.toString());
            dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int button) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}