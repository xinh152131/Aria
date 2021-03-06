package com.arialyy.simple.multi_task;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.Task;
import com.arialyy.frame.util.show.L;
import com.arialyy.simple.R;
import com.arialyy.simple.base.BaseActivity;
import com.arialyy.simple.databinding.ActivityDownloadBinding;

/**
 * Created by AriaL on 2017/1/6.
 */

public class DownloadActivity extends BaseActivity<ActivityDownloadBinding> {
  @Bind(R.id.list) RecyclerView    mList;
  private          DownloadAdapter mAdapter;

  @Override protected int setLayoutId() {
    return R.layout.activity_download;
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    mAdapter = new DownloadAdapter(this, Aria.get(this).getDownloadList());
    mList.setLayoutManager(new LinearLayoutManager(this));
    mList.setAdapter(mAdapter);
  }

  @Override protected void dataCallback(int result, Object data) {

  }

  @Override protected void onResume() {
    super.onResume();
    Aria.whit(this).addSchedulerListener(new MySchedulerListener());
  }

  private class MySchedulerListener extends Aria.SimpleSchedulerListener {
    @Override public void onTaskPre(Task task) {
      super.onTaskPre(task);
      L.d(TAG, "download pre");
      mAdapter.updateState(task.getDownloadEntity());
    }

    @Override public void onTaskStart(Task task) {
      super.onTaskStart(task);
      L.d(TAG, "download start");
      mAdapter.updateState(task.getDownloadEntity());
    }

    @Override public void onTaskResume(Task task) {
      super.onTaskResume(task);
      L.d(TAG, "download resume");
      mAdapter.updateState(task.getDownloadEntity());
    }

    @Override public void onTaskRunning(Task task) {
      super.onTaskRunning(task);
      mAdapter.setProgress(task.getDownloadEntity());
    }

    @Override public void onTaskStop(Task task) {
      super.onTaskStop(task);
      mAdapter.updateState(task.getDownloadEntity());
    }

    @Override public void onTaskCancel(Task task) {
      super.onTaskCancel(task);
      mAdapter.updateState(task.getDownloadEntity());
    }

    @Override public void onTaskComplete(Task task) {
      super.onTaskComplete(task);
      mAdapter.updateState(task.getDownloadEntity());
    }

    @Override public void onTaskFail(Task task) {
      super.onTaskFail(task);
      L.d(TAG, "download fail");
    }
  }
}
