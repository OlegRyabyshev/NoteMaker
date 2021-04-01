package xyz.fcr.notemaker.activity_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.Button;
import android.widget.TextView;

import xyz.fcr.notemaker.MainActivity;
import xyz.fcr.notemaker.R;

public class AboutActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView telegramView = findViewById(R.id.about_text_3);
        telegramView.setText(getText(R.string.about_text_telegram) + " t.me/OlegRyabyshev");
        Linkify.addLinks(telegramView, Linkify.WEB_URLS);

        Button buttonReturn = findViewById(R.id.button_return);

        buttonReturn.setOnClickListener((view) -> {
            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(AboutActivity.this, MainActivity.class));
        finish();
    }
}