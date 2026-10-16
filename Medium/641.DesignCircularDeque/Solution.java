class MyCircularDeque {
    int[] arr;
    int size;
    int f;
    int r;

    public MyCircularDeque(int k) {
        arr = new int[k];
        size = k;
        f = -1;
        r = -1;
    }

    public boolean insertFront(int value) {
        if (isFull()) return false;

        if (isEmpty()) {
            f = 0;
            r = 0;
        } else {
            f = (f - 1 + size) % size;
        }
        arr[f] = value;
        return true;
    }

    public boolean insertLast(int value) {
        if (isFull()) return false;

        if (isEmpty()) {
            f = 0;
            r = 0;
        } else {
            r = (r + 1) % size;
        }
        arr[r] = value;
        return true;
    }

    public boolean deleteFront() {
        if (isEmpty()) return false;

        if (f == r) {
            f = -1;
            r = -1;
        } else {
            f = (f + 1) % size;
        }
        return true;
    }

    public boolean deleteLast() {
        if (isEmpty()) return false;

        if (f == r) {
            f = -1;
            r = -1;
        } else {
            r = (r - 1 + size) % size;
        }
        return true;
    }

    public int getFront() {
        return isEmpty() ? -1 : arr[f];
    }

    public int getRear() {
        return isEmpty() ? -1 : arr[r];
    }

    public boolean isEmpty() {
        return f == -1 && r == -1;
    }

    public boolean isFull() {
        return (r + 1) % size == f;
    }
}
