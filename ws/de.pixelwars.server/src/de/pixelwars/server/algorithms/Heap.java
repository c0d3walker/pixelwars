package de.pixelwars.server.algorithms;

import java.util.Comparator;

public class Heap<T> {

	private Object[] _heap;
	private int _size;
	private Comparator<T> _comparator;

	public Heap(Comparator<T> comparator) {
		_heap = new Object[300];
		_size = 0;
		_comparator = comparator;
	}

	public void add(T element) {
		_size++;
		_heap[_size] = element;
		propagateUpwards(_size);
	}

	@SuppressWarnings("unchecked")
	private void propagateUpwards(int position) {
		var parentPosition = getParent(position);
		if (parentPosition >= 1) {
			var parent = _heap[parentPosition];
			var child = _heap[position];
			var result = _comparator.compare((T) parent, (T) child);
			if (result > 0) {
				swap(parentPosition, position);
				propagateUpwards(position);
			}
		}
	}

	private void swap(int p0, int p1) {
		var temp = _heap[p0];
		_heap[p0] = _heap[p1];
		_heap[p1] = temp;
	}

	private int getParent(int position) {
		if (position % 2 == 0) {
			return position / 2;
		} else {
			return (position - 1) / 2;
		}
	}

	public T peek() {

		return size() == 0 ? null : get(1);
	}

	public T poll() {
		if (size() == 0) {
			return null;
		}
		var head = get(1);
		_heap[1] = _heap[_size];
		_size--;
		propagateDownwards(1);
		return head;
	}

	public int size() {
		return _size;
	}

	@SuppressWarnings("unchecked")
	private void propagateDownwards(int position) {
		var left = position * 2;
		var right = position * 2 + 1;

		if (right >= size()) {
			if (left <= size()) {
				var hasCorrectStructure = _comparator.compare((T) _heap[position], (T) _heap[left]) <= 0;
				if (!hasCorrectStructure) {
					swap(left, position);
					propagateDownwards(left);
				}
			}
		} else {
			var smallerEntryIndex = _comparator.compare((T) _heap[left], (T) _heap[right]) < 0 ? left : right;
			if (_comparator.compare((T) _heap[position], (T) _heap[smallerEntryIndex]) > 0) {
				swap(smallerEntryIndex, position);
				propagateDownwards(smallerEntryIndex);
			}
		}

//		if (left <= size() && right <= size() && _comparator.compare((T) _heap[position], (T) _heap[left]) > 0) {
//			swap(position, left);
//			propagateDownwards(left);
//		} else if (right <= size() && _comparator.compare((T) _heap[position], (T) _heap[right]) > 0) {
//			swap(position, right);
//			propagateDownwards(right);
//		}
	}

	public int indexOf(T element) {
		var i = 0;
		Object entry;
		do {
			i++;
			entry = _heap[i];
		} while ((!entry.equals(element)) && i <= size());
		return entry.equals(element) ? i : -1;
	}

	public void updateEntry(T element, int index) {
		_heap[index] = element;
		swap(size(), index);
		propagateUpwards(size());

	}

	@SuppressWarnings("unchecked")
	public T get(int index) {
		return (T) _heap[index];
	}

	public boolean isEmpty() {
		return size() == 0;
	}
}
