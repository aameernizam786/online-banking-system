<%@ page import="java.util.*,com.banking.model.Account" %>
<%
    List<Account> accs = (List<Account>)request.getAttribute("accounts");
%>
<table border="1">
    <tr><th>ID</th><th>Type</th><th>Balance</th></tr>
    <% for(Account a: accs){ %>
    <tr>
        <td><%=a.getAccountId()%></td>
        <td><%=a.getAccountType()%></td>
        <td><%=a.getBalance()%></td>
    </tr>
    <% } %>
</table>

<form action="transaction" method="post">
    <input name="accountId" placeholder="Account ID">
    <input name="amount" placeholder="Amount">
    <button name="type" value="deposit">Deposit</button>
    <button name="type" value="withdraw">Withdraw</button>
</form>

<a href="logout">Logout</a>
