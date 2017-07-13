package com.example.ofek.calculatorexercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import widgets.DescButton;

public class MainActivity extends AppCompatActivity {
    TextView numbersTV,ansTV;
    Button deleteBtn,clearBtn;
    DescButton num1Btn,num2Btn,num3Btn,num4Btn,num5Btn,num6Btn,num7Btn,num8Btn,num9Btn,num0Btn;
    DescButton equalsBtn,slashBtn,asterixBtn,minusBtn,plusBtn,dotBtn;
    DescButton lastClicked=null;
    DescButton lastOperand=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setNumbersListener();
        setOperandsListeners();
        setClearButtonsListener();
    }
    // initialize all views
    public void initializeViews(){
        //buttons
        num0Btn=(DescButton) findViewById(R.id.Num0Btn);
        num0Btn.setDescription("0");
        num1Btn=(DescButton) findViewById(R.id.Num1Btn);
        num1Btn.setDescription("1");
        num2Btn=(DescButton) findViewById(R.id.Num2Btn);
        num2Btn.setDescription("2");
        num3Btn=(DescButton) findViewById(R.id.Num3Btn);
        num3Btn.setDescription("3");
        num4Btn=(DescButton) findViewById(R.id.Num4Btn);
        num4Btn.setDescription("4");
        num5Btn=(DescButton) findViewById(R.id.Num5Btn);
        num5Btn.setDescription("5");
        num6Btn=(DescButton) findViewById(R.id.Num6Btn);
        num6Btn.setDescription("6");
        num7Btn=(DescButton) findViewById(R.id.Num7Btn);
        num7Btn.setDescription("7");
        num8Btn=(DescButton) findViewById(R.id.Num8Btn);
        num8Btn.setDescription("8");
        num9Btn=(DescButton) findViewById(R.id.Num9Btn);
        num9Btn.setDescription("9");
        equalsBtn=(DescButton) findViewById(R.id.EqualsBtn);
        equalsBtn.setDescription("=");
        dotBtn=(DescButton) findViewById(R.id.DotBtn);
        dotBtn.setDescription(".");
        slashBtn=(DescButton) findViewById(R.id.slashBtn);
        slashBtn.setDescription("/");
        asterixBtn=(DescButton) findViewById(R.id.AsterixBtn);
        asterixBtn.setDescription("*");
        plusBtn=(DescButton) findViewById(R.id.PlusBtn);
        plusBtn.setDescription("+");
        minusBtn=(DescButton) findViewById(R.id.MinBtn);
        minusBtn.setDescription("-");
        //Text views
        //---------------------
        ansTV=(TextView) findViewById(R.id.AnsTV);
        numbersTV=(TextView) findViewById(R.id.NumbersTV);
    }
    //numbers onClick listener//
    public View.OnClickListener NumbersClickListener(){
        return new View.OnClickListener() {
            String numStr;
            @Override
            public void onClick(View view){
                if(numbersTV.getText().toString().length()<18) {
                    numStr = numbersTV.getText().toString();
                    numStr += ((DescButton) view).getAsString();
                    numbersTV.setText(numStr);
                    lastClicked = (DescButton) view;
                }
            }
        };
    }
    //set the numbers buttons onClick listener
    public void setNumbersListener(){
        num0Btn.setOnClickListener(NumbersClickListener());
        num1Btn.setOnClickListener(NumbersClickListener());
        num2Btn.setOnClickListener(NumbersClickListener());
        num3Btn.setOnClickListener(NumbersClickListener());
        num4Btn.setOnClickListener(NumbersClickListener());
        num5Btn.setOnClickListener(NumbersClickListener());
        num6Btn.setOnClickListener(NumbersClickListener());
        num7Btn.setOnClickListener(NumbersClickListener());
        num8Btn.setOnClickListener(NumbersClickListener());
        num9Btn.setOnClickListener(NumbersClickListener());
    }
    //set operands buttons onClick listener
    //for those operands '-', '+' , '/' , '*' - only the + listener is described, the others has the same logic.

    public void setOperandsListeners(){
        // "=" button listener
        equalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ansTV.getText().toString().length()==1||numbersTV.getText().toString().isEmpty()){     // do nothing if the numbers textview is empty and if the ansTv is empty
                    return;
                }
                if (numbersTV.getText().toString().endsWith("."))   //in order to avoid only a point at the end
                    numbersTV.setText(numbersTV.getText().toString().substring(0,numbersTV.getText().toString().length()-1));
                double currntNum= Double.parseDouble(numbersTV.getText().toString());
                if (lastOperand.getAsString().equals("/")&&currntNum==0){                                           //avoid divide to 0, alert the user
                    Toast.makeText(MainActivity.this, "You cant divide in zero!", Toast.LENGTH_LONG).show();
                    return;
                }
                double result1=calculate(lastOperand.getAsString(),extractPrevNumber(),currntNum);
                String lastNum=ansTV.getText().toString();
                lastNum=lastNum.substring(1);
                String currentNumStr="";
                if (currntNum%1==0){                                                //just for a nicer interface
                    currentNumStr+=(long)(currntNum);
                }
                else {
                    currentNumStr+=currntNum;
                }
                long result;
                if (result1%1==0) {
                    result = (long) result1;
                    ansTV.setText(lastNum+lastOperand.getAsString()+currentNumStr+"="+result);
                }
                else {
                    ansTV.setText(lastNum+lastOperand.getAsString()+currentNumStr+"="+result1);
                }
                numbersTV.setText(null);
                lastOperand=null;
                lastClicked=equalsBtn;
            }
        });
        //-----------------------
        //dot listener
        dotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=numbersTV.getText().toString();
                if (str.contains("."))                          //if there was a dot, you cant add another one
                    return;
                if (str==null||str.isEmpty())
                    str="0.";
                else
                    str+=".";
                numbersTV.setText(str);
                lastClicked=dotBtn;
            }
        });
        //-----------------------
        //"+" listener
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastClicked!=null&&lastClicked.getAsString().equals("+")||lastClicked==null)    //in the initial run of the app you cant use the '+' btn,also if your operand is already a'+' break the function
                    return;
                if (numbersTV.getText().toString().endsWith("."))   //in order to avoid only a point at the end
                    numbersTV.setText(numbersTV.getText().toString().substring(0,numbersTV.getText().toString().length()-1));
                String str;
                if (lastClicked!=null&&lastOperand!=null&&lastClicked.getAsString().equals(lastOperand.getAsString())){     //in order to easily doing multiple adding
                    str=ansTV.getText().toString();
                    str = str.substring(str.indexOf(lastOperand.getAsString())+1);
                }
                else {
                    if (lastClicked != null && lastClicked.getAsString().equals("=")) {
                        str = ansTV.getText().toString();
                        str = str.substring(str.indexOf("=")+1);
                        double num=Double.parseDouble(str);
                        if (num%1==0)                                                                   //just for a nicer interface
                            str=""+(int)num;
                    }
                    else {
                        str=numbersTV.getText().toString();
                    }
                }

                ansTV.setText("+"+str);
                numbersTV.setText(null);
                lastOperand=plusBtn;
                lastClicked=plusBtn;
            }
        });
        //-----------------------
        //"-" listener
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastClicked!=null&&lastClicked.getAsString().equals("-"))
                    return;
                if (numbersTV.getText().toString().endsWith("."))   //in order to avoid only a point at the end
                    numbersTV.setText(numbersTV.getText().toString().substring(0,numbersTV.getText().toString().length()-1));
                String str;
                if (lastClicked!=null&&lastOperand!=null&&lastClicked.getAsString().equals(lastOperand.getAsString())){
                    str=ansTV.getText().toString();
                    str=str.substring(1);
                }
                else {
                    if (lastClicked != null && lastClicked.getAsString().equals("=")) {
                        str = ansTV.getText().toString();
                        str = str.substring(str.indexOf("=")+1);
                        double num=Double.parseDouble(str);
                        if (num%1==0)
                            str=""+(int)num;
                    }
                    else {
                        str=numbersTV.getText().toString();
                    }
                }
                ansTV.setText("-"+str);
                numbersTV.setText(null);
                lastOperand=minusBtn;
                lastClicked=minusBtn;
            }
        });
        //-----------------------
        //"/" listener
        slashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastClicked!=null&&lastClicked.getAsString().equals("/"))
                    return;
                if (numbersTV.getText().toString().endsWith("."))   //in order to avoid only a point at the end
                    numbersTV.setText(numbersTV.getText().toString().substring(0,numbersTV.getText().toString().length()-1));
                String str;
                if (lastClicked!=null&&lastOperand!=null&&lastClicked.getAsString().equals(lastOperand.getAsString())){
                    str=ansTV.getText().toString();
                    str=str.substring(1);
                }
                else {
                    if (lastClicked != null && lastClicked.getAsString().equals("=")) {
                        str = ansTV.getText().toString();
                        str = str.substring(str.indexOf("=")+1);
                        double num=Double.parseDouble(str);
                        if (num%1==0)
                            str=""+(int)num;
                    }
                    else {
                        str=numbersTV.getText().toString();
                    }
                }
                ansTV.setText("/"+str);
                numbersTV.setText(null);
                lastOperand=slashBtn;
                lastClicked=slashBtn;
            }
        });
        //-----------------------
        //"*" listener
        asterixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastClicked!=null&&lastClicked.getAsString().equals("*"))
                    return;
                if (numbersTV.getText().toString().endsWith("."))   //in order to avoid only a point at the end
                    numbersTV.setText(numbersTV.getText().toString().substring(0,numbersTV.getText().toString().length()-1));
                String str;
                if (lastClicked!=null&&lastOperand!=null&&lastClicked.getAsString().equals(lastOperand.getAsString())){
                    str=ansTV.getText().toString();
                    str=str.substring(1);
                }
                else {
                    if (lastClicked != null && lastClicked.getAsString().equals("=")) {
                        str = ansTV.getText().toString();
                        str = str.substring(str.indexOf("=")+1);
                        double num=Double.parseDouble(str);
                        if (num%1==0)
                            str=""+(int)num;
                    }
                    else {
                        str=numbersTV.getText().toString();
                    }
                }
                ansTV.setText("*"+str);
                numbersTV.setText(null);
                lastOperand=asterixBtn;
                lastClicked=asterixBtn;
            }
        });

    }
    //calculation algorithm
    public double calculate(String operand,double num1,double num2){
        switch (operand) {
            case ("+"):
                return num1 + num2;
            case ("-"):
                return num1 - num2;
            case ("*"):
                return num1 * num2;
            case ("/"):
                return num1 / num2;
            default:
                return -1;
        }
    }
    //extract from the ansTV the previous entered number
    public double extractPrevNumber(){
        String str=ansTV.getText().toString();
        str=str.substring(1);
        return Double.parseDouble(str);
    }
    public void setClearButtonsListener(){
        clearBtn=(Button) findViewById(R.id.clearAllBtn);
        deleteBtn=(Button) findViewById(R.id.delBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbersTV.setText(null);
                ansTV.setText("=");
                lastClicked=null;
                lastOperand=null;
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=numbersTV.getText().toString();
                if (str.isEmpty())
                    return;
                if (str.length()==1||str.equals("0."))
                    numbersTV.setText(null);
                else {
                    str = str.substring(0, str.length() - 1);
                    numbersTV.setText(str);
                }
            }
        });
    }
}
