package com.studio.cloudelevator.entity;

public class Media {

	// {"status":true,"message":,"data" :
	// [{"fileSize":"92837","id":175,"url":"http://hzfx10000.oss-cn-hangzhou.aliyuncs.com/B0273F86-98AE-4AE1-ABE8-EE52CC7E739C.png"},{"fileSize":"65464","id":176,"url":"http://hzfx10000.oss-cn-hangzhou.aliyuncs.com/2008124144258165_2.jpg"}]
	// }

	public long mediaId; // 175
	public long fileSize; // "92837",
	public String url; // "http://hzfx10000.oss-cn-hangzhou.aliyuncs.com/B0273F86-98AE-4AE1-ABE8-EE52CC7E739C.png"

	@Override
	public String toString() {
		return "Media [mediaId=" + mediaId + ", fileSize=" + fileSize + ", url=" + url + "]";
	}

}
