package com.example.prototypeb.ui.game.Game_components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;

import com.example.prototypeb.ui.game.GameFragment;

public class ExampleDialog extends AppCompatDialogFragment {
    View v;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("GAME ENDED")
                .setMessage("Good Job!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(v.getContext(), GameFragment.class);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}