package com.bochynski.example.lab2;

/**
 * Created by Karol on 2017-04-22.
 */
public interface ItemTouchHelperAdapter {

    /*
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and not at the end of a "drop" event.
     */
    void onItemMove(int fromPosition, int toPosition);

    /*
    * Called when an item has been dismissed by a swipe.
    */
    void onItemDismiss(int position);
}