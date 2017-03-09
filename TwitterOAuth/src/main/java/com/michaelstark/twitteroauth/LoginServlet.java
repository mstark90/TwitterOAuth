/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michaelstark.twitteroauth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Handles the authentication between Twitter by initiating the process and handling the callback.
 * @author mstark
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger("LoginServlet");
    

    /**
     * Handles the HTTP <code>GET</code> method.
     * This method will store the OAuth token in the HTTP session for later use.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Begins the OAuth authentication mechanism.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ConfigurationBuilder builder = new ConfigurationBuilder();
        
        builder.setOAuthConsumerKey("<consumer key goes here>")
               .setOAuthConsumerSecret("<consumer secret goes here>");
        
        Twitter twitter = new TwitterFactory(builder.build()).getInstance();
        
        HttpSession session = request.getSession(true);
        
        // Get the callback URL.
        String callbackUrl = String.format("http://%s:%d%s/oauth_callback",
                request.getServerName(), request.getServerPort(), request.getContextPath());
        
        try {
            // Get the request token.
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
            
            // Store the request token for later use.
            session.setAttribute("requestToken", requestToken);
            
            // Save the Twitter client for later.
            session.setAttribute("twitterClient", twitter);
            
            // Redirect to Twitter authentication page.
            response.sendRedirect(requestToken.getAuthenticationURL());
        } catch(TwitterException e) {
            logger.log(Level.SEVERE, "Could not login via Twitter: ", e);
            
            // Failure. Go back to home. Maybe add an error message.
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "OAuth Handler Servlet";
    }// </editor-fold>

}
