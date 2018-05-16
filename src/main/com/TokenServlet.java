package main.com;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hd48552 on 2018/5/16.
 */
public class TokenServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("form parameter name :"+request.getParameter("name"));
//延长页面响应时间，创造表单重复提交条件
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //todo
//        检测token是否一致
        boolean isValid = TokenProcesser.getInstance().isValid(request);
//        一致 受理请求 清除token 并提示成功信息
        if(isValid){
            TokenProcesser.getInstance().removeToken(request);
            response.sendRedirect("success.jsp");
        }
        else
            response.sendRedirect("sorry.jsp");
//        不一致 转到提示页面

    }

}
