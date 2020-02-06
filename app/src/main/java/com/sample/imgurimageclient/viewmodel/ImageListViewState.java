package com.sample.imgurimageclient.viewmodel;

/**
 * State of API Used to display loaded and for unit testing
 */
public class ImageListViewState {

    public enum State{
        LOADING(0), SUCCESS(1),FAILED(-1);
        public int value;
        State(int val) {
            value = val;
        }
    }

    protected Throwable error;
    protected int currentState;



    private ImageListViewState(int currentState, Throwable error){
        this.error = error;
        this.currentState = currentState;
    }
    public static ImageListViewState ERROR_STATE = new ImageListViewState( State.FAILED.value, new Throwable());
    public static ImageListViewState LOADING_STATE = new ImageListViewState(State.LOADING.value, null);
    public static ImageListViewState SUCCESS_STATE = new ImageListViewState(State.SUCCESS.value, null);



}
