/**
 * FileName: AStarSearch
 * Author:   Administrator
 * Date:     2019/5/21 15:32
 * Description: A*寻址算法实现
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈A*寻址算法实现〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/5/21 15:32
 */
public class AStarSearch {

    private static Node[][] table;

    private static List<Node> openList = new ArrayList<>();


    private static List<Node> closeList = new ArrayList<>();

    private class Node {
        /**
         * 行
         */
        private int row;
        /**
         * 列
         */
        private int column;

        /**
         * 父节点
         */
        private Node parentNode;

        int h;
        int g;
        int f;

        /**
         * node值标志是否可行
         */
        private int value;

        public Node(int row, int column, int value) {
            this.row = row;
            this.column = column;
            this.value = value;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public Node getParentNode() {
            return parentNode;
        }

        public void setParentNode(Node parentNode) {
            this.parentNode = parentNode;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /**
     * @Author Administrator
     * @Description 初始化二维节点表，初始值为0
     * @Param [row, column]
     * @Return void
     * @date 2019/5/21 15:46
     **/
    public void initNodes(int row, int column) {
        table = new Node[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                table[i][j] = new Node(i, j, 0);
            }
        }
    }


    /**
     * @Author Administrator
     * @Description 设置节点，开始节点值设置为1，结束节点值设置为2，障碍物设置为-1
     * @Param [row, column]
     * @Return void
     * @date 2019/5/21 15:46
     **/
    public void setNodeValue(int row, int column, int value) {
        if (row > table.length - 1 || column > table[0].length - 1) {
            throw new IndexOutOfBoundsException("row or column out of bounds.");
        }
        table[row][column].setValue(value);
    }

    public Node aSearch(Node start, Node end) {
        // 把起点加入 open list
        openList.add(start);
        //主循环，每一轮检查一个当前方格节点
        while (openList.size() > 0) {
            // 在OpenList中查找 F值最小的节点作为当前方格节点
            Node current = findMinNode();
            // 当前方格节点从open list中移除
            openList.remove(current);
            // 当前方格节点进入 close list
            closeList.add(current);
            // 找到所有邻近节点
            List<Node> neighbors = findNeighbors(current);
            for (Node node : neighbors) {
                if (!openList.contains(node)) {
                    //邻近节点不在OpenList中，标记父亲、G、H、F，并放入OpenList
                    markAndInvolve(current, end, node);
                    openList.add(node);
                }
            }
            //如果终点在OpenList中，直接返回终点格子
            if (find(openList, end) != null) {
                return find(openList, end);
            }
        }
        //OpenList用尽，仍然找不到终点，说明终点不可到达，返回空
        return null;
    }

    private Node find(List<Node> openList, Node end) {
        for (Node node : openList) {
            if (node == end) {
                return node;
            }
        }
        return null;
    }

    private void markAndInvolve(Node current, Node end, Node node) {
        node.g = Math.abs(node.row - current.row) + Math.abs(node.column - current.column);

        node.h = Math.abs(node.row - end.row) + Math.abs(node.column - end.column);

        node.f = node.g + node.h;

        node.parentNode = current;

    }

    private List<Node> findNeighbors(Node current) {
        List<Node> nearNodes = new ArrayList<>();
        int row = current.getRow();
        int column = current.getColumn();
        if (column + 1 <= table[0].length && table[row][column + 1].getValue() != -1) {
            nearNodes.add(table[row][column]);
        }
        if (row + 1 <= table.length && table[row + 1][column].getValue() != -1) {
            nearNodes.add(table[row][column]);
        }
        if (row - 1 >= 0 && table[row - 1][column].getValue() != -1) {
            nearNodes.add(table[row][column]);
        }
        if (column - 1 >= 0 && table[row][column - 1].getValue() != -1) {
            nearNodes.add(table[row][column]);
        }
        return nearNodes;
    }

    private Node findMinNode() {
        int f = openList.get(0).f;
        Node temp = null;
        for (Node node : openList) {
            if (node.f <= f) {
                temp = node;
            }
        }
        return temp;
    }

}
