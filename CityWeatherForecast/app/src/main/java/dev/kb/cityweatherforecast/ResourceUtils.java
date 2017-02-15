package dev.kb.cityweatherforecast;

/**
 * Created by Karol on 2017-02-15.
 */

public class ResourceUtils {
    public static int getIconResourceFromWeatherCode(int weatherCode) {
        switch (weatherCode / 100) {
            case 8:
                return getIconResource_8XX(weatherCode);
            case 6:
                return R.drawable.icon_6xx;
            case 5:
                return getIconResource_5XX(weatherCode);
            case 2:
                return R.drawable.icon_2xx;
            default:
                //TODO create default icon
                return R.drawable.icon_800;
        }
    }

    private static int getIconResource_8XX(int weatherCode) {
        switch (weatherCode) {
            case 800:
                return R.drawable.icon_800;
            case 801:
                return R.drawable.icon_801;
            case 802:
                return R.drawable.icon_802;
            case 803:
            case 804:
                return R.drawable.icon_803_804;
            default:
                //TODO create default icon
                return R.drawable.icon_800;
        }
    }

    private static int getIconResource_5XX(int weatherCode) {
        switch (weatherCode) {
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
                return R.drawable.icon_500_to_504;
            default:
                return R.drawable.icon_520plus;
        }
    }

    /* get relevant background resource for expandable list_item with given weather code */
    public static int getBackgroundResourceFromWeatherCode(int weatherCode) {
        int imageResource;
        switch (weatherCode / 100) {
            case 8:
                imageResource = R.drawable.bluesky_banner_800;
                break;
            case 7:
                imageResource = R.drawable.atmosphere_banner_700;
                break;
            case 6:
                imageResource = R.drawable.snow_banner_600;
                break;
            case 5:
                imageResource = R.drawable.rain_banner_500;
                break;
            case 2:
                imageResource = R.drawable.thunderstorm_banner_200;
                break;
            default:
                imageResource = R.drawable.default_banner;
                break;
        }
        return imageResource;
    }
}
