<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagamento</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            width: 100%;
            max-width: 480px;
            animation: slideIn 0.4s ease-out;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        h1 {
            color: #667eea;
            margin-bottom: 30px;
            font-size: 28px;
            text-align: center;
            font-weight: 600;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #4a5568;
            font-weight: 600;
            font-size: 14px;
        }

        .required {
            color: #f56565;
            margin-left: 3px;
        }

        input, select {
            width: 100%;
            padding: 14px 16px;
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            font-size: 15px;
            transition: all 0.3s ease;
            background: white;
            color: #2d3748;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            transform: translateY(-2px);
        }

        input:hover, select:hover {
            border-color: #cbd5e0;
        }

        select {
            cursor: pointer;
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23667eea' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 16px center;
            padding-right: 40px;
        }

        button {
            width: 100%;
            padding: 16px;
            margin-top: 10px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
        }

        button:active {
            transform: translateY(0);
        }

        input[type="number"]::-webkit-inner-spin-button,
        input[type="number"]::-webkit-outer-spin-button {
            opacity: 1;
        }

        @media (max-width: 500px) {
            .container {
                padding: 30px 25px;
            }
            
            h1 {
                font-size: 24px;
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
    </div>
</body>
</html>