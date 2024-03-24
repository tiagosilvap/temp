package com.hotmart.api.subscription.checkouttokens3.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip solution found at: https://myadventuresincoding.wordpress.com/2016/01/02/java-simple-gzip-utility-to-compress-and-decompress-a-string/
 */
@UtilityClass
@Slf4j
public class GzipUtil {

    public static String zip(final String str) {
        if (StringUtils.isNotBlank(str)) {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                    gzipOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
                }

                return Base64.encodeBase64String(byteArrayOutputStream.toByteArray());
            } catch(IOException e) {
                throw new RuntimeException("Failed to zip content", e);
            }
        }
        return str;
    }

    public static String unzip(final String str) {
        if (StringUtils.isNotBlank(str) && Base64.isBase64(str)) {
            byte[] compressed = Base64.decodeBase64(str);

            if (!isZipped(compressed)) {
                return str;
            }

            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed)) {
                try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
                    try (InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)) {
                        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                            StringBuilder output = new StringBuilder();
                            String line;
                            while((line = bufferedReader.readLine()) != null){
                                output.append(line);
                            }
                            return output.toString();
                        }
                    }
                }
            } catch(IOException e) {
                throw new RuntimeException("Failed to unzip content", e);
            }
        }
        return str;
    }

    public static boolean isZipped(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
                && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }

    public static boolean isZipped(final String str) {
        try {
            if (StringUtils.isNotBlank(str) && Base64.isBase64(str)) {
                byte[] compressed = Base64.decodeBase64(str);
                return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
                        && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
            }
        } catch (Exception e) {
            log.warn("Failure to unzip string", e);
        }
        return false;
    }
}
