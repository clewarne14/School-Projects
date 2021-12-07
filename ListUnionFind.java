import java.util.ArrayList;

public class ListUnionFind{
    public class Node{
        public Header head;
        public int x;
        public Node next;
        public Node(Header h, int x1, Node n){
            this.head = h;
            this.x = x1;
            this.next = n;
        }
        public String toString(){
            if(next == null){
                return "{ head : " + Integer.toString(head.first.x) +  ", x : " + Integer.toString(x) + ", next : null }";
            }
            return "{ head : " + Integer.toString(head.first.x) + ", x : " + Integer.toString(x) + ", next : " + Integer.toString(next.x) + " }";
        }
    }
    public class Header{
        public Node first;
        public int weight;
        public Node tail;
        public Header(Node f, int w, Node t){
            this.first = f;
            this.weight = w;
            this.tail = t;
        }
        public String toString(){
            return "{ first : " + Integer.toString(first.x) +  ", weight : " + Integer.toString(weight) + ", tail : " + Integer.toString(tail.x) + " }";
        }
    }
    public int size;
    public Node[] element;

    public ListUnionFind(int n){
        makeSet(n);
    }

    /**
	 * This method will initialize the Union Find data structure and create the n singleton 
	 * sets {0}, {1}, ..., {n-1}. This method must be called before any find() 
	 * or union() method is called.
	 * NOTE: We could have this done by the constructor, UnionFind(n).
	 * 
	 * @param n the size of the Universal Set
	 */
    public void makeSet(int n){
        this.element = new Node[n];
        for(int i = 0; i < n; i++){
            Header h = new Header(null,1,null);
            Node temp = new Node(h,i,null);
            h.first = temp;
            h.tail = temp;
            element[i] = temp;
        }
    }

    /**
	 * If x is in set S1 and y is in set S2, this method will create S1 U S2. 
	 * NOTE: If S1=S2, there is no change to the data structure.
	 * @param x an element in the Universal Set U.
	 * @param y an element in the Universal Set U.
	 */
    public void union(int x, int y){
        Header h;
        Node temp;
        if((find(x)==find(y))){
            return;
        }
        if(element[x].head.weight > element[y].head.weight){
            h = element[x].head;
            temp = h.first;
            h.tail.next = element[y].head.first;
            while(temp != null){
                temp.head = h;
                temp = temp.next;
            }
        }
        else if(element[y].head.weight > element[x].head.weight){
            h = element[y].head;
            temp = h.first;
            h.tail.next = element[x].head.first;
            while(temp != null){
                temp.head = h;
                temp = temp.next;
            }
        }
        else{
            h = element[y].head;
            temp = h.first;
            h.tail.next = element[x].head.first;
            while(temp != null){
                temp.head = h;
                temp = temp.next;
            }
            h.weight++;
        }
    }

    /**
	 * The find(x) method returns the set representative for x. 
	 * @param x an element in the Universal Set U.
	 * @return the set representative for the set x is in.
	 */
    public int find(int x){
        return element[x].head.first.x;
    }

    public String toString(){
        String output = "";
        ArrayList<Header> comp = new ArrayList<Header>();
        for(int i = 0; i < element.length; i++){
            if(!comp.contains(element[i].head)){
                Node temp = element[i].head.first;
                output = output + "{ ";
                while(temp != null){
                    output = output + temp.toString();
                    temp = temp.next;
                    output = output + ", ";
                }
                output = output.substring(0, output.length()-2);
                output = output + " }";
                output = output + "\n";
                comp.add(element[i].head);
            }
        }
        return output;
    }


    public static void main(String[] args){
        ListUnionFind l = new ListUnionFind(4);
        System.out.println(l.toString());
        l.union(0, 1);
        System.out.println(l.toString());
        System.out.println(l.find(0));
    }
}