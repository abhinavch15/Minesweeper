package com.example.abhinavchinta.minesweeper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TableLayout table;

    private static final int ROW = 15;//15;
    private static final int COL = 10;//10;
    private static final int no_bombs = 25;
    private static final int BOMB_ROW[] = new int[no_bombs];
    private static final int BOMB_COL[] = new int[no_bombs];


    Button buttons[][] = new Button[ROW][COL];
    Button bombarray[] = new Button[no_bombs];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        populatetable();
        setbombs();

    }


    private void populatetable() {
        table = (TableLayout) findViewById(R.id.table);

        for (int i = 0; i < ROW; i++) {
            TableRow tablerow = new TableRow(this);
            tablerow.setLayoutParams(new TableLayout.LayoutParams(
                    0,
                    0,
                    0.2f

            ));

            table.addView(tablerow);
            for (int j = 0; j < COL; j++) {
                final int finalrow = i;
                final int finalcol = j;
                final Button button = new Button(this);

                button.setBackgroundResource(R.drawable.xxxxx);
                button.setTag(0);

                button.setLayoutParams(new TableRow.LayoutParams(
                        0,
                        115,//height fitting of tile
                        0.01f

                ));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkifgameover(finalrow, finalcol)) {
                            revealallbombs(finalrow, finalcol);
                        } else {
                            if (buttons[finalrow][finalcol].getTag().equals(0)){
                            buttons[finalrow][finalcol].setBackgroundResource(R.drawable.clickedtilewh);
                            if (!bombisnexttome(finalrow, finalcol)){buttons[finalrow][finalcol].setTag(1);}
                                else {buttons[finalrow][finalcol].setTag(2);setmynumber(finalrow,finalcol);}
                                tileclearerright(finalrow,(finalcol+1));
                            tileclearerleft(finalrow,(finalcol-1));
                            tileclearerup((finalrow+1),finalcol);
                            tileclearerdown((finalrow-1),finalcol);
                                for (int i=0;i<ROW;i++){
                                    for (int j=0;j<COL;j++){
                                        if (buttons[i][j].getTag().equals(1)){tileclearerup((i+1),j);tileclearerdown((i-1),j);tileclearerright(i,(j+1));tileclearerleft(i,(j-1));tileclearerup((i+1),j);tileclearerdown((i-1),j);//tileclearerright(finalrow,(finalcol+1));tileclearerleft(finalrow,(finalcol-1));tileclearerup((i+1),j);tileclearerdown((i-1),j);
                                             }
                                    }
                                }


                                for (int i=1;i<ROW-1;i++){
                                    for (int j=1;j<COL-1;j++){
                                        if (buttons[i][j].getTag().equals(0)){
                                            if (buttons[i+1][j+1].getTag().equals(1)||buttons[i+1][j-1].getTag().equals(1)||buttons[i-1][j+1].getTag().equals(1)||buttons[i-1][j-1].getTag().equals(1)){
                                                buttons[i][j].setBackgroundResource(R.drawable.clickedtilewh);
                                                if (!bombisnexttome(i,j)){buttons[i][j].setTag(1);}
                                                else {buttons[i][j].setTag(2);setmynumber(i,j);}
                                                tileclearerright(i,(j+1));
                                                tileclearerleft(i,(j-1));
                                                tileclearerup((i+1),j);
                                                tileclearerdown((i-1),j);
                                        }
                                        }
                                    }
                                }
                            }
                            checkifwon();
                        }
                    }

                });
                tablerow.addView(button);
                buttons[i][j] = button;


            }
        }



    }

    private void setbombs() {
        for (int i = 0; i < no_bombs; i++) {
            BOMB_ROW[i] = (int) (Math.random() * (ROW - 0));
            BOMB_COL[i] = (int) (Math.random() * (COL - 0));

        }
        //int m=(int)(Math.random() * (9 - 0));
        //int n=(int)(Math.random() * (9 - 0));


        //bombarray[0]=imagebuttons[m][n];
        for (int i = 0; i < no_bombs; i++) {
            bombarray[i] = buttons[BOMB_ROW[i]][BOMB_COL[i]];
            bombarray[i].setTag(3);
        }


    }

    private boolean checkifgameover(int row, int col) {
        for (int i = 0; i < no_bombs; i++) {
            if (bombarray[i] == buttons[row][col]) {openalert();
                return true;
            }


        }


        return false;

    }

    private void revealallbombs(int row, int col) {
        for (int i = 0; i < no_bombs; i++) {
            bombarray[i].setBackgroundResource(R.drawable.bombwh);

        }
        buttons[row][col].setBackgroundResource(R.drawable.redbomb);
    }

    private void tileclearerright(int row, int col) {

        if (col == 10) {
            return;
        }
        int flag = 0;
        for (int i = 0; i < no_bombs; i++) {
            if (bombarray[i] == buttons[row][col]) {
                flag++;
            }
        }
        if (bombisnexttome(row,col)){flag++;setmynumber(row,col);}
        if (flag == 0) {


            buttons[row][col].setBackgroundResource(R.drawable.clickedtilewh);
            buttons[row][col].setTag(1);
            if (col < 9) {
                tileclearerright(row, col + 1);
            } else {
                return;
            }

        } else {
            return;
        }
        return;

    }

    private void tileclearerleft(int row, int col) {

        if (col == -1) {
            return;
        }
        int flag = 0;
        //int a=0;
        //while(a==0) {
        for (int i = 0; i < no_bombs; i++) {
            if (bombarray[i] == buttons[row][col]) {
                flag++;
            }
            if (bombisnexttome(row,col)){flag++;setmynumber(row,col);}
        }
        if (flag == 0) {


            buttons[row][col].setBackgroundResource(R.drawable.clickedtilewh);
            buttons[row][col].setTag(1);
            if (col > 0) {
                tileclearerleft(row, col - 1);
            } else {
                return;
            }

        } else {
            return;
        }
        return;

    }
    private void tileclearerup(int row, int col) {

        if (row == 15) {
            return;
        }
        int flag = 0;
        for (int i = 0; i < no_bombs; i++) {
            if (bombarray[i] == buttons[row][col]) {
                flag++;
            }
            if (bombisnexttome(row,col)){flag++;setmynumber(row,col);}
        }
        if (flag == 0) {


            buttons[row][col].setBackgroundResource(R.drawable.clickedtilewh);tileclearerright(row,col+1);tileclearerleft(row,col-1);
            buttons[row][col].setTag(1);
            if (row < 14) {
                tileclearerup(row+1, col);
            } else {
                return;
            }

        } else {
            return;
        }
        return;

    }
    private void tileclearerdown(int row, int col) {

        if (row == -1) {
            return;
        }
        int flag = 0;
        for (int i = 0; i < no_bombs; i++) {
            if (bombarray[i] == buttons[row][col]) {
                flag++;
            }
            if (bombisnexttome(row,col)){flag++;setmynumber(row,col);}
        }
        if (flag == 0) {


            buttons[row][col].setBackgroundResource(R.drawable.clickedtilewh);tileclearerright(row,col+1);tileclearerleft(row,col-1);
            buttons[row][col].setTag(1);
            if (row > 0) {
                tileclearerdown(row-1, col);
            } else {
                return;
            }

        } else {
            return;
        }
        return;


    }
    private boolean bombisnexttome(int row,int col)
    {
        int count=0;
        if (col<9){if (buttons[row][col+1].getTag().equals(3)){count++;return true;}}
        if (row<14&&col<9){if (buttons[row+1][col+1].getTag().equals(3)){count++;return true;}}
        if (row>0&&col<9){if (buttons[row-1][col+1].getTag().equals(3)){count++;return true;}}
        if (row<14){if (buttons[row+1][col].getTag().equals(3)){count++;return true;}}
        if (col>0){if (buttons[row][col-1].getTag().equals(3)){count++;return true;}}
        if (row>0){if(buttons[row-1][col].getTag().equals(3)){count++;return true;}}
        if (row<14&&col>0){if (buttons[row+1][col-1].getTag().equals(3)){count++;return true;}}
        if (row>0&&col>0){if(buttons[row-1][col-1].getTag().equals(3)){count++;return true;}}
        return false;
    }
    private void setmynumber(int row,int col)
    {
        int count=0;
        if (col<9){if (buttons[row][col+1].getTag().equals(3)){count++;}}
        if (row<14&&col<9){if (buttons[row+1][col+1].getTag().equals(3)){count++;}}
        if (row>0&&col<9){if (buttons[row-1][col+1].getTag().equals(3)){count++;}}
        if (row<14){if (buttons[row+1][col].getTag().equals(3)){count++;}}
        if (col>0){if (buttons[row][col-1].getTag().equals(3)){count++;}}
        if (row>0){if(buttons[row-1][col].getTag().equals(3)){count++;}}
        if (row<14&&col>0){if (buttons[row+1][col-1].getTag().equals(3)){count++;}}
        if (row>0&&col>0){if(buttons[row-1][col-1].getTag().equals(3)){count++;}}
        if(buttons[row][col].getTag().equals(1)||buttons[row][col].getTag().equals(2)||buttons[row][col].getTag().equals(0)){buttons[row][col].setTag(2);}
        //buttons[row][col].setText(""+count);
        if (buttons[row][col].getTag().equals(2)) {
            if (count == 1) {
                buttons[row][col].setBackgroundResource(R.drawable.onewh);
            }
            if (count == 2) {
                buttons[row][col].setBackgroundResource(R.drawable.twowh);
            }
            if (count == 3) {
                buttons[row][col].setBackgroundResource(R.drawable.threewh);
            }
            if (count==4){buttons[row][col].setBackgroundResource(R.drawable.fourwh);}
            //if (count==5){buttons[row][col].setBackgroundResource(R.drawable.five);}
            //if (count==6){buttons[row][col].setBackgroundResource(R.drawable.six);}

        }
    }
    private void checkifwon(){
        int count=0;
        for (int i=0;i<ROW;i++){
            for (int j=0;j<COL;j++){
                if (buttons[i][j].getTag().equals(0)){count++;}
            }
        }
        if (count==0){Toast.makeText(MainActivity.this,"YOU WON :D", Toast.LENGTH_LONG).show();windialog();}

    }
    private void openalert()
    {
        AlertDialog.Builder alertdialogbuilder= new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("Game Over \nWould you like to play again?");
        alertdialogbuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this,"New Game",Toast.LENGTH_LONG).show();
                        //populatetable();
                        for (int i=0;i<ROW;i++){
                            for (int j=0;j<COL;j++){
                                  buttons[i][j].setTag(0);buttons[i][j].setBackgroundResource(R.drawable.xxxxx);
                                }
                            }


                        setbombs();

                    }
                });

        alertdialogbuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();
    }

    private void windialog()
    {
        AlertDialog.Builder alertdialogbuilder= new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("YOU WON! \nWould you like to play again?");
        alertdialogbuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this,"New Game",Toast.LENGTH_LONG).show();
                        //populatetable();
                        for (int i=0;i<ROW;i++){
                            for (int j=0;j<COL;j++){
                                buttons[i][j].setTag(0);buttons[i][j].setBackgroundResource(R.drawable.xxxxx);
                            }
                        }


                        setbombs();

                    }
                });

        alertdialogbuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();
    }
}










