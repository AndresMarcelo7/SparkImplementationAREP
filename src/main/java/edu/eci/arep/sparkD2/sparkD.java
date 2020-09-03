package edu.eci.arep.sparkD2;

import edu.eci.arep.sparkD2.httpserver.Request;
import edu.eci.arep.sparkD2.httpserver.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * The type Sparkd, SPARK's framework Logic definition.
 */
public class sparkD {
    private static Map<String,BiFunction<Request,Response,String>> ep = new HashMap<>();

    /**
     * Enables an endpoint for a GET method.
     *
     * @param path the path to be saved
     * @param f    the function implemented
     */
    public static void get(String path, BiFunction<Request, Response,String> f){
        ep.put("GET"+path,f);
    }

    /**
     * Enables an endpoint for a POST method .
     *
     * @param path the path to be saved
     * @param f    the function implemented
     */
    public static void post(String path, BiFunction<Request,Response,String> f){
        ep.put("POST"+path,f);}

    /**
     * Exec response, it searchs if exists and endpoint and execute its respective function and generates a new Response.
     *
     * @param request the Client request
     * @return the  new response
     */
    public static Response exec(Request request){
        String k = request.getMethod()+request.getPath();
        if (ep.containsKey(k)){

            Response response = new Response();
            response.setBody(ep.get(k).apply(request,response));
            return response;
        }
        else{
            //System.out.println(k);
            return null;
        }
    }
}
