package com.example.gayashan.databaseapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public ImagesAdapter(Context mContext){this.mContext = mContext;}


    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_image_item, parent, false);

        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {

        int fraganceName = mCursor.getColumnIndex(DbHelper.COLUMN_NAME);


        mCursor.moveToPosition(position);

        byte[] image = mCursor.getBlob(fraganceName);

        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.image.setImageBitmap(Bitmap.createScaledBitmap(bmp,200,20,false));
    }

    @Override
    public int getItemCount() {
        if(mCursor==null){
            return 0;
        }
        return mCursor.getCount();
    }
    public Cursor swapCursor(Cursor c){
        if(mCursor==c){
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor= c;

        if(c != null){
            this.notifyDataSetChanged();
        }
        return temp;
    }
    public class ImagesViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ImagesViewHolder(View itemView){
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.imageProof);

        }
    }
}
