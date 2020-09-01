package edu.eci.arep.sparkD2;

import edu.eci.arep.sparkD2.httpserver.Request;
import edu.eci.arep.sparkD2.httpserver.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class sparkD {
    private static Map<String,BiFunction<Request,Response,String>> ep = new HashMap<>();

    public static void get(String path, BiFunction<Request, Response,String> f){
        ep.put("GET"+path,f);
    }

    public static void post(String path, BiFunction<Request,Response,String> f){
        ep.put("POST"+path,f);}

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
