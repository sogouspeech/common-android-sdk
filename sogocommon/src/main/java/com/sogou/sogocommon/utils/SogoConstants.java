// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.utils;

import android.os.Environment;

import java.io.File;

public class SogoConstants {

	// 网络连接超时时间
	public static final int SOCKET_TIME_OUT_TIME = 30000;
	// 网络读取时间
	public static final int SOCKET_READ_TIME = 20000;

	public static final String APPID = "com.sogou.speech.common-appID";
	public static final String APPKEY = "com.sogou.speech.common-appkey";

	public static class Path{
		//得到当前外部存储设备的目录
		public static final String SDCardPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		private static String APPFilesPath = SDCardPATH + "SogouSpeech" + File.separator;
		public static final String localPCMFilesPath = APPFilesPath + "ttsResult" + File.separator;
		public static final String localLogFilesPath = APPFilesPath + "log" + File.separator;

		public static String pathPrefix_data = "/data/local/model_file";
		public static String pathPrefix_system = "/system/media/model_file";
		public static String pathPrefix_default = "/sdcard";
	}


	public static class NetWorkConstants {
		/**
		 * Unknown network class
		 */
		public static final int NETWORK_CLASS_UNKNOWN = 0;

		public static final int NETWORK_OFFLINE = 1;
		public static final int NETWORK_CLASS_WAP = 2;

		/**
		 * "2G" networks
		 */
		public static final int NETWORK_CLASS_2_G = 3;

		/**
		 * "3G" networks
		 */
		public static final int NETWORK_CLASS_3_G = 4;

		/**
		 * "4G" networks
		 */
		public static final int NETWORK_CLASS_4_G = 5;
		/**
		 * wifi net work
		 */
		public static final int NETWORK_WIFI = 6;

	}

	public static class URL_CONSTANT{
		public static final String URL_PRIFIX = "";

		public static final String URL_RECOGNIZE = "";

	}
}