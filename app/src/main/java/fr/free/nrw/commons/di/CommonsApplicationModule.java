package fr.free.nrw.commons.di;

import android.support.v4.util.LruCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.free.nrw.commons.BuildConfig;
import fr.free.nrw.commons.CommonsApplication;
import fr.free.nrw.commons.auth.AccountUtil;
import fr.free.nrw.commons.auth.SessionManager;
import fr.free.nrw.commons.caching.CacheController;
import fr.free.nrw.commons.data.DBOpenHelper;
import fr.free.nrw.commons.mwapi.ApacheHttpClientMediaWikiApi;
import fr.free.nrw.commons.mwapi.MediaWikiApi;
import fr.free.nrw.commons.nearby.NearbyPlaces;

@Module
@SuppressWarnings({"WeakerAccess", "unused"})
public class CommonsApplicationModule {
    private CommonsApplication application;

    public CommonsApplicationModule(CommonsApplication application) {
        this.application = application;
    }

    @Provides
    public AccountUtil providesAccountUtil() {
        return new AccountUtil(application);
    }

    @Provides
    @Singleton
    public SessionManager providesSessionManager(MediaWikiApi mediaWikiApi) {
        return new SessionManager(application, mediaWikiApi);
    }

    @Provides
    @Singleton
    public MediaWikiApi provideMediaWikiApi() {
        return new ApacheHttpClientMediaWikiApi(BuildConfig.WIKIMEDIA_API_HOST);
    }

    @Provides
    @Singleton
    public CacheController provideCacheController() {
        return new CacheController();
    }

    @Provides
    @Singleton
    public DBOpenHelper provideDBOpenHelper() {
        return new DBOpenHelper(application);
    }

    @Provides
    @Singleton
    public NearbyPlaces provideNearbyPlaces() {
        return new NearbyPlaces();
    }

    @Provides
    @Singleton
    public LruCache<String, String> provideLruCache() {
        return new LruCache<>(1024);
    }
}
