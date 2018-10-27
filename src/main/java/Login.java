import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class Login extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setMaxAge(3600);
        cookie.setSecure(false);
        response.addCookie(cookie);

    }

    private static String createCSRFTOken() {
        String csrfToken = null;
        byte[] bytes = new byte[16];
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            secureRandom.nextBytes(bytes);
            csrfToken = new String(Base64.getEncoder().encode(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return csrfToken;
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username, password;

        try {
            username = request.getParameter("username");
            password = request.getParameter("password");

            Cookie[] cookies = request.getCookies();
            cookies[0].setPath("/");

            if (username.equals("admin") && password.equals("admin")) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                CookieStorage.getCookieStorage().addItem(cookies[0].getValue(), createCSRFTOken());
                response.sendRedirect("home.jsp");
            } else {
                out.println("Invalid username or password | try 'admin' for both username and password");
            }
        } finally {
            out.close();
        }

    }

}
