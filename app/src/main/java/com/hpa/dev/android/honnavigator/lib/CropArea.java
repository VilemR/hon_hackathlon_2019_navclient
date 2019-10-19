package com.hpa.dev.android.honnavigator.lib;

import android.graphics.Bitmap;
import android.graphics.Color;

public class CropArea {
    int screenWidth = 0;
    int screenHeighth = 0;
    String poiName = null;

    public CropArea(int screenWidth, int screenHeighth, String poiName) {
        this.screenWidth = screenWidth;
        this.screenHeighth = screenHeighth;
        this.poiName = poiName;
    }

    public Bitmap crop(Bitmap mapLayoutBitmap) {
        //MapCoordinates poiPosition = new MapCoordinates(516, 306); //Copy B

        MapCoordinates poiPosition = getCoordinates(poiName);

        Bitmap resized = Bitmap.createScaledBitmap(mapLayoutBitmap, 1260, 740, false);
        float mapScaleMagnitude = 3.0f;

        for (int ix = -5; ix <= 6; ix++) {
            for (int iy = -5; iy <= 6; iy++) {
                resized.setPixel(poiPosition.x + ix, poiPosition.y + iy, Color.rgb(255, 0, 0));
            }
        }

        int mapWidth = resized.getWidth();
        int mapHeighth = resized.getHeight();

        int sizeTileHalf = (screenWidth > screenHeighth) ? screenHeighth : screenWidth;
        sizeTileHalf = (int) ((sizeTileHalf / mapScaleMagnitude) / 2);

        int deltaX = 0;
        int deltaY = 0;

        MapCoordinates from = new MapCoordinates(poiPosition.x - sizeTileHalf, poiPosition.y - sizeTileHalf);

        if (from.x < 0) {
            deltaX = from.x;
            from.x = 0;
        }
        if (from.y < 0) {
            deltaY = from.y;
            from.y = 0;
        }

        if ((from.x + (2 * sizeTileHalf)) > mapWidth) {
            deltaX = (from.x + (2 * sizeTileHalf)) - mapWidth;
            from.x = from.x - deltaX;
        }
        if ((from.y + (2 * sizeTileHalf)) > mapHeighth) {
            deltaY = (from.y + (2 * sizeTileHalf)) - mapHeighth;
            from.y = from.y - deltaY;
        }

        System.err.println("Map : widthxheight " + mapWidth + "x" + mapHeighth);
        System.err.println("Scr : widthxheight " + this.screenWidth + "x" + this.screenHeighth);
        System.err.println("Center :" + poiPosition.x + "." + poiPosition.y + " From " + from.x + "." + from.y + " -> " + " tile " + (sizeTileHalf * 2));

        Bitmap croppedBitmap = Bitmap.createBitmap(resized, from.x, from.y, sizeTileHalf * 2, sizeTileHalf * 2);
        return croppedBitmap;
    }

    private MapCoordinates getCoordinates(String poiName) {
        switch (poiName.toUpperCase()) {
            case "B2.2_COPYC":
                return new MapCoordinates(516, 306);
            case "A2.8_CR_PANDORA":
                return new MapCoordinates(237, 205);
            case "A2.8_CR_THALIA":
                return new MapCoordinates(256, 205);
            default:
                return new MapCoordinates(0, 0);
        }
    }

}

/* NOK 1080x1920
   Mapa 3780x2220   / original ale 1260x740
(x,y) :-
1) (0,0) is top left corner.
2) (maxX,0) is top right corner
3) (0,maxY) is bottom left corner
4) (maxX,maxY) is bottom right corner



        int oX = ((poiPosition.x - ((screenWidth   / 2) * mapScaleMagnitude)) < 0) ? 0 : (int) (poiPosition.x - ((screenWidth   / 2) * mapScaleMagnitude));
        int oY = ((poiPosition.y - ((screenHeighth / 2) * mapScaleMagnitude)) < 0) ? 0 : (int) (poiPosition.y - ((screenHeighth / 2) * mapScaleMagnitude));

        int oW = ((poiPosition.x + ((screenWidth   / 2) * mapScaleMagnitude)) > mapWidth  ) ? screenWidth : (int) (poiPosition.x + (screenWidth / (2 * mapScaleMagnitude)));
        int oH = ((poiPosition.y + ((screenHeighth / 2) * mapScaleMagnitude)) > mapHeighth) ? screenHeighth : (int) (poiPosition.y + (screenHeighth / (2 * mapScaleMagnitude)));

        System.err.println("Map : widthxheight " + mapWidth + "x" + mapHeighth );
        System.err.println("Scr : widthxheight " +  this.screenWidth  + "x" + this.screenHeighth  );
        System.err.println("Center :" + poiPosition.x + "." + poiPosition.y + " From " + oX + "." + oY + " -> " + " width " + oW + " Heighth " + oH);

        Bitmap croppedBitmap = Bitmap.createBitmap(resized, oX, oY, oW, oH);
        return croppedBitmap;

*/
