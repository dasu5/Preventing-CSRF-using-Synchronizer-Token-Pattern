
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Validator extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String csrfToken = CookieStorage.getCookieStorage().getItem(cookies[0].getValue());
        response.setContentType("application/json");
        JSONObject member = new JSONObject();
        member.put("csrfToken", csrfToken);
        PrintWriter pw = response.getWriter();
        pw.print(member.toString());
        pw.flush();
        pw.close();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        serviceNew(request, response);
    }


    protected void serviceNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String id = request.getParameter("id");
            String key = request.getParameter("key");
            String CSRFTokenRecieved = request.getParameter("tokentxt");
            out.println("Registration Number :" + id);
            out.println("Password :" + key);
            out.println("Token :" + CSRFTokenRecieved);
            Cookie[] cookies = request.getCookies();
            String CSRFToken = CookieStorage.getCookieStorage().getItem(cookies[0].getValue());
            if (CSRFTokenRecieved.equals(CSRFToken)) {
                out.println("Successfully Verfied !!");
            } else {
                out.println("Verification Failed !!");
            }
        } finally {
            out.close();
        }
    }
}
