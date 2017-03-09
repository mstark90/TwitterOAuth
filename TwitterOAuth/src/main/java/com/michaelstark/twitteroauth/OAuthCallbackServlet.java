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
 * Provides methods for handing OAuth callbacks.
 * @author mstark
 */
@WebServlet(name = "OAuthCallbackServlet", urlPatterns = {"/oauth_callback"})
public class OAuthCallbackServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger("OAuthCallbackServlet");

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String verificationToken = request.getParameter("oauth_verifier");
        
        HttpSession session = request.getSession(true);
        
        // Get the stored Twitter client from LoginServlet.
        Twitter twitter = (Twitter) session.getAttribute("twitterClient");
        
        // Get the saved request token.
        RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");
        
        try {
            // Get an access token so we can do stuff.
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verificationToken);
            
            // Store the access token for later.
            session.setAttribute("accessToken", accessToken);
            
            // Configure the Twitter client to use our access token.
            twitter.setOAuthAccessToken(accessToken);
            
            // Get the current user info from Twitter and store it in the session.
            session.setAttribute("user", twitter.showUser(twitter.getScreenName()));
        }catch(TwitterException e) {
            logger.log(Level.SEVERE, "Could not get the user info: ", e);
        }
        
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
