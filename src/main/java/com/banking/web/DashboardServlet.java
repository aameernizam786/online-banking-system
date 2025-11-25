package com.banking.web;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest r, HttpServletResponse s)
            throws IOException, ServletException {
        User u = (User) r.getSession().getAttribute("user");
        if (u == null) { s.sendRedirect("login.jsp"); return; }
        r.setAttribute("accounts", new AccountDAO().findByUserId(u.getUserId()));
        r.getRequestDispatcher("dashboard.jsp").forward(r, s);
    }
}

