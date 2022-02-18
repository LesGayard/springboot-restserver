package com.leslie.springbootrestserver.component;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
* Corrige les pbs liés aux Cross-Domain
* Le client et le serveux peuvent être hébergés sur deux serveurs distants
* Penser aux pbs réseaux qui peuvent entraver la communication
* il faut indiquer au server quelles sont les type d'en têtes à prendre en considération
* */

@Component
public class CrossDomainFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)throws ServletException, IOException{
        response.addHeader("Access-Control-Allow-Origin","*"); //ttes les urI sont autorisées
        response.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers","origin,content-type,accept,x-req");
        chain.doFilter(request, response);
    }
}
