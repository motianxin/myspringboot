/**
 * FileName: Game
 * Author:   Administrator
 * Date:     2019/5/30 10:55
 * Description: 16方格游戏
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms;

/**
 * 〈16方格游戏〉
 *
 * @author Administrator
 * @create 2019/5/30 10:55
 * @version 3.2.2
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class Game extends JFrame {

    JPanel mainPanel = new JPanel();
    JButton button[][] = new JButton[4][4];
    int data[][] = new int[4][4];
    ArrayList<String> num = new ArrayList<String>();


    public Game() {
        super("16方格排序游戏");
        Container c = this.getContentPane();
        mainPanel.setLayout(new GridLayout(4, 4, 3, 3));
        c.add(mainPanel);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                button[i][j] = new JButton();
                mainPanel.add(button[i][j]);
                button[i][j].addActionListener(new Handler());
                data[i][j] = 0;
            }
        }
        InitNum();
    }

    void InitNum() {
        num.clear();
        for (int i = 0; i < 16; i++) {
            num.add(String.valueOf(i));
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Random r = new Random();
                int index = r.nextInt(num.size());
                String string = num.get(index);
                if (string.equals("0")) {
                    button[i][j].setText("");
                    data[i][j] = 0;
                } else {
                    button[i][j].setText(string);
                    data[i][j] = Integer.parseInt(string);
                }
                num.remove(index);
            }
        }
    }

    class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().length() == 0)
                return;
            int n = Integer.parseInt(e.getActionCommand());
            int locationx = 0, locationy = 0;
            int location = 0;
            int aim = -1;//记录空白按钮位置
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (data[i][j] == n) {
                        locationx = i;
                        locationy = j;
                        location = i * 4 + j;
                    }
                }
            }
            int cand[] = {location - 1, location + 1, location - 4, location + 4};
            for (int i = 0; i < 4; i++) {
                if (check(cand[i])) {
                    aim = cand[i];
                }
            }
            if (aim >= 0 && aim < 16) {
                int temp = data[locationx][locationy];
                button[aim / 4][aim % 4].setText(String.valueOf(temp));
                data[aim / 4][aim % 4] = temp;
                button[locationx][locationy].setText("");
                data[locationx][locationy] = 0;
            }

            if (isOver()) {
                int choice = JOptionPane.showConfirmDialog(null, JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION)
                    InitNum();
                else
                    System.exit(0);
            }
        }

        boolean check(int index) {
            if (index >= 0 && index < 16 && data[(index) / 4][(index) % 4] == 0)
                return true;
            return false;
        }

        boolean isOver() {
            int i, j;
            for (i = 0; i < 16; i++) {
                if (data[i / 4][i % 4] != i)
                    break;
            }
            if (i == 16)
                return true;

            for (i = 0; i < 15; i++) {
                if (data[i / 4][i % 4] != i + 1)
                    break;
            }
            if (i == 15)
                return true;

            for (i = 0, j = 15; i < 16; i++, j--) {
                if (data[j / 4][j % 4] != i)
                    break;
            }
            if (i == 16)
                return true;

            for (i = 0, j = 15; i < 15; i++, j--) {
                if (data[j / 4][j % 4] != i + 1)
                    break;
            }
            if (i == 15)
                return true;

            return false;
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setSize(300, 300);
        game.setVisible(true);
    }

}

