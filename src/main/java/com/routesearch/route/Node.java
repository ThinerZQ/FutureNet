package com.routesearch.route;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Date: 2016/3/27
 * Time: 16:47
 * User: ThinerZQ
 * GitHub: <a>https://github.com/ThinerZQ</a>
 * Blog: <a>http://www.thinerzq.me</a>
 * Email: 601097836@qq.com
 */
public class Node {
    private int id;
    private Map<Node,Integer> child = new HashMap<Node, Integer>();

    public Node(int id) {
        this.id = id;
    }

    public Node() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Node, Integer> getChild() {
        return child;
    }

    public void setChild(Map<Node, Integer> child) {
        this.child = child;
    }
}
