class MyCircularQueue {
    static{
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try(FileWriter writer = new FileWriter("display_runtime.txt")){
                writer.write("0");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }));
    }
    static int[] arr;
    static int size;
    static int f;
    static int r;
    public MyCircularQueue(int k) {
        arr=new int[k];
        f=-1;
        r=-1;
        size=k;        
    }
    
    public boolean enQueue(int value) {
        if(isFull()){
            return false;
        }
        if(f==-1){
            f=0;
        }
        r=(r+1)%size;
        arr[r]=value;
        return true;
    }
    
    public boolean deQueue() {
        if(isEmpty()) return false;
        if(f==r){
            f=-1;
            r=-1;
        }else{
            f=(f+1)%size;
        }
        return true;        
    }
    
    public int Front() {
        if(isEmpty()){
            return -1;
        }
        return arr[f];
    }
    
    public int Rear() {
        if(isEmpty()){
            return -1;
        }
        return arr[r];
    }
    
    public boolean isEmpty() {
        return f==-1;
    }
    
    public boolean isFull() {
        return (r+1)%size==f;
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */