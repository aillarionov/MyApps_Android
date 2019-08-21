package ru.gastroafisha.MyApps.Utils;

/**
 * Created by alex on 28.02.2018.
 */

public class MapUtils {
    static double EQUATOR_LENGTH = 40075696;

    public static double getZoomForMetersWide (
            final double desiredMeters,
            final double mapWidth,
            final double latitude )
    {
        final double latitudinalAdjustment = Math.cos( Math.PI * latitude / 180.0 );

        final double arg = EQUATOR_LENGTH * mapWidth * latitudinalAdjustment / ( desiredMeters * 256.0 );

        return Math.log( arg ) / Math.log( 2.0 );
    }

}
