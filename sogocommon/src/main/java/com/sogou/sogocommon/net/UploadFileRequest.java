package com.sogou.sogocommon.net;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * created by zhouwei 2018/6/15
 */
public final class UploadFileRequest extends Request {
    public Map<String, String> formParams = new HashMap<>();
    public byte[] attachment;
    public String attachmentName;

    public UploadFileRequest(String url, byte[] attachment, String attachmentName) {
        super(url);
        if (attachment == null || attachment.length == 0) {
            throw new RuntimeException("attachment is empty");
        }
        this.attachment = attachment;
        this.attachmentName = attachmentName;
    }

    @Override
    public void setWorker() {
        task = new UploadFileWorker(this);
    }

    public Map<String, String> getFormParams() {
        return formParams;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public UploadFileRequest setFormParams(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            formParams.put(key, value);
        }

        return this;
    }
}
