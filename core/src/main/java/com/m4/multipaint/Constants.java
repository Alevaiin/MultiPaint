package com.m4.multipaint;

public class Constants
{
    private Constants()
    {
    }

    //BRUSH_CONSTANTS
    public final static int DEFAULT_BRUSH_SIZE = 2;
    public final static int DELTA_BRUSH_SIZE = 2;
    public final static int MIN_BRUSH_SIZE = 1;
    public final static int MAX_BRUSH_SIZE = 25;

    //UI
    public final static String UI_SKIN_FILE_NAME = "uiskin.json";
    public final static int BUTTON_WIDTH_BIG = 120;
    public final static int BUTTON_WIDTH_NORMAL = 80;
    public final static int BUTTON_HEIGHT_NORMAL = 40;
    public final static int BUTTON_PADDING = 5;

    public final static int SCREEN_RESOLUTION_W = 1280;
    public final static int SCREEN_RESOLUTION_H = 720;

    //DEFAULT VALUES
    public final static String IP_VALIDATION_REGEX = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    public final static int MAX_PORT = 65535;
    public final static int MIN_PORT = 1;
    public final static String DEFAULT_IP = "192.168.1.37";
    public final static String DEFAULT_PORT = "6666";
    public final static String DEFAULT_USER_NAME = "Player 1";
}
