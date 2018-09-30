package com.example.gayashan.databaseapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.provider.BaseColumns._ID;

public class ImagesProvider extends ContentProvider{

    public static final String LOG_TAG = ImagesProvider.class.getSimpleName();

    private static final int PICTURES = 100;

    private static final int PICTURES_ID = 101;

    public static final String CONTENT_AUTHORITY = "com.delaroystudios.imagecamerabitmap";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_IMAGES = "image-path";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_IMAGES);

    private static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUrimatcher.addURI(CONTENT_AUTHORITY, PATH_IMAGES, PICTURES);

        sUrimatcher.addURI(CONTENT_AUTHORITY, PATH_IMAGES + "/#", PICTURES_ID );
    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUrimatcher.match(uri);

        switch (match){
            case PICTURES:

                cursor = database.query(DbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PICTURES_ID:

                selection = _ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(DbHelper.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
             default:
                 throw new IllegalArgumentException("Cannot query Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {return null;}

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUrimatcher.match(uri);
        switch (match){
            case PICTURES:
                return insertCart(uri, contentValues);
             default:
                 throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertCart(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
