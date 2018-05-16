package main.com;

import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hd48552 on 2018/5/16.
 */
public class TokenProcesser {
    private  final static String  SESSION_TOKEN = "SESSION_TOKEN";
    private  final static String  PARAMETER_TOKEN = "AT_RACHEL";
    private  long previous;
//    使用单例设计模式
    private static  TokenProcesser instance = new TokenProcesser();
    public static TokenProcesser getInstance(){
        return instance;
    }

    public  boolean isValid(HttpServletRequest request){

        HttpSession httpSession = request.getSession();
        if(httpSession == null){
            System.out.println("http session is null :"+System.currentTimeMillis());
            return  false;

        }
        String sessToken = (String) httpSession.getAttribute(SESSION_TOKEN);
        String paraToken = request.getParameter(PARAMETER_TOKEN);
        if(sessToken==null || paraToken ==null){
            System.out.println("session or para token is null :"+System.currentTimeMillis());
            return  false;
        }
        else {
            System.out.println("token is valid  :"+System.currentTimeMillis());
            return sessToken.equals(paraToken);
        }
    }
    public  void  removeToken(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        if (httpSession != null) {
            httpSession.removeAttribute(SESSION_TOKEN);
            System.out.println("remove token :"+System.currentTimeMillis());
            System.out.println("--------------- ");
        }
        else
            return;
    }
    public  String saveToken(HttpServletRequest request) throws NoSuchAlgorithmException {
        String tok = generateToken(request);
        if(tok != null){
            request.getSession().setAttribute(SESSION_TOKEN,tok);
            System.out.println("set session token :"+System.currentTimeMillis());
        }
        return tok;
    }
//    产生token
    private String generateToken(HttpServletRequest request) throws NoSuchAlgorithmException {
        String sessionId = request.getSession().getId();
        System.out.println("session id  :"+ request.getSession().getId());
        return  genereteToken(sessionId);
    }

//    使用sessionID和当前时间产生token
    private  String genereteToken(String id) throws NoSuchAlgorithmException {

        System.out.println("generate token :"+System.currentTimeMillis());
        String token = null;
        long current = System.currentTimeMillis();
        if(previous==current){
            current++;//????
        }
        previous = current;

//        产生token
        byte[] now = new Long(current).toString().getBytes();//将当前时间对应的字符串转换为byte数组
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");//获取MD5消息摘要，用于加密数据
        messageDigest.update(now); //对数据进行加密
        messageDigest.update(id.getBytes());
        byte[] message = messageDigest.digest(); //将加密结果转化为byte数组

        token = toHex(message);
        return  token;
    }
//    将byte数组转化为十六进制字符串
    private String toHex(byte[] buffer){
        StringBuffer sb = new StringBuffer(buffer.length * 2);

        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }

        return sb.toString();
    }

}
