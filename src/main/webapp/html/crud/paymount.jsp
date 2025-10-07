<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagamento</title>
    <style>
        :root {
            --oil: #274855;
            --black-oil: #202C33;
            --orange: #E89640;
            --black: #4B4844;
            --polar: #DAE4E3;
            --white: #FFFFFF;

            --border-big: 15px;
            --border-medium: 6px;
            --border-small: 4.5px;
            --border-simple: 2.5px;

            --icon: 18px;

            --weak-shadow: 0px 2px 20px -15px rgba(0, 0, 0, 0.210);
            --strong-shadow: 0px 4px 40px 5px rgba(0, 0, 0, 0.120);
            --text-shadow: 0 10px 5px rgba(0, 0, 0, 0.250);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--black-oil);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .container {
            background: var(--oil);
            padding: 35px;
            border-radius: var(--border-big);
            box-shadow: var(--strong-shadow);
            width: 100%;
            max-width: 480px;
            animation: slideIn 0.3s ease-out;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-15px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        h1 {
            color: var(--white);
            margin-bottom: 25px;
            font-size: 26px;
            text-align: center;
            font-weight: 600;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            color: var(--polar);
            font-weight: 500;
            font-size: 14px;
        }

        .required {
            color: var(--orange);
            margin-left: 2px;
        }

        input, select {
            width: 100%;
            padding: 12px 14px;
            border: 2px solid var(--black-oil);
            border-radius: var(--border-medium);
            font-size: 15px;
            transition: all 0.2s ease;
            background: var(--black-oil);
            color: var(--white);
        }

        input:focus, select:focus {
            outline: none;
            border-color: var(--orange);
            box-shadow: 0 0 0 2px rgba(232, 150, 64, 0.2);
        }

        input:hover, select:hover {
            border-color: var(--orange);
        }

        input::placeholder {
            color: rgba(218, 228, 227, 0.5);
        }

        select {
            cursor: pointer;
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23E89640' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 14px center;
            padding-right: 38px;
        }

        option {
            background: var(--black-oil);
            color: var(--white);
        }

        button {
            width: 100%;
            padding: 14px;
            margin-top: 8px;
            background: var(--orange);
            color: var(--white);
            border: none;
            border-radius: var(--border-medium);
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            transition: all 0.2s ease;
            box-shadow: var(--weak-shadow);
        }

        button:hover {
            background: #d4843a;
            box-shadow: 0 4px 20px rgba(232, 150, 64, 0.4);
            transform: translateY(-2px);
        }

        button:active {
            transform: translateY(0);
        }

        .message {
            color: var(--orange);
            margin-top: 15px;
            padding: 12px;
            background: rgba(232, 150, 64, 0.15);
            border-radius: var(--border-small);
            border-left: 3px solid var(--orange);
            font-size: 14px;
        }

        input[type="number"]::-webkit-inner-spin-button,
        input[type="number"]::-webkit-outer-spin-button {
            opacity: 1;
        }

        input[type="date"]::-webkit-calendar-picker-indicator {
            filter: invert(1);
            cursor: pointer;
        }

        @media (max-width: 500px) {
            .container {
                padding: 28px 22px;
            }
            
            h1 {
                font-size: 23px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>💳 Pagamento</h1>
        
        <form action="/HivemindWeb_war/paymount" id="paymentForm" method="post">
            <div class="form-group">
                <label>Vencimento <span class="required">*</span></label>
                <input type="date" name="deadline" required>
            </div>

            <div class="form-group">
                <label>Número de Parcelas <span class="required">*</span></label>
                <input type="number" name="installmentCount" required min="1" max="12" placeholder="1">
            </div>

            <div class="form-group">
                <label>Método <span class="required">*</span></label>
                <select name="method" required>
                    <option value="">Selecione...</option>
                    <option value="PIX">PIX</option>
                    <option value="Cartão de Crédito">Cartão de Crédito</option>
                    <option value="Cartão de Débito">Cartão de Débito</option>
                    <option value="Boleto">Boleto</option>
                    <option value="Transferência">Transferência</option>
                </select>
            </div>

            <div class="form-group">
                <label>Beneficiário <span class="required">*</span></label>
                <input type="text" name="beneficiary" required placeholder="Nome completo">
            </div>

            <div class="form-group">
                <label>Status <span class="required">*</span></label>
                <select name="status" required>
                    <option value="">Selecione...</option>
                    <option value="Pendente">Pendente</option>
                    <option value="Pago">Pago</option>
                    <option value="Atrasado">Atrasado</option>
                    <option value="Cancelado">Cancelado</option>
                </select>
            </div>

            <div class="form-group">
                <label>ID Plano <span class="required">*</span></label>
                <input type="number" name="id_plan_sub" required min="1">
            </div>

            <button type="submit">Adicionar Pagamento</button>
        </form>
        
        <% String msg = (String) request.getAttribute("msg"); %>
        <% if (msg != null) { %>
            <div class="message">
                <%= msg %>
            </div>
        <% } %>
    </div>
</body>
</html>