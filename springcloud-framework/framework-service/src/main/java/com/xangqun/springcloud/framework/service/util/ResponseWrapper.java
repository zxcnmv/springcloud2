/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author laixiangqun
 * @since 2018-5-25
 */
public class ResponseWrapper extends HttpServletResponseWrapper {


    private ByteArrayOutputStream buffer = null;

    private ServletOutputStream out = null;

    private PrintWriter writer = null;


    public ResponseWrapper(HttpServletResponse response) throws IOException{
        super(response);
        buffer = new ByteArrayOutputStream();
        out = new WapperedOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, StringUtils.isBlank(response.getCharacterEncoding())?"UTF-8":response.getCharacterEncoding()));
    }

    /**
     * 重载父类获取outputstream的方法
     * @return
     * @throws IOException
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }


    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
            out.close();
        }
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public byte[] getResponseData() throws IOException {
        //将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据
        flushBuffer();
        byte[] bytes;
        try {
            bytes = buffer.toByteArray();
            return bytes;
        } catch (Exception e) {
            return null;
        }finally {
            if (buffer!=null){
                buffer.close();
            }
        }
    }


    //内部类，对ServletOutputStream进行包装，指定输出流的输出端

    private class WapperedOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream bos = null;

        public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
            bos = stream;
        }

        /**
         * 将指定字节写入输出流bos
         * @param b
         * @throws IOException
         */
        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public boolean isReady() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
            // TODO Auto-generated method stub

        }
    }


}