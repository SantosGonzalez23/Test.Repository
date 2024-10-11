package com.example.calculatorlab1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonleftbrack, buttonrightbrack, buttondiv, button7, button8, button9, buttonX, button4;
    MaterialButton button5, button6, buttonplus, button1, button2, button3, buttonminus, buttonAC, button0, buttondot, buttonequals;

    boolean firstPress = true;
    String lastResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // UI components for displaying results and input
        resultTV = findViewById(R.id.result_tv);
        solutionTV = findViewById(R.id.solution_tv);

        assignID(buttonC, R.id.button_c);
        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);
        assignID(buttonAC, R.id.button_AC);
        assignID(buttonleftbrack, R.id.button_leftbrack);
        assignID(buttonrightbrack, R.id.button_rightbrack);
        assignID(buttondiv, R.id.button_div);
        assignID(buttonX, R.id.button_X);
        assignID(buttonminus, R.id.button_minus);
        assignID(buttonplus, R.id.button_plus);
        assignID(buttondot, R.id.button_dot);
        assignID(buttonequals, R.id.button_equals);
    }

    /**
     * Assigns a view ID to a MaterialButton and sets its OnClickListener to the current activity.
     *
     * @param btn The MaterialButton to be assigned.
     * @param id The resource ID of the button.
     */
    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }
    /**
     * Handles click events for all calculator buttons. Depending on the button clicked,
     * the corresponding action (e.g., updating display, clearing input, calculating result) is executed.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();


        if (firstPress) {
            solutionTV.setText("");
            resultTV.setText("");
            firstPress = false;
        }


        if (buttonText.equals("AC")) {
            solutionTV.setText("");
            resultTV.setText("0");
            lastResult = "";
            return;
        }


        if (buttonText.equals("=")) {
            String finalResult = getResults(dataToCalculate);
            if (!finalResult.equals("Err")) {
                resultTV.setText(finalResult);
            }
            return;
        }


        if (buttonText.equals("C")) {
            if (dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {

            dataToCalculate += buttonText;
        }


        solutionTV.setText(dataToCalculate);


        lastResult = getResults(dataToCalculate);
    }

    /**
     * Evaluates the mathematical expression passed as a string.
     *
     * @param data The mathematical expression to evaluate.
     * @return The result of the evaluation as a string, or "Err" if there is an error.
     */
    String getResults(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
        } catch (Exception e) {
            return "Err";
        }
    }
}