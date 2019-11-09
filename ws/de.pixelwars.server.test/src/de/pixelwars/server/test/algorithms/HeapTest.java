package de.pixelwars.server.test.algorithms;

import de.pixelwars.server.algorithms.Heap;

public class HeapTest {

	public static void main(String[] args) {
		testHeapBreathing();
	}

	private static void testHeapBreathing() {
		var heap = new Heap<Integer>((v0, v1) -> {
			return v0 - v1;
		});
		heap.add(6);
		heap.add(9);
		heap.add(2);
		heap.add(9);
		heap.add(11);

		chk(heap.size() == 5);
		var peek = heap.peek();
		chk(heap.size() == 5);
		chk(peek == 2);
		chk(heap.poll() == 2);
		chk(heap.poll() == 6);
		chk(heap.size() == 3);
		chk(heap.poll() == 9);
		chk(heap.poll() == 9);
		chk(heap.poll() == 11);
		chk(heap.size() == 0);
		chk(heap.isEmpty());
		chk(heap.poll() == null);
	}

	private static void chk(boolean result) {
		if (!result)
			throw new IllegalStateException();
	}
}
