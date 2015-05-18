package com.iphotolocator.activity.imagefactory;

public class Solution
{
	public static class ListNode
	{

		int val;

		ListNode next;

		public ListNode(int value)
		{

			val = value;

			next = null;

		}

	}

	public static ListNode mergeList(ListNode l1, ListNode l2)
	{

		ListNode l1pre = l1;

		ListNode l2pre = l2;
		ListNode firstNode = null;

		if (l1.val <= l2.val)
		{
			firstNode = l1;
		}
		else
		{
			firstNode = l2;
		}

		while (l1 != null || l2 != null)
		{

			// compare value

			if (l1 == null)
			{

				// directly add l2 nodes in l1

				l1pre.next = l2;

				break;

			}

			if (l2 == null)
			{

				break;

			}

			splice(l1, l2, l1pre);

			l1pre = l1;

			l2pre = l2;

			l1 = l1.next;

			l2 = l2.next;
		}
		return firstNode;
	}

	public static void splice(ListNode l1, ListNode l2, ListNode l1pre)
	{

		if (l1.val <= l2.val)
		{

			ListNode temp = l1.next;

			l1.next = l2;

			l2.next = temp;

			l1 = l1.next;

		}

		else
		{

			ListNode temp = l1pre.next;

			l1pre.next = l2;

			l2.next = l1;

			l1 = l1pre.next;

		}

	}

	public static void main(String args[])
	{

		ListNode l2 = new ListNode(6);
		ListNode l1 = new ListNode(5);

		System.out.println(mergeList(l1, l2));

		System.out.println(mergeList(l1, l2).next);

		ListNode l3 = null;

		ListNode l4 = null;

		System.out.println(mergeList(l3, l4));

		System.out.println(mergeList(l3, l1));

	}

}
