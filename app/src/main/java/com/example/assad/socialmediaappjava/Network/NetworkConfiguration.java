package com.example.assad.socialmediaappjava.Network;

public class NetworkConfiguration {

    /* Using String BASE_NETWORK_ADDRESS and setting the IP address gives Android access to be able to
     * acknowledge where the database is coming from, which is then used with volley requests to establish the connection
       * to our endpoints on Node.JS*/

    public static String BASE_NETWORK_ADDRESS = "http://10.0.2.2:3000/";

}
