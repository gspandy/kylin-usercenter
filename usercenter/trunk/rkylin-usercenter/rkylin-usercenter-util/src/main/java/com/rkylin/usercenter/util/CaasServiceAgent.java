package com.rkylin.usercenter.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import cn.rongcapital.caas.agent.CaasAgentSettings;

import com.ruixue.serviceplatform.commons.web.DefaultJacksonJaxbJsonProvider;

public  class CaasServiceAgent {
    
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CaasServiceAgent.class);


    /**
     * the client
     */
    private ResteasyClient client;

    
    /**
     * the initialize flag
     */
    private volatile AtomicBoolean initialized = new AtomicBoolean(false);

    
    
    public AtomicBoolean getInitialized() {
        return initialized;
    }


    /**
     * to start the agent
     */
    public  <T> T initAgent( Class<T> serviceInterface,String configFile) {
        this.initialized.set(false);
        
        CaasAgentSettings settings = null;

        // step-1: load settings
        if (settings == null) {
            LOGGER.debug("starting the CAAS agent with settings file: {}", configFile);
            InputStream is = null;
            final Yaml yaml = new Yaml();
            try {
                is = new FileInputStream(configFile);
                settings = yaml.loadAs(is, CaasAgentSettings.class);
            } catch (Exception e) {
                LOGGER.error("load the CAAS settings from YAML file failed, file: " + configFile
                        + ", error: " + e.getMessage(), e);
                throw new IllegalArgumentException("load the CAAS settings from YAML file failed, file: "
                        + configFile + ", error: " + e.getMessage(), e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e2) {
                        //
                    }
                }
            }
        }

        // step-2: build the client for thread safe
        final RegistryBuilder<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE);
        // SSL
        if (settings.isSslEnabled()) {
            try {
                final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {

                    @Override
                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return true;
                    }

                }).build();
                socketFactoryRegistry.register("https", new SSLConnectionSocketFactory(sslContext));
            } catch (Exception e) {
                LOGGER.error("enable the SSL failed,error: " + e.getMessage(), e);
                throw new RuntimeException("enable the SSL failed,error: " + e.getMessage(), e);
            }
        }
        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(
                new PoolingHttpClientConnectionManager(socketFactoryRegistry.build()));
        // proxy
        if (settings.isProxyEnabled()) {
            final HttpHost proxyHost = new HttpHost(settings.getProxyHost(), settings.getProxyPort());
            httpClientBuilder.setProxy(proxyHost);
        }
        // the client
        this.client = new ResteasyClientBuilder().httpEngine(new ApacheHttpClient4Engine(httpClientBuilder.build()))
                .build();
        // json provider
        this.client.register(new DefaultJacksonJaxbJsonProvider());
        // target
        final ResteasyWebTarget target = client.target(settings.getCaasApiUrl());

        // step-3: proxy the CAAS resource
        T proxy = target.proxy(serviceInterface);

        // done
        this.initialized.set(true);
        LOGGER.info("the CAAS agent is ready to work");
        return proxy;
    }

    /**
     * to stop the agent
     */
    public void releaseAgent() {
        LOGGER.debug("stopping the CAAS agent ...");
        this.initialized.set(false);
        // close the client
        if (this.client != null) {
            this.client.close();
        }
        LOGGER.info("the CAAS agent stopped");
    }

}
