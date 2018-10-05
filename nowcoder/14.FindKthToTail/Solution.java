public class Solution {
	/*
	 * 使用快慢指针进行遍历求解 对情况多样性需要考虑，例如k==0，头结点为空，链表长度不足k
	 */
	public ListNode FindKthToTail(ListNode head, int k) {
		if (head == null || k <= 0)
			return null;
		ListNode fast = head, slow = head;
		for (int i = 0; i < k - 1; i++) {
			if (fast.next != null)
				fast = fast.next;
			else
				return null;
		}
		while (fast.next != null) {
			fast = fast.next;
			slow = slow.next;
		}
		return slow;
	}
}