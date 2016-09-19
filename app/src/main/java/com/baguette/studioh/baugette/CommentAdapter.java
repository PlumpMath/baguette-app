package com.baguette.studioh.baugette;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by studioh on 8/21/16.
 */
public class CommentAdapter extends BaseAdapter {
    private ArrayList<CommentItem> CommentItemList = new ArrayList<CommentItem>();

    @Override
    public int getCount() { return CommentItemList.size(); }

    @Override
    public Object getItem(int position) { return CommentItemList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater infl = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infl.inflate(R.layout.comment_item, parent, false);
        }
        CommentItem ci = CommentItemList.get(position);
        ((TextView)(convertView.findViewById(R.id.commentUserLabel))).setText(ci.getUsernameText());
        ((TextView)(convertView.findViewById(R.id.commentContentLabel))).setText(ci.getContentText());

        return convertView;
    }


    public void addItem(String Username, String Content) {
        CommentItem ci = new CommentItem();
        ci.setContentText(Content);
        ci.setUsernameText(Username);
        CommentItemList.add(ci);
    }
    public void Clear() {
        CommentItemList.clear();
    }
}
