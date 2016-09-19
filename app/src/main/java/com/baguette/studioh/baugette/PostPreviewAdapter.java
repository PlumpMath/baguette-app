package com.baguette.studioh.baugette;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by studioh on 8/21/16.
 */
public class PostPreviewAdapter extends BaseAdapter {

    private ArrayList<PostPreviewItem> postPreviewItemList = new ArrayList<PostPreviewItem>();
    @Override
    public int getCount() {
        return postPreviewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return postPreviewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        PostPreviewViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater infl = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infl.inflate(R.layout.postpreview_item, parent, false);


            viewHolder = new PostPreviewViewHolder();

            viewHolder.profile = (ImageView) convertView.findViewById(R.id.previewProfileImage);
            viewHolder.preview1 = (ImageView) convertView.findViewById(R.id.previewPreviewImage1);
            viewHolder.preview2 = (ImageView) convertView.findViewById(R.id.previewPreviewImage2);
            viewHolder.Author = (TextView) convertView.findViewById(R.id.previewAuthorText);
            viewHolder.Title = (TextView) convertView.findViewById(R.id.previewTitleText);
            viewHolder.Lights = (TextView) convertView.findViewById(R.id.previewLBText);
            viewHolder.Comments = (TextView) convertView.findViewById(R.id.previewCommentText);
            viewHolder.AdditionalText = (TextView) convertView.findViewById(R.id.previewAdditionalText);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (PostPreviewViewHolder) convertView.getTag();
        }
        PostPreviewItem ppi = postPreviewItemList.get(position);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCache(new WeakMemoryCache()).build();
        com.nostra13.universalimageloader.core.ImageLoader il = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        il.init(config);
        il.displayImage(ppi.getPostImage1(), viewHolder.preview1);
        il.displayImage(ppi.getPostImage2(), viewHolder.preview2);
        il.displayImage(ppi.getProfileImage(), viewHolder.profile);
        viewHolder.Author.setText(ppi.getAuthor());
        viewHolder.Title.setText(ppi.getTitle());
        viewHolder.Lights.setText(ppi.getLights());
        viewHolder.Comments.setText(ppi.getComments());
        viewHolder.AdditionalText.setText(ppi.getAdditionalText());

        return convertView;
    }

    public void addItem(String profile, String preview1, String preview2, String title, String author, String lightsCnt, String commentCnt, String additionalText) {
        PostPreviewItem ppi = new PostPreviewItem();

        ppi.setAuthor(author);
        ppi.setTitle(title);
        ppi.setPostImage1(preview1);
        ppi.setPostImage2(preview2);
        ppi.setProfileImage(profile);
        ppi.setComments(commentCnt);
        ppi.setLights(lightsCnt);
        ppi.setAddtionalText(additionalText);

        postPreviewItemList.add(ppi);
    }
    public void Clear() {
        postPreviewItemList.clear();
    }
}
