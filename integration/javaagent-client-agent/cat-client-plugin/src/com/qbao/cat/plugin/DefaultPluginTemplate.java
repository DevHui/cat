/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qbao.cat.plugin;
/**
 *
 */


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

/**
 * @author andersen
 *
 */
public abstract class DefaultPluginTemplate implements PluginTemplate {

    protected static Properties config = new Properties();

    private static AtomicBoolean isInited = new AtomicBoolean(false);

    static {
        if (isInited.compareAndSet(false, true) && System.getProperty("CATPLUGIN_CONF") != null) {
            String configPath = System.getProperty("CATPLUGIN_CONF");
            try {
                // ��ȡ�����ļ�a.properties
                InputStream in = new BufferedInputStream(new FileInputStream(configPath));
                config.load(new InputStreamReader(in, "utf-8")); /// ���������б�
                in.close();
            } catch (Exception e) {
                System.out.println("Warn: CatPlugin can't resolve properties file : " + configPath);
            }
        } else {
            System.out.println("Warn: CatPlugin miss properties file! You can set it with -DCATPLUGIN_CONF=/opt/.... ��");
        }
    }

    @Override
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return proxyCollector(pjp);
    }

    public Object proxyCollector(ProceedingJoinPoint pjp) throws Throwable {
        Transaction transaction = proxyBeginLog(pjp);
        Object obj = null;
        try {
            obj = pjp.proceed();
            proxySuccess(transaction);
            return obj;
        } catch (Throwable e) {
            exception(transaction, e);
            throw e;
        } finally {
            proxyEndLog(transaction, obj, pjp.getArgs());
        }
    }

    protected void proxySuccess(Transaction transaction) {
        try {
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Throwable e) {
        }
    }

    protected void exception(Transaction transaction, Throwable t) {
        try {
            if (isNotNull(transaction)) {
                transaction.setStatus(t);
                Cat.logError(t);
            }
        } catch (Throwable e) {
        }
    }

    protected Transaction proxyBeginLog(ProceedingJoinPoint pjp) {
        try {
            return beginLog(pjp);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void proxyEndLog(Transaction transaction, Object retVal, Object... params) {
        try {
            if (isNotNull(transaction)) {
                endLog(transaction, retVal, params);
            }
        } catch (Throwable e) {

        } finally {
            if (isNotNull(transaction)) {
                transaction.complete();
            }
        }
    }

    protected Transaction newTransaction(String type, String name) {
        return Cat.newTransaction(type, name);
    }

    /**
     * ����ִ��ǰ��ʼ���
     * @param pjp ����ִ��������
     * @return ������ɵ�transaction����   ע���������м�������event
     */
    protected abstract Transaction beginLog(ProceedingJoinPoint pjp);

    /**
     * ����ִ�к������β����
     * @param transaction  beginLog�����ɵ�transaction����ע�������ֶ�����complete������ģ���ѵ���
     * @param retVal �������ؽ��
     * @param params ��������ʱ�������
     */
    protected abstract void endLog(Transaction transaction, Object retVal, Object... params);

    public boolean isNull(Object obj) {
        return obj == null;
    }

    public boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public boolean isNullOrEmpty(String param) {
        return isNull(param) || param.trim().equals("");
    }


    @Override
    public void doReturn(JoinPoint joinPoint, Object retVal) {
        // TODO Auto-generated method stub

    }

    @Override
    public void doThrowing(JoinPoint joinPoint, Throwable ex) {
        // TODO Auto-generated method stub

    }


    @Override
    public void doBefore(JoinPoint joinPoint) {
        // TODO Auto-generated method stub

    }

    @Override
    public void doAfter(JoinPoint joinPoint) {
        // TODO Auto-generated method stub

    }

    public String getConcreteUri(String uri) {
        int index = -1;
        if ((index = uri.indexOf(";")) > -1) {
            uri = uri.substring(0, index);
        }
        return uri;
    }

}
