import java.util.Arrays;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA
 * Date: 2016/3/27
 * Time: 10:57
 * User: ThinerZQ
 * GitHub: <a>https://github.com/ThinerZQ</a>
 * Blog: <a>http://www.thinerzq.me</a>
 * Email: 601097836@qq.com
 */
public class DJ {
    /**
     * @param args
     */
    /*
     * 本题是求从0点到所有点的最短路径
     * 每一轮"Find shrotest"都是再找距离0点最近的点v，顾可以知道暂时从0到v的最短距离就是dist[v]，因为如果还有更近的距离，那么v就不是距离0最近的点
     * 到找到最近点v后，都要以v为过度点，来比较0->v->j和原来记录的路径哪个更近，从而以刷新dist[]
     * 当假设除了0点其他，都当过v点的所有情况后，dist[j]数组保留下来的就是从0到j的最短路径
     */
    final static int MAXN = 100;
    final static int BigNum = 10000000;
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int[][] graph = new int[MAXN][MAXN];//The Adjacency matrix of the graph
        int[] dist = new int[MAXN];//The shortest distence between 0 and N
        boolean[] vis= new boolean[MAXN];//Sign the point which is visited
        int n,m;//n stands for theamount of positions;m stands for the path
        n = scan.nextInt();
        m = scan.nextInt();
        Arrays.fill(vis, false);
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                if(i==j)
                    graph[i][j] = 0;
                else
                    graph[i][j] = BigNum;
        int pos1,pos2;
        for(int i=0;i<m;i++)    {//Set the date
            pos1 = scan.nextInt();
            pos2 = scan.nextInt();
            graph[pos1][pos2] = scan.nextInt();
        }
        for(int i=0;i<n;i++)    //Set the dist[]
            dist[i] = graph[0][i];
        vis[0] = true;int min,v = 0;
        for(int i=0;i<n-1;i++)    {//Check n-1 times
            min = BigNum;
            for(int j=0;j<n;j++)    {//Find shortest
                if(vis[j]!= true && dist[j]<min)    {//If the point is not visited and the distence between 0 and j is smallest mark the point j
                    min = dist[j];
                    v = j;
                }
                vis[v] = true;        //Sign the point v to be visited
            }
            for(int j=0;j<n;j++)    {//Refresh the dist[]
                if(vis[j] != true && dist[j]>dist[v]+graph[v][j])    {//when distence is shorter when pass the point v refresh the dist
                    dist[j] = dist[v] + graph[v][j];
                }
            }
        }
        for(int i=0;i<n;i++)    {
            System.out.println("0->"+i+":"+dist[i]);
        }
    }
}