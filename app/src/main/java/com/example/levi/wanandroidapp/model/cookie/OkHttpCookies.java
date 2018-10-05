package com.example.levi.wanandroidapp.model.cookie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/**
 * Author: Levi
 * CreateDate: 2018/10/5 11:03
 */
public class OkHttpCookies implements Serializable {
    private transient final Cookie mCookies;
    private transient Cookie mClientCookies;

    public OkHttpCookies(Cookie cookie) {
        this.mCookies = cookie;
    }

    public Cookie getCookies() {
        Cookie bestCookies = mCookies;
        if (mClientCookies != null) {
            bestCookies = mClientCookies;
        }
        return bestCookies;
    }

    /**
     * 自定义序列化与反序列化的规则，ObjectOutputStream通过反射调用writeObject方法
     * @param oos
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(mCookies.name());
        oos.writeObject(mCookies.value());
        oos.writeLong(mCookies.expiresAt());
        oos.writeObject(mCookies.domain());
        oos.writeObject(mCookies.path());
        oos.writeBoolean(mCookies.secure());
        oos.writeBoolean(mCookies.httpOnly());
        oos.writeBoolean(mCookies.hostOnly());
        oos.writeBoolean(mCookies.persistent());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        String name = (String) ois.readObject();
        String value = (String) ois.readObject();
        long expiresAt = ois.readLong();
        String domain = (String) ois.readObject();
        String path = (String) ois.readObject();
        boolean secure = ois.readBoolean();
        boolean httpOnly = ois.readBoolean();
        boolean hostOnly = ois.readBoolean();
        boolean persistent = ois.readBoolean();
        Cookie.Builder builder = new Cookie.Builder();
        builder = builder.name(name);
        builder = builder.value(value);
        builder = builder.expiresAt(expiresAt);
        builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
        builder = builder.path(path);
        builder = secure ? builder.secure() : builder;
        builder = httpOnly ? builder.httpOnly() : builder;
        mClientCookies = builder.build();
    }
}
