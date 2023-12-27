package com.nanshan.springbootnginxreverseproxy.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip 壓縮工具類別
 */
public class GzipUtil {

    public static byte[] compress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return new byte[0];
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            GZIPOutputStream gzOut = new GZIPOutputStream(out);
            gzOut.write(data);
            gzOut.close();
            return out.toByteArray();
        }
    }

    public static byte[] decompress(byte[] gzip) throws IOException {
        if (gzip == null || gzip.length == 0) {
            return new byte[0];
        }
        GZIPInputStream gzIn = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(gzip)) {
            gzIn = new GZIPInputStream(in);
        }
        return gzIn.readAllBytes();
    }

    public static void main(String[] args) throws IOException {
       var jsonStr = "{" +
                        "\"userId\": 1," +
                        "\"id\": 1," +
                        "\"title\": \"delectus aut autem\"," +
                        "\"completed\": false" +
                      "}";
        byte[] compressByteArr = GzipUtil.compress(jsonStr.getBytes(StandardCharsets.UTF_8));
        System.out.println("compressByteArr = " + compressByteArr);

        byte[] decompress = GzipUtil.decompress(compressByteArr);
        System.out.println("decompress = " + decompress);
    }
}
