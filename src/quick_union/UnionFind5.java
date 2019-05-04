package quick_union;

/**
 * @Author: tangkeyon@gmail.com
 * @Date: 2019/5/4 22:03
 * @Version 5.0
 */
/*
  路径压缩对并查集优化，是rank优化的升级版本，
  在查找插入节点的根节点的过程中进行路径的压缩
 */
public class UnionFind5  implements UF {
    //    数组中的索引表示各个元素，数组索引对应的值保存着该元素的父节点
    private int[]parent;
    //    用于保存树的层数
    private int[]rank;

    public UnionFind5(int size){
        parent=new int[size];
        rank=new int[size];
//        初始化数组，每个索引保存和索引相同的元素，表示每个元素的父节点指向自己
        for(int i=0;i<size;i++){
            parent[i]=i;
            rank[i]=1;
        }

    }

    /**
     * 查找元素p对应的集合，即根节点的值
     * @param p
     * @return
     */
    private int find(int p){
        if(p<0 || p>=parent.length)
            throw new IllegalArgumentException("wrong index");
        while (p!=parent[p]){
//            当前节点指向的不是自身,就把当前节点的爷爷节点的值赋给当前节点，跳了一个节点，做到了路径的压缩
            p = parent[parent[p]];
        }
        return p;

    }
    @Override
    public boolean isConnected(int p, int q) {
        return find(p)==find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        int pRoot=find(p);
        int qRoot=find(q);
        if(pRoot==qRoot)
            return;

//        进行合并的时候让层数低的树合并到层数高的树下
        if(rank[pRoot] > rank[qRoot]){
            pRoot=parent[qRoot];
//            低层树合并到高层的树下其层数不会发生改变
        }else if(rank[pRoot]<rank[qRoot]){
            qRoot=parent[pRoot];
        }else{
//            若两棵树的高度相同，任意合并即可，但要维护层数
            qRoot=parent[pRoot];
            rank[qRoot]++;
        }

    }

    @Override
    public int getSize() {
        return parent.length;
    }
}
