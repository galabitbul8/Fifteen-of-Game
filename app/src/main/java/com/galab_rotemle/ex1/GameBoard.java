package com.galab_rotemle.ex1;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GameBoard {
    int [][] matrix;
    int[] emptyPlace;
    int count;
    public GameBoard(){
        matrix = initMatrix(4);
        emptyPlace = new int[] {matrix.length - 1, matrix.length - 1};
        shuffleMatrix();
        count = 0;
    }

    public boolean checkGameStatus() {
        int length = matrix.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(matrix[i][j] != (j + 1 + i*length))
                    return false;
            }
        }
        return true;
    }

    private int[][] initMatrix(int size) {
        int[][]mat = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[i][j] = (j + 1 + i*size);
            }
        }
        return mat;
    }

    private void switchPlaces(int[] switchPlace, int value) {
        matrix[switchPlace[0]][switchPlace[1]] = 16;
        matrix[emptyPlace[0]][emptyPlace[1]] = value;
        emptyPlace[0] = switchPlace[0];
        emptyPlace[1] = switchPlace[1];
    }

    public void shuffleMatrix() {
        count = 0;
        int[] preLoc = new int[] {matrix.length - 1, matrix.length - 1};
        int[][] indexes = new int[4][2];

        int numberOfSteps = 100;
        while(numberOfSteps != 0) {
            int index2 = 0;
            if(emptyPlace[0]!=3 && !(preLoc[0]== emptyPlace[0]+1 && preLoc[1]== emptyPlace[1] )) {
                indexes[index2][0]= emptyPlace[0]+1;
                indexes[index2][1]= emptyPlace[1];
                index2++;
            }


            if(emptyPlace[0]!=0  && !(preLoc[0]== emptyPlace[0]-1 && preLoc[1]== emptyPlace[1] )) {
                indexes[index2][0]= emptyPlace[0]-1;
                indexes[index2][1]= emptyPlace[1];
                index2++;

            }

            if(emptyPlace[1]!=0  && !(preLoc[0]== emptyPlace[0] && preLoc[1]== emptyPlace[1]-1 )) {
                indexes[index2][0]= emptyPlace[0];
                indexes[index2][1]= emptyPlace[1]-1;
                index2++;

            }

            if(emptyPlace[1]!=3  && !(preLoc[0]== emptyPlace[0] && preLoc[1]== emptyPlace[1]+1 )) {
                indexes[index2][0]= emptyPlace[0];
                indexes[index2][1]= emptyPlace[1]+1;
                index2++;
            }
            int rand = (int)(Math.random() * index2+1)-1;

            preLoc[0] = emptyPlace[0];
            preLoc[1] = emptyPlace[1];
            switchPlaces(indexes[rand], matrix[indexes[rand][0]][indexes[rand][1]]);

            numberOfSteps--;
        }
    }
    public void switchByPress(int[][] matrix,int num) {
        Log.d("myLog", "switchByPress: ");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(num == matrix[i][j] &&
                        (((i==emptyPlace[0]-1 ||i==emptyPlace[0]+1) && j == emptyPlace[1]) ||
                                ((j==emptyPlace[1]-1 ||j==emptyPlace[1]+1) && i == emptyPlace[0]) )) {
                    switchPlaces(new int[] {i,j} , num);
                    count++;
                    return;
                }
            }
        }
    }
}
