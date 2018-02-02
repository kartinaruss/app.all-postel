package  all_postel.all_postel;

import android.content.Context;
import android.net.ConnectivityManager;

import com.yandex.metrica.YandexMetrica;
import com.facebook.FacebookSdk;

public class AllPostel extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();


        // Инициализация AppMetrica SDK
        YandexMetrica.activate(getApplicationContext(), "c0e959b3-a5bb-4fc9-8242-f59405f731a2");
        // Отслеживание активности пользователей
        YandexMetrica.enableActivityAutoTracking(this);
    }



}
