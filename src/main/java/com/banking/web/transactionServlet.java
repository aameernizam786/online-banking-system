package com.banking.web;

I@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws IOException, ServletException {
        BankService bs = new BankService();
        long acc = Long.parseLong(r.getParameter("accountId"));
        BigDecimal amt = new BigDecimal(r.getParameter("amount"));
        if ("deposit".equals(r.getParameter("type"))) bs.deposit(acc, amt);
        else bs.withdraw(acc, amt);
        s.sendRedirect("dashboard");
    }
}
