package com.jierong.share.mvp.view.ada;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jierong.share.R;
import com.jierong.share.jcplayer.JCVideoPlayer;
import com.jierong.share.jcplayer.JCVideoPlayerStandard;
import com.jierong.share.mvp.model.info.VideoInfo;
import java.util.List;

/**
 * 视频播放器列表适配器
 */
public class JCVideoAdapter extends RecyclerView.Adapter<JCVideoAdapter.MyViewHolder> {
    private Context context;
    private List<VideoInfo> data;

    public JCVideoAdapter(Context context, List<VideoInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.recyclerview_item_video, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final VideoInfo info = data.get(position);
        holder.JCvp.setUp(info.getUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, info.getName());
        holder.video_time.setText(info.getTime());
        holder.video_comment_num.setText(String.valueOf(info.getCommentNum()));
        holder.video_love_num.setText(String.valueOf(info.getLoveNum()));
        if(info.isLove()) {
            holder.video_love_img.setImageResource(R.drawable.ic_video_love);
        } else {
            holder.video_love_img.setImageResource(R.drawable.ic_video_loveno);
        }

        holder.video_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 模拟一秒网络请求
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        info.setLove(!info.isLove());
                        if (info.isLove()) {
                            info.setLoveNum(info.getLoveNum() + 1);
                            holder.video_love_num.setText(String.valueOf(info.getLoveNum()));
                            holder.video_love_img.setImageResource(R.drawable.ic_video_love);
                        } else {
                            info.setLoveNum(info.getLoveNum() - 1);
                            holder.video_love_num.setText(String.valueOf(info.getLoveNum()));
                            holder.video_love_img.setImageResource(R.drawable.ic_video_loveno);
                        }
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard JCvp;
        TextView video_time, video_comment_num, video_love_num;
        LinearLayout video_comment, video_love;
        ImageView video_love_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            JCvp = (JCVideoPlayerStandard) itemView.findViewById(R.id.JCvp);
            video_time = (TextView) itemView.findViewById(R.id.video_time);
            video_comment_num = (TextView) itemView.findViewById(R.id.video_comment_num);
            video_love_num = (TextView) itemView.findViewById(R.id.video_love_num);
            video_comment = (LinearLayout) itemView.findViewById(R.id.video_comment);
            video_love = (LinearLayout) itemView.findViewById(R.id.video_love);
            video_love_img = (ImageView) itemView.findViewById(R.id.video_love_img);
        }
    }
}
