package com.arialyy.simple.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.Task;
import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;
import com.arialyy.simple.R;

/**
 * Created by Aria.Lao on 2017/1/18.
 */

public class SimpleNotification {
  private static final String DOWNLOAD_URL =
      "http://static.gaoshouyou.com/d/6e/e5/ff6ecaaf45e532e6d07747af82357472.apk";

  private NotificationManager mManager;
  private Context mContext;
  private NotificationCompat.Builder mBuilder;
  private static final int mNotifiyId = 0;

  public SimpleNotification(Context context) {
    mContext = context;
    init();
  }

  private void init() {
    mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    mBuilder = new NotificationCompat.Builder(mContext);
    mBuilder.setContentTitle("Aria Download Test")
        .setContentText("进度条")
        .setProgress(100, 0, false)
        .setSmallIcon(R.mipmap.ic_launcher);
    mManager.notify(mNotifiyId, mBuilder.build());
    Aria.whit(mContext).addSchedulerListener(new DownloadCallback(mBuilder, mManager));
  }

  public void start() {
    Aria.whit(mContext)
        .load(DOWNLOAD_URL)
        .setDownloadName("notification_test.apk")
        .setDownloadPath(
            Environment.getExternalStorageDirectory() + "/Download/notification_test.apk")
        .start();
  }

  public void stop() {
    Aria.whit(mContext).load(DOWNLOAD_URL).stop();
  }

  private static class DownloadCallback extends Aria.SimpleSchedulerListener {
    NotificationCompat.Builder mBuilder;
    NotificationManager mManager;

    private DownloadCallback(NotificationCompat.Builder builder, NotificationManager manager) {
      mBuilder = builder;
      mManager = manager;
    }

    @Override public void onTaskStart(Task task) {
      super.onTaskStart(task);
    }

    @Override public void onTaskPre(Task task) {
      super.onTaskPre(task);
    }

    @Override public void onTaskStop(Task task) {
      super.onTaskStop(task);
    }

    @Override public void onTaskRunning(Task task) {
      super.onTaskRunning(task);
      long len = task.getFileSize();
      int p = (int) (task.getCurrentProgress() * 100 / len);
      if (mBuilder != null) {
        mBuilder.setProgress(100, p, false);
        mManager.notify(mNotifiyId, mBuilder.build());
      }
    }

    @Override public void onTaskComplete(Task task) {
      super.onTaskComplete(task);
      if (mBuilder != null) {
        mBuilder.setProgress(100, 100, false);
        mManager.notify(mNotifiyId, mBuilder.build());
      }
    }

    @Override public void onTaskCancel(Task task) {
      super.onTaskCancel(task);
    }
  }
}
