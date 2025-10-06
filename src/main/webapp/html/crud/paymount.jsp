<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gerenciar Pagamentos</title>
    <style>
        body { font-family: Arial; background: #f5f5f5; padding: 30px; }
        form { background: white; padding: 20px; border-radius: 10px; width: 400px; margin-bottom: 20px; }
        input, select { width: 100%; padding: 8px; margin: 5px 0; }
        table { border-collapse: collapse; width: 100%; background: white; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background: #007bff; color: white; }
        .btn { background: #007bff; color: white; padding: 8px; border: none; cursor: pointer; border-radius: 6px; }
    </style>
</head>
<body>
    <h2>Cadastro de Pagamento</h2>
    <form action="/HivemindWeb_war/paymount" method="post">
        <label>Método de Pagamento:</label>
        <input type="text" name="method" required>

        <label>Beneficiário:</label>
        <input type="text" name="beneficiary" required>

        <label>Data Limite:</label>
        <input type="date" name="deadline" required>

        <label>Status:</label>
        <select name="status">
            <option value="true">Ativo</option>
            <option value="false">Inativo</option>
        </select>

        <label>ID do Plano:</label>
        <input type="number" name="id_plan_sub" required>

        <button class="btn" type="submit">Salvar Pagamento</button>
    </form>

    <hr>

    <h3>Pagamentos Existentes</h3>
    <table>
        <tr>
            <th>Valor</th>
            <th>Data Limite</th>
            <th>Método</th>
            <th>Beneficiário</th>
            <th>Status</th>
            <th>Ações</th>
        </tr>

        <%-- Exemplo estático, depois pode trocar por lista real (PaymentDAO.listAll()) --%>
        <tr>
            <td>120.00</td>
            <td>2025-10-10</td>
            <td>PIX</td>
            <td>Quitto</td>
            <td>Ativo</td>
            <td>
                <a href="paymount?action=edit&id=1">Editar</a> |
                <a href="paymount?action=delete&id=1">Excluir</a>
            </td>
        </tr>
    </table>
</body>
</html>
