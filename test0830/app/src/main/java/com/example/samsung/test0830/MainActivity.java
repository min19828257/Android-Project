package com.example.samsung.test0830;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String operand1 = "";
    String operand2 = "";
    boolean isNextOperand = false;

    String operator = "";

    EditText resultEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultEdit = findViewById(R.id.re);
    }

    public void onClick(View v){
        Button button = (Button) v;

        String buttonValue = button.getText().toString();

        if(buttonValue.equals("/") || buttonValue.equals("*") || buttonValue.equals("-") || buttonValue.equals("+"))
        {
            if(TextUtils.isEmpty(operand1)) return;
            isNextOperand = true;
            operand2 = "";

            operator = buttonValue;
            resultEdit.setText(operand1+ " " + operator);
        }
        else if(buttonValue.equals("clear"))
        {
            resultEdit.setText("");
            operand1 = "";
            operand2 =  "";
            isNextOperand = false;
        }
        else if(buttonValue.equals("=")) {
            if(TextUtils.isEmpty(operand1) || TextUtils.isEmpty(operand2) || TextUtils.isEmpty(operator)) {
                return;
            }
            resultEdit.setText(resultEdit.getText() + " " + operator + " " + cal(operand1,operand2,operator));
        }
        else
        {
            if(isNextOperand)
            {
                operand2 = operand2 + buttonValue;
                resultEdit.setText(operand1 + " " + operator + " " + operand2);
            }
            else{
                operand1 = operand1 + buttonValue;
                resultEdit.setText(operand1);
        }
        }
    }

    private int cal(String operator1, String operator2, String operator)
    {
        int op1 = Integer.parseInt(operator1);
        int op2 = Integer.parseInt(operator2);

        if (operator.equals("+"))
            return op1 + op2;
        else if(operator.equals("-"))
            return op1 - op2;
        else
            return 0;
    }
}
