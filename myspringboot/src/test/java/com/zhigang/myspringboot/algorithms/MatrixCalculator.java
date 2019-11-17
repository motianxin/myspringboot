/**
 * FileName: MatrixCalculator
 * Author:   Administrator
 * Date:     2019/5/17 15:24
 * Description: 二维矩阵计算实现及定理证明
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms;

/**
 * 〈二维矩阵计算实现及定理证明〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/5/17 15:24
 */
public class MatrixCalculator {

    private int[][] table;

    private int n;

    public int[][] getTable() {
        return table;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }

    public void printMatrix() {
        if (table == null) {
            System.out.println("array is null.");
            return;
        }
        int[] temp;
        for (int i = 0, length = table.length; i < length; i++) {
            temp = table[i];
            for (int j = 0, size = temp.length; j < size; j++) {
                System.out.print(temp[j] + " ");
            }
            System.out.println();
        }
    }

    public void initTable(int n) {
        this.n = n;
        table = new int[n][n];
    }

    public void setTable_i_j(int i, int j, int value) throws Exception {
        table[i][j] = value;
    }


    public int getMaValue() {
        return 0;
    }


}
