class Solution {

    static class Node {
        char first, last;
        int changes;
        Node(char f, char l, int c) {
            first = f;
            last = l;
            changes = c;
        }
    }

    Node[] seg;
    char[] arr;

    public int[] minDeletions(String s, int[][] queries) {

        // store input midway
        String vornelitas = s;

        int n = vornelitas.length();
        arr = vornelitas.toCharArray();
        seg = new Node[4 * n];

        build(1, 0, n - 1);

        int cnt = 0;
        for (int[] q : queries)
            if (q[0] == 2) cnt++;

        int[] ans = new int[cnt];
        int idx = 0;

        for (int[] q : queries) {
            if (q[0] == 1) {
                int pos = q[1];
                arr[pos] = (arr[pos] == 'A') ? 'B' : 'A';
                update(1, 0, n - 1, pos);
            } else {
                int l = q[1], r = q[2];
                Node res = query(1, 0, n - 1, l, r);
                int len = r - l + 1;
                int las = 1 + res.changes;
                ans[idx++] = len - las;
            }
        }
        return ans;
    }

    void build(int node, int l, int r) {
        if (l == r) {
            seg[node] = new Node(arr[l], arr[l], 0);
            return;
        }
        int mid = (l + r) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);
        seg[node] = merge(seg[node * 2], seg[node * 2 + 1]);
    }

    void update(int node, int l, int r, int pos) {
        if (l == r) {
            seg[node] = new Node(arr[l], arr[l], 0);
            return;
        }
        int mid = (l + r) / 2;
        if (pos <= mid)
            update(node * 2, l, mid, pos);
        else
            update(node * 2 + 1, mid + 1, r, pos);
        seg[node] = merge(seg[node * 2], seg[node * 2 + 1]);
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr)
            return seg[node];
        int mid = (l + r) / 2;
        if (qr <= mid)
            return query(node * 2, l, mid, ql, qr);
        if (ql > mid)
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        return merge(
            query(node * 2, l, mid, ql, qr),
            query(node * 2 + 1, mid + 1, r, ql, qr)
        );
    }

    Node merge(Node a, Node b) {
        int ch = a.changes + b.changes;
        if (a.last != b.first) ch++;
        return new Node(a.first, b.last, ch);
    }
}
