package com.locydragon.fibel.network;

import com.locydragon.fibel.util.ObjectUtil;
import com.locydragon.fibel.util.concurrent.TimeUnit;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DataDownland {
	public boolean downlandSuccess = true;
	public boolean isOk = false;
	private List<Thread> watchThreadList = new ArrayList<>();
    public void downLoadData(int threadSize) {
    	try {
			download(ObjectUtil.dataUrl, threadSize);
		} catch (Exception exc) {
    		downlandSuccess = false;
    		isOk = false;
    		exc.printStackTrace();
		}
	}
	private void download(String path, int threadSize) throws Exception {
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		if (connection.getResponseCode() == 200) {
			int length = connection.getContentLength();// 获取网络文件长度
			File file = new File(getFileName(path));
			if (!file.exists()) {
				file.createNewFile();
			}
			// 在本地生成一个长度与网络文件相同的文件
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			accessFile.setLength(length);
			accessFile.close();

			// 计算每条线程负责下载的数据量
			int block = length % threadSize == 0 ? length / threadSize : length
					/ threadSize + 1;
			for (int threadId = 0; threadId < threadSize; threadId++) {
				DownloadThread download = new DownloadThread(threadId, block, url, file);
				download.start();
				this.watchThreadList.add(download);
			}
			Thread watchingThread = new Thread(new Runnable() {
				@Override
				public void run() {
					whileTrue:
					while (true) {
						try {
							Thread.sleep(TimeUnit.SECOND.time);
							for (Thread instance : watchThreadList) {
								if (instance.isAlive()) {
									continue whileTrue;
								}
							}
							isOk = true;
							break whileTrue;
						} catch (Exception exc) {
							continue;
						}
					}
				}
			});
			watchingThread.start();
		} else {
			this.downlandSuccess = false;
		}
	}

	private class DownloadThread extends Thread {

		private int threadId;
		private int block;
		private URL url;
		private File file;

		public DownloadThread(int threadId, int block, URL url, File file) {
			this.threadId = threadId;
			this.block = block;
			this.url = url;
			this.file = file;
		}

		@Override
		public void run() {
			int start = threadId * block; // 计算该线程从网络文件什么位置开始下载
			int end = (threadId + 1) * block - 1; // 计算下载到网络文件什么位置结束
			try {
				RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
				accessFile.seek(start); //从start开始

				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(5000);
				//设置获取资源数据的范围，从start到end
				connection.setRequestProperty("Range", "bytes=" + start + "-"
						+ end);
				//注意多线程下载状态码是 206 不是200
				if (connection.getResponseCode() == 206) {
					InputStream inputStream = connection.getInputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = inputStream.read(buffer)) != -1) {
						accessFile.write(buffer, 0, len);
					}
					accessFile.close();
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取文件名称
	 *
	 * @param path 网络文件路径
	 * @return
	 */
	private String getFileName(String path) {
		return path.substring(path.lastIndexOf("/") + 1);
	}
}
