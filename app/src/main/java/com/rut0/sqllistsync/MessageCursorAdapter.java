package com.rut0.sqllistsync;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Winston on 9/13/2015.
 */
public class MessageCursorAdapter extends CursorAdapter {
    public MessageCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MessageCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView messageView = (TextView) view.findViewById(R.id.message);
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String message = cursor.getString(cursor.getColumnIndex("message"));
        nameView.setText(name);
        messageView.setText(message);
    }
}
