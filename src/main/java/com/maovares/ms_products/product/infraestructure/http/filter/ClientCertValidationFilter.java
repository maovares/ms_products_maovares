package com.maovares.ms_products.product.infraestructure.http.filter;

import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import com.maovares.ms_products.product.infraestructure.http.exception.InvalidCertificateException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class ClientCertValidationFilter implements Filter {
     private static final String EXPECTED_THUMBPRINT = System.getenv("CLIENT_CERT_THUMBPRINT");

     @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest httpReq = (HttpServletRequest) request;
            String certHeader = httpReq.getHeader("X-ARR-ClientCert");

            if (certHeader == null) {
                throw new InvalidCertificateException("Missing client certificate header");
            }

            byte[] decoded = Base64.getDecoder().decode(certHeader);
            X509Certificate cert = (X509Certificate) CertificateFactory
                    .getInstance("X.509")
                    .generateCertificate(new java.io.ByteArrayInputStream(decoded));

            // Calcular thumbprint SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            String thumbprint = bytesToHex(md.digest(cert.getEncoded()));

            if (!thumbprint.equalsIgnoreCase(EXPECTED_THUMBPRINT)) {
                throw new InvalidCertificateException("Invalid client certificate thumbprint");
            }

            chain.doFilter(request, response);

        } catch (Exception e) {
            throw new InvalidCertificateException("Error validating client certificate", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}