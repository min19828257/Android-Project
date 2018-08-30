package com.example.samsung.test0828;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    String operand1 ="";
    String operand2 ="";
    boolean isNextOperand = false;

    String operator = "";

    EditText resultEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultEdit = findViewById(R.id.editText);
    }

    public void onClick(View v) {
        Button button = (Button) v;

        String buttonValue = button.getText().toString();

        if(buttonValue.equals("/") || buttonValue.equals("*") || buttonValue.equals("-") || buttonValue.equals("+"))
        {
            if(TextUtils.isEmpty(operand1)) return;
            isNextOperand = true;
            operand2 = "";

            operator = buttonValue;

            resultEdit.setText(operand1+ " "+ operator);
        } else if (buttonValue.equals("clear")){
            resultEdit.setText("");
            setClear();
        }else if(buttonValue.equals("=")){
            if(TextUtils.isEmpty(operand1) || TextUtils.isEmpty(operand2) || TextUtils.isEmpty(operator))
            {
                return;
            }

            resultEdit.setText(resultEdit.getText() + " " +buttonValue + " " + getCalc(operator, operand1, operand2));
            setClear();
        }else
            if(isNextOperand) {
                operand2 = operand2 + buttonValue;
                resultEdit.setText(operand1 + " "+ operator + " "+operand2);
            }

            else{
                    operand1 = operand1 + buttonValue;
                    resultEdit.setText(operand1);
                }
            }

private void setClear(){
    operand1 = "";
    operand2 = "";
    isNextOperand = false;
}

private int getInteger(String s)
{
    int value = -1;

    try{
        value = Integer.parseInt(s);
    }catch (NumberFormatException e){
    }

    return value;
}

private int getCalc(String operator, String operand1, String operand2){
    int op1 = getInteger(operand1);
    int op2 = getInteger(operand2);

    if(operator.equals("/")) return op1/op2;
    else if(operator.equals("*")) return op1+op2;
    else if(operator.equals("-")) return op1-op2;
    else if(operator.equals("+")) return op1+op2;
    else return 0;
}
}