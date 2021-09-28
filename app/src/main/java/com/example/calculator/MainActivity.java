package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button solution;
    Button minus;
    Button plus;
    Button multiplication;
    Button comma;
    Button drop;
    Button right_br;
    Button left_br;
    Button division;
    TextView text_xml;
    String text = "";
    double otvet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        minus = (Button) findViewById(R.id.minus);
        plus = (Button) findViewById(R.id.plus);
        multiplication = (Button) findViewById(R.id.multiplication);
        drop = (Button) findViewById(R.id.drop);
        right_br = (Button) findViewById(R.id.right_br);
        left_br = (Button) findViewById(R.id.left_br);
        division = (Button) findViewById(R.id.division);
        comma = (Button) findViewById(R.id.comma);
        solution = (Button) findViewById(R.id.solution);
        text_xml = (TextView) findViewById(R.id.text);
        OneTup();

    }
    @SuppressLint("SetTextI18n")
    void OneTup() {
        button0.setOnClickListener(view -> {
                text += "0";
                text_xml.setText(text);
                });
        button1.setOnClickListener(view -> {
                text += "1";
                text_xml.setText(text);
                });
        button2.setOnClickListener(view -> {
                text += "2";
                text_xml.setText(text);
                });
        button3.setOnClickListener(view -> {
                text += "3";
                text_xml.setText(text);
        });
        button4.setOnClickListener(view -> {
                text += "4";
            text_xml.setText(text);
        });
        button5.setOnClickListener(view -> {
                text += "5";
            text_xml.setText(text);
        });
        button6.setOnClickListener(view -> {
                text += "6";
            text_xml.setText(text);
        });
        button7.setOnClickListener(view -> {
                text += "7";
            text_xml.setText(text);
        });
        button8.setOnClickListener(view -> {
                text += "8";
            text_xml.setText(text);
        });
        button9.setOnClickListener(view -> {
                text += "9";
            text_xml.setText(text);
        });
        minus.setOnClickListener(view -> {
                text += "-";
            text_xml.setText(text);
        });
        plus.setOnClickListener(view -> {
                text += "+";
            text_xml.setText(text);
        });
        multiplication.setOnClickListener(view -> {
                text += "*";
            text_xml.setText(text);
        });
        division.setOnClickListener(view -> {
                text += "/";
            text_xml.setText(text);
        });
        drop.setOnClickListener(view -> {
                text = "";
            text_xml.setText(text);
        });
        left_br.setOnClickListener(view -> {
                text += "(";
            text_xml.setText(text);
        });
        right_br.setOnClickListener(view -> {
                text += ")";
            text_xml.setText(text);
        });
        comma.setOnClickListener(view -> {
                text += ".";
            text_xml.setText(text);
        });
        solution.setOnClickListener(view -> {
            otvet = eval(text);
            text_xml.setText(otvet+"");
        });
    }
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}