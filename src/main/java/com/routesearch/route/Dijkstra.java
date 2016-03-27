package com.routesearch.route;

import java.util.*;

/**
 * Created with IntelliJ IDEA
 * Date: 2016/3/27
 * Time: 16:56
 * User: ThinerZQ
 * GitHub: <a>https://github.com/ThinerZQ</a>
 * Blog: <a>http://www.thinerzq.me</a>
 * Email: 601097836@qq.com
 */
public class Dijkstra {

    private Set<Node> open = new HashSet<Node>();
    private Set<Node> close = new HashSet<Node>();
    private Map<Integer,Node> vertexes =new HashMap<Integer, Node>();
    private Node origin =null;

    private Map<Integer,Integer> path=new HashMap<Integer, Integer>();//封装路径距离
    private Map<Integer,String> pathInfo=new HashMap<Integer, String>();//封装路径信息

    public Dijkstra() {
    }

    public Dijkstra(String[] edges, int edgeNumber, int vertexNumber, int origin) {

        for (String edge : edges) {
            String[] vertex = edge.split(",");
            int start = Integer.parseInt(vertex[1]);
            int end = Integer.parseInt(vertex[2]);
            int weight = Integer.parseInt(vertex[3]);

            Node node = vertexes.get(start);
            if (node == null){
                vertexes.put(start,new Node(start));
                node = vertexes.get(start);
            }
            if (start == origin){
                path.put(end,weight);
                pathInfo.put(end, origin+"->"+end);
            }
            Node endNode = vertexes.get(end);
            if (endNode ==null){
                vertexes.put(end,new Node(end));
                endNode = vertexes.get(end);
            }
            node.getChild().put(endNode,weight);
        }
        this.origin = vertexes.get(origin);
        for (Node node : vertexes.values()) {
            if (node.getId() != origin){
                open.add(node);
            }
            if (path.get(node.getId()) == null){
                path.put(node.getId(),Integer.MAX_VALUE);
            }
        }
        close.add(this.origin);
    }

    public void computePath(Node start) {
        Node nearest = getShortestPath(start);//取距离start节点最近的子节点,放入close
        if (nearest == null) {
            return;
        }
        close.add(nearest);
        open.remove(nearest);
        Map<Node, Integer> childs = nearest.getChild();
        for (Node child : childs.keySet()) {
            if (open.contains(child)) {//如果子节点在open中
                Integer newCompute = path.get(nearest.getId()) + childs.get(child);
                if (path.get(child.getId()) > newCompute) {//之前设置的距离大于新计算出来的距离
                    path.put(child.getId(), newCompute);
                    pathInfo.put(child.getId(), pathInfo.get(nearest.getId()) + "->" + child.getId());
                }
            }
        }

        System.out.println(nearest.getId());

        computePath(nearest);//向外一层层递归,直至所有顶点被遍历
        computePath(start);//重复执行自己,确保所有子节点被遍历
    }



    public void printPathInfo() {
        Set<Map.Entry<Integer, String>> pathInfos = pathInfo.entrySet();
        for (Map.Entry<Integer, String> pathInfo : pathInfos) {
            System.out.println(pathInfo.getKey() + ":" + pathInfo.getValue()+":: "+ path.get(pathInfo.getKey()));
        }
    }
    public void printPathInfo(int[] requirePassedVertex){
        List<Integer> required = new ArrayList<Integer>();
        for (int temp : requirePassedVertex) {
            required.add(temp);
        }
        Set<Map.Entry<Integer, String>> pathInfos = pathInfo.entrySet();
        for (Map.Entry<Integer, String> pathInfo : pathInfos) {
            if (required.contains(pathInfo.getKey()))
                System.out.println(pathInfo.getKey() + ":" + pathInfo.getValue());
        }
    }

    /**
     * 获取与node最近的子节点
     */
    private Node getShortestPath(Node node) {
        Node res = null;
        int minDis = Integer.MAX_VALUE;
        Map<Node, Integer> childs = node.getChild();
        for (Node child : childs.keySet()) {
            if (open.contains(child)) {
                int distance = childs.get(child);
                if (distance < minDis) {
                    minDis = distance;
                    res = child;
                }
            }
        }
        return res;
    }

















    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    private int[][] buildAdjacentMatrix(String[] edges, int edgeNumber, int vertexNumber) {

        int max = Integer.MAX_VALUE;
        int[][] adjacentMatrix = new int[vertexNumber][vertexNumber];

        for (int i = 0; i < adjacentMatrix.length; i++) {
            for (int j = 0; j < adjacentMatrix.length; j++) {
                if (i == j)
                    adjacentMatrix[i][j] = 0;
                else
                    adjacentMatrix[i][j] = max;
            }
        }
        for (String edge : edges) {
            String[] vertexes = edge.split(",");
            adjacentMatrix[Integer.parseInt(vertexes[1])][Integer.parseInt(vertexes[2])] = Integer.parseInt(vertexes[3]);
        }
        return adjacentMatrix;

    }
}
