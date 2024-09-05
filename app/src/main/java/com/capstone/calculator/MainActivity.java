package com.capstone.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private TextView userInputTextView;
    private TextView resultTextView;

    private static final String USER_INPUT_KEY = "USER_INPUT";
    private static final String RESULT_KEY = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInputTextView = findViewById(R.id.UserInputNumbersTextView);
        resultTextView = findViewById(R.id.resultText);

        if (savedInstanceState != null) {
            userInputTextView.setText(savedInstanceState.getString(USER_INPUT_KEY));
            resultTextView.setText(savedInstanceState.getString(RESULT_KEY));
        }

        int[] buttonIds = new int[]{
                R.id.buttonAllClear, R.id.buttonDivide, R.id.buttonMultiply, R.id.buttonClear,
                R.id.button7, R.id.button8, R.id.button9, R.id.buttonSubtract,
                R.id.button4, R.id.button5, R.id.button6, R.id.buttonAdd,
                R.id.button1, R.id.button2, R.id.button3, R.id.buttonEquals,
                R.id.buttonDot, R.id.button0, R.id.buttonPercentage
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String buttonText = button.getText().toString();
                handleButtonClick(buttonText);
            }
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(USER_INPUT_KEY, userInputTextView.getText().toString());
        outState.putString(RESULT_KEY, resultTextView.getText().toString());
    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "AC":
                userInputTextView.setText("");
                resultTextView.setText("");
                break;
            case "C":
                if (!userInputTextView.getText().toString().isEmpty()) {
                    String text = userInputTextView.getText().toString();
                    userInputTextView.setText(text.substring(0, text.length() - 1));
                }
                break;
            case "=":
                calculateResult();
                break;
            case "X":
                userInputTextView.append("*");
                break;
            case "/":
                userInputTextView.append("/");
                break;
            case "%":
                if (!userInputTextView.getText().toString().isEmpty()) {
                    String currentValue = userInputTextView.getText().toString();
                    try {
                        double percentageValue = Double.parseDouble(currentValue) / 100;
                        userInputTextView.setText(String.valueOf(percentageValue));
                    } catch (NumberFormatException e) {
                        resultTextView.setText("Error");
                    }
                }
                break;
            case ".":
                if (!userInputTextView.getText().toString().contains(".")) {
                    userInputTextView.append(".");
                }
                break;
            default:
                if (userInputTextView.getText().toString().equals("0")) {
                    userInputTextView.setText(buttonText);
                } else {
                    userInputTextView.append(buttonText);
                }
                break;
        }
    }

    private void calculateResult() {
        String expression = userInputTextView.getText().toString();
        try {
            ExpressionBuilder builder = new ExpressionBuilder(expression);
            double result = builder.build().evaluate();
            String formattedResult = String.format("%.2f", result);
            resultTextView.setText(formattedResult);
        } catch (Exception e) {
            resultTextView.setText("Error");
        }
    }
}
