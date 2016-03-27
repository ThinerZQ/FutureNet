/**
 * 实现代码文件
 *
 * @author XXX
 * @version V1.0
 * @since 2016-3-4
 */
package com.routesearch.route;

import java.util.*;

public final class Route {
    /**
     * 你需要完成功能的入口
     *
     * @author XXX
     * @version V1
     * @since 2016-3-4
     */
    public static String searchRoute(String graphContent, String condition) {

        String[] conditions = condition.split(",");
        String[] edges = graphContent.split("\n");
        int edgeNumber = edges.length;
        int vertexNumber = getVertexNumber(edges);
        int origin = Integer.parseInt(conditions[0]);
        int destination = Integer.parseInt(conditions[1]);
        String[] requirePathString = conditions[2].split("\\|");
        int[] requirePassedVertex = new int[requirePathString.length];
        //int dist[] = new int[vertexNumber];
        //int prev[] = new int[vertexNumber];
        // int[][] adjacentMatrix = buildAdjacentMatrix(edges, edgeNumber, vertexNumber);
        for (int i = 0; i < requirePassedVertex.length; i++) {
            if (i== requirePassedVertex.length-1){
                requirePathString[i]=requirePathString[i].substring(0,requirePathString[i].length()-1);
            }
            requirePassedVertex[i] = Integer.parseInt(requirePathString[i]);
        }


        Dijkstra dijkstra = new Dijkstra(edges,edgeNumber,vertexNumber,origin);
        dijkstra.computePath(dijkstra.getOrigin());
        dijkstra.printPathInfo(requirePassedVertex);





        return "hello world!";
    }


    //dijkstra算法
    public static void dijkstra(int origin, int[][] a, int dist[], int prev[]) {

        int n = dist.length;

        //s[]:存储已经找到最短路径的顶点，false为未求得
        boolean[] s = new boolean[n];

        for (int i = 0; i < n; i++) {
            //初始化dist[]数组
            dist[i] = a[origin][i];
           /*
            * prve[]数组存储源点到顶点vi之间的最短路径上该顶点的前驱顶点,
            * 若从源点到顶点vi之间无法到达，则前驱顶点为-1
            */
            if (dist[i] < Integer.MAX_VALUE)
                prev[i] = origin;
            else
                prev[i] = -1;
        }

        dist[origin] = 0;   //初始化v0源点属于s集
        s[origin] = true;   //表示v0源点已经求得最短路径
        for (int i = 0; i < n; i++) {
            int temp = Integer.MAX_VALUE; //temp暂存v0源点到vi顶点的最短路径
            int u = origin;
            for (int j = 0; j < n; j++) {
                if ((!s[j]) && dist[j] < temp) {  //顶点vi不属于s集当前顶点不属于s集(未求得最短路径)并且距离v0更近
                    u = j;           //更新当前源点,当前vi作为下一个路径的源点
                    temp = dist[j];       //更新当前最短路径
                }
            }
            s[u] = true;          //顶点vi进s集
            //更新当前最短路径以及路径长度
            for (int j = 0; j < n; j++) {
                if ((!s[j]) && a[u][j] < Integer.MAX_VALUE) {   //当前顶点不属于s集(未求得最短路径)并且当前顶点有前驱顶点
                    int newDist = dist[u] + a[u][j];        //累加更新最短路径
                    if (newDist < dist[j]) {
                        dist[j] = newDist;             //更新后的最短路径
                        prev[j] = u;               //当前顶点加入前驱顶点集
                    }
                }
            }
        }
    }


    public static void savePath(int origin, int[] dist, int[] prev,int[] requirePassedVertex,Map<Integer,ArrayList<String>> map) {
        List<Integer> required = new ArrayList<Integer>();
        for (int temp : requirePassedVertex) {
            required.add(temp);
        }

        for (int i = 0; i < dist.length; i++) {
            //当前顶点已求得最短路径并且当前顶点不等于源点
            if (dist[i] < Integer.MAX_VALUE && i != origin && required.contains(i)) {
                StringBuilder stringBuilder = new StringBuilder();
                System.out.print("v" + i + "<--");
                stringBuilder.append(i);
                int next = prev[i];    //设置当前顶点的前驱顶点
                while (next != origin) {  //若前驱顶点不为一个，循环求得剩余前驱顶点
                    System.out.print("v" + next + "<--");
                    stringBuilder.append(next);
                    next = prev[next];
                }
                stringBuilder.append(origin);
                System.out.println("v" + origin + ":" + dist[i]);
                ArrayList<String> list = new ArrayList<String>();
                list.add(stringBuilder.toString());
                list.add(dist[i] +"");
                map.put(i,list);
                System.out.println(stringBuilder.toString());
            }

        }
    }



    /**
     *
     * @param origin 源节点
     * @param dist 最短路径数组
     * @param prev 前驱节点数组
     */
    public static void outPath(int origin, int[] dist, int[] prev) {
        for (int i = 0; i < dist.length; i++) {
            //当前顶点已求得最短路径并且当前顶点不等于源点
            if (dist[i] < Integer.MAX_VALUE && i != origin) {
                System.out.print("v" + i + "<--");
                int next = prev[i];    //设置当前顶点的前驱顶点
                while (next != origin) {  //若前驱顶点不为一个，循环求得剩余前驱顶点
                    System.out.print("v" + next + "<--");
                    next = prev[next];
                }
                System.out.println("v" + origin + ":" + dist[i]);
            }
            //当前顶点未求得最短路径的处理方法
            else if (i != origin)
                System.out.println("v" + i + "<--" + "v" + origin + ":no path");
        }
    }



    private static int getVertexNumber(String[] edges) {
        Set<String> set = new HashSet<String>();
        for (String edge : edges) {
            String[] vertexs = edge.split(",");
            set.add(vertexs[1]);
            set.add(vertexs[2]);
        }
        return set.size();
    }


}