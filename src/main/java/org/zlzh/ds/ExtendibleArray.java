/*
 * File: ExtendibleArray.java
 * Author: Keith Schwarz (htiek@cs.stanford.edu)
 */
package org.zlzh.ds;

import java.util.AbstractList;

/************************************************************************
 *<pre>
 * An implementation of the List interface backed by a variant of the
 * ArrayList.  Internally, ArrayList works by maintaining a dynamic
 * array of elements that doubles in size whenever the capacity is
 * exhausted and more space must become available.  This gives insert 
 * an amortized cost O(1), but any individual operation may take O(n)
 * time to complete.  In some situations, this is unacceptably slow,
 * either from a practical or a theoretical perspective.
 *
 * The implementation used in this ExtendibleArray class is similar
 * to the ArrayList, except with a worst-case guarantee of O(1) for 
 * lookup, size, add-to-end, and remove-from-end.  Addition or
 * removal in the middle of the list still takes O(n).
 *
 * The main difference in the implementation is that while the ArrayList
 * mathematically amortizes the cost of a copy across the elements
 * (by making each insert contribute to future resize operations),
 * the ExtendibleArray class amortizes the cost explicitly by copying
 * the elements from the old array into the new one lazily as future
 * elements are inserted.  For example, suppose that we have an array
 * of size four and that we need to insert the element 5.  Initially
 * we have this setup:
 *
 * [1] [2] [3] [4]
 *
 * Next, we allocate a new array twice the size of the old one, but
 * we still keep the old one around, as seen here:
 *
 * [1] [2] [3] [4]
 * [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
 *
 * Next, we insert the element five into the new array.  We also copy
 * the element four over from the previous array, as seen here:
 *
 * [1] [2] [3] [ ]
 * [ ] [ ] [ ] [4] [5] [ ] [ ] [ ]
 *
 * If we then insert a new element, we append it to the new array and
 * copy yet another element from the old array to the new:
 *
 * [1] [2] [ ] [ ]
 * [ ] [ ] [3] [4] [5] [6] [ ] [ ]
 *
 * If we keep this up over time, eventually the new array becomes filled:
 *
 * [ ] [ ] [ ] [ ]
 * [1] [2] [3] [4] [5] [6] [7] [8]
 *
 * Now, when we need to double the size of the array again, we can
 * discard the older array, use the bigger array as the old array, and
 * put a new array up in front:
 *
 * [1] [2] [3] [4] [5] [6] [7] [ ]
 * [ ] [ ] [ ] [ ] [ ] [ ] [ ] [8] [9] [ ] [ ] [ ] [ ] [ ] [ ] [ ]
 *
 * Internally, this structure maintains the two arrays, along with two
 * pointers, "end," which points to the first unused spot in the new
 * array, and "shadow," which points to the last element of the old array
 * that hasn't yet been transferred:
 *
 * [1] [2] [ ] [ ]
 * [ ] [ ] [3] [4] [5] [6] [ ] [ ]
 *      ^                   ^
 *      |                   |
 *    shadow               end
 *
 * Whenever we insert an element, we add it to the spot marked with "end,"
 * move the element marked by "shadow" from the old array to the new.
 * We then march each pointer outward one step.
 *
 * When removing an element, we run this process backward, moving the
 * appropriate pointers inward, then copying an element back to the
 * old array if necessary.  However, at some point we may find that
 * we've removed every element from the new array.  If this happens,
 * we'll find that the shadow and end pointers cross.  We can then
 * move the old array to the new array and then put a dummy array
 * holding all null behind it.  For example, after removing the
 * last element in this setup:
 *
 * [1] [2] [3] [ ]
 * [ ] [ ] [ ] [4] [5] [ ] [ ] [ ]
 *          ^           ^
 *          |           |
 *        shadow       end
 *
 * We would arrive at this configuration:
 *
 *        [ ] [ ]
 *        [1] [2] [3] [4]
 *      ^                 ^
 *      |                 |
 *   shadow              end
 *
 * There is one major edge case to consider - what happens when we
 * first create the structure?  In that case, we don't have an old
 * array, and so none of this logic will make sense.  We'll address
 * this by initially creating the new array as a singleton array
 * holding null.  This means that our array will carry around a
 * dummy null element the whole time, but the total overhead only
 * ends up being O(1).
 * </pre>
 */
public final class ExtendibleArray<T> extends AbstractList<T> {
    /* Initially, the old array is null and the new array is a singleton
     * that holds null.
     */
    T[] mOld = null;
    @SuppressWarnings("unchecked")
	T[] mNew = (T[]) new Object[1];

    /* The shadow and end pointers need to point to the next write location
     * and the spot right before the last element that was pulled up.
     * This is shown here:
     *
     *         [X]
     *        ^   ^
     *        |   |
     *   shadow   end
     *
     * This means that mShadow is -1 and mEnd is +1.  On the very first
     * insert, this will get converted to
     *
     *       [ ]
     *       [X] [*]
     *     ^         ^
     *     |         |
     *   shadow     end
     *
     * And from here things can proceed as usual.
     */
    int mShadow = -1;
    int mEnd = +1;

    /**
     * Adds a new element to the ExtendibleArray, possibly allocating a new
     * array if necessary.
     *
     * @param elem The element to add.
     * @return true
     */
    @SuppressWarnings("unchecked")
	@Override 
    public boolean add(T elem) {
        /* First, check whether we need to do a reallocation.  This is true
         * if the end pointer is past the end of the new array.
         */
        if (mEnd == mNew.length) {
            /* Drop the old buffer.  The new buffer becomes the old
             * buffer and a fresh buffer is made.
             */
            mOld = mNew;
            mNew = (T[]) new Object[mNew.length * 2];

            /* The end position stays put, since it's telling us where the
             * next write location is.  The shadow pointer now is right
             * before the end pointer.
             */
            mShadow = mEnd - 1;
        }

        /* Write the element in its proper place. */
        mNew[mEnd] = elem;

        /* Pull from the old array into the new, then null out the old spot
         * to place nicely with the garbage collector.
         */
        mNew[mShadow] = mOld[mShadow];

        /* Move the two pointers outward. */
        ++mEnd; --mShadow;

        /* By contract, must return true. */
        return true;
    }

    /**
     * Returns the element in the ExtendibleArray at the specified position,
     * throwing an IndexOutOfBoundsException if there is no element at that
     * position.
     *
     * @param index The index to query.
     * @return The element at that index.
     * @throws IndexOutOfBoundsException If index is invalid.
     */
    @Override
    public T get(int index) {
        /* Check that the index is valid. */
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size());

        /* Now, there are two places we might need to look.  If index is
         * at or below the shadow point, we read from the old array.
         * Otherwise, read from the new array.  However, we have to be
         * careful, because the raw index is not the true index into the
         * structure because of the dummy element, so we bump the user
         * index by one first.
         */
        return (index + 1 <= mShadow) ? mOld[index + 1] : mNew[index + 1];
    }

    /**
     * Returns the size of the ExtendibleArray.
     *
     * @return The size of the ExtendibleArray.
     */
    @Override
    public int size() {
        /* The size of the array is marked by the mEnd pointer.  However,
         * because of the dummy element, we need to subtract one from that
         * value.
         */
        return mEnd - 1;
    }

    /**
     * Sets the value of the element at the particular index to be the
     * client-specified value.  If the index is out of bounds, an
     * IndexOutOfBoundsException will be thrown.
     *
     * @param index The index of the element to set.
     * @param value The new value to write.
     * @return The value of that element before the write.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    @Override public
    T set(int index, T value) {
        /* Cache the old value.  This call to get also checks that the index
         * is in bounds.
         */
        T originalValue = get(index);

        /* Write to the appropriate array. */
        if (index + 1 <= mShadow)
            mOld[index + 1] = value;
        else
            mNew[index + 1] = value;

        /* Return the old value. */
        return originalValue;
    }

    /**
     * Removes the element in the specified position from the list, throwing
     * an IndexOutOfBoundsException if the index is invalid.
     *
     * @param index The index of the element to remove.
     * @return The value of the removed element.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    @SuppressWarnings("unchecked")
	@Override public
    T remove(int index) {
        /* Grab the value of the element we're removing; this also verifies
         * the index.
         */
        T result = get(index);

        /* Removing an element from the array is tricky because we have to
         * maintain the invariant that there are an even number of elements
         * in the new array and that they are in the central elements.
         * There are two cases we need to consider.
         * 
         * 1. The element to remove is in the new array.  We need to remove
         *    two elements from this array, one that actually got deleted
         *    and one that needs to be moved back to the old array.  We'll
         *    do a standard shuffle-down algorithm to cover up the element
         *    that was deleted, then we'll move the oldest element down.
         * 2. The element to remove is in the old array.  We need to move
         *    two elements from the new array down into the old one.  To
         *    do this, we'll do the standard array shuffle-down algorithm
         *    on the old array, then will copy the first two elements of
         *    the new array into the old.  Finally, we shuffle the remaining
         *    elements of the new array down one spot.
         *
         * After doing this step, we'll need to determine whether or not
         * to do a rebalance by tossing out the new list and swapping the
         * old list forward.
         */
        if (index + 1 > mShadow) { // In the new array
            /* Scoot the elements past this element down on top of the
             * element itself.  The number of elements to copy is given
             * by the number of elements between mEnd - 1 (the last element)
             * and index + 1 (the element to delete)
             */
            System.arraycopy(mNew, index + 2, mNew, index + 1,
                             (mEnd - 1) - (index + 1));

            /* Move the element before the shadow down. */
            mOld[mShadow + 1] = mNew[mShadow + 1];

            /* To be nice to the garbage collector, clear the endpoints. */
            mNew[mEnd - 1] = null;
            mNew[mShadow + 1] = null;

            /* Pull the shadow and endpoint closer together. */
            ++mShadow; --mEnd;
        } else { // In old array
            /* Shuffle down the elements in the old array to cover the
             * element that was removed.  The math is similar to above.
             */
            System.arraycopy(mOld, index + 2, mOld, index + 1,
                             mShadow - (index + 1));

            /* Move the last two elements of the new array into the old one.
             * Recall that mShadow points to the last unshadowed element in
             * the old array, and so we'll copy from one spot past there
             * in the new array.
             */
            for (int i = 0; i < 2; ++i) {
                mOld[mShadow + i] = mNew[mShadow + 1 + i];
                mNew[mShadow + 1 + i] = null; // Place nice with the GC
            }

            /* The new shadow point is one step past where it once was,
             * since we just pulled two elements down, but lost an
             * old element.
             */
            ++mShadow;

            /* Shuffle the elements of the new array down one position.
             * The beginning position is one past the shadow, as it always
             * is.
             */
            System.arraycopy(mNew, mShadow + 2, mNew, mShadow + 1,
                             mEnd - mShadow - 1);

            /* Clear the element that just got moved. */
            mNew[mEnd - 1] = null;

            /* Back up the end position, since we just lost an element. */
            --mEnd;
        }

        /* Finally, see if we just emptied the new array.  This happens if
         * the end pointer is one step past the shadow pointer.
         */
        if (mEnd == mShadow + 1) {
            /* Drop the new array and promote the old array to new. */
            mNew = mOld;

            /* Make a blank array for new elements. */
            mOld = (T[]) new Object[mNew.length / 2];

            /* Move the end and shadow pointers off the ends of the array. */
            mShadow = -1;
            mEnd = mNew.length;
        }

        return result;
    }

    /**
     * Adds the specfied element to the ExtendibleArray at the specified
     * position, increasing the size by one.
     *
     * @param index The index before which to insert.
     * @param elem The value of the element to insert.
     * @throws IndexOutOfBoundsException If index is invalid.
     */
    @Override
    public void add(int index, T elem) {
        /* Bounds-check. */
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size());

        /* Begin by throwing the element on the end, which will do all of the
         * tricky balancing work.
         */
        add(elem);

        /* Now, we need to put the element in its proper position.  This 
         * requires us to check whether the element is in the new or old
         * array.  If it's in the new array, we just scoot everything
         * down and put it in its proper place.  Otherwise, we have to
         * scoot the whole new array down a spot, then push the old array
         * elements over, then drop the new element in.
         */
        if (index + 1 > mShadow) { // New array
            /* The number of elements to copy is the number of old elements
             * after the insert point, which is given by 
             * (mEnd - 2) - (index + 1)
             */
            System.arraycopy(mNew, index + 1,
                             mNew, index + 2,
                             (mEnd - 2) - (index + 1));
            mNew[index + 1] = elem;
        } else { // Old array
            /* Shuffle all the new elements down one spot. */
            System.arraycopy(mNew, mShadow + 1,
                             mNew, mShadow + 2,
                             (mEnd - 2) - (mShadow + 1));

            /* Shuffle all the old elements down one spot. */
            System.arraycopy(mOld, index + 1,
                             mOld, index + 2,
                             (mShadow - (index + 1)) + 1);

            /* Drop the element in its place. */
            mOld[index + 1] = elem;

            /* Promote last element of the list.  It will be at position
             * shadow + 1, since we just pushed past the end of the shadow.
             */
            mNew[mShadow + 1] = mOld[mShadow + 1];
            mOld[mShadow + 1] = null;
        }
    }
}
